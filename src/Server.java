import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Server implements Runnable
{
	Server(Comms c)
	{
		this.c = c;
	}

	public static void main(String[] args)
	{
		Server.init();
	}

	public static void init()
	{
		f.setTitle("Server");
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(500, 500);
		f.setContentPane(new ScrollPane());
		textArea.setEditable(false);
		f.getContentPane().add(textArea);
		f.setVisible(true);

		Comms.serverStart();
		while (true) {
			Comms c = Comms.connect();
			print(c.nameTime(), "ACCEPTED CONNECTION");
			c.init();
			Thread x = new Thread(new Server(c));
			x.start();
		}
	}

	private static void print(String name, String str)
	{
		print(name + " : " + str);
	}

	private static void print(String str)
	{
		textArea.append(str + "\n");
	}

	@Override
	public void run()
	{
		try {
			while (c.isOpen()) {
				Message m = c.getMessage();
				if (m instanceof RegisterUserMessage) {
					RegisterUserMessage u = (RegisterUserMessage) m;
					User user = new User(u.ID, u.name, u.familyName, u.hash);
					synchronized (registeredUsers) {
						if (registeredUsers.containsKey(user.getID())) {
							c.sendMessage(new StringMessage("register exists"));
						} else {
							registeredUsers.put(user.getID(), user);
							c.sendMessage(new StringMessage("register success"));
							print(c.nameTime(), user.getID() + " was registered");
						}
					}
				}
				if (m instanceof LoginMessage) {
					LoginMessage lm = (LoginMessage) m;
					synchronized (registeredUsers) {
						if (registeredUsers.containsKey(lm.ID)) {
							User u = registeredUsers.get(lm.ID);
							if (u.isOnline()) {
								c.sendMessage(new StringMessage("login invalid logged in"));
							} else if (u.getHash() == lm.hash) {
								c.sendMessage(new StringMessage("login success"));
								c.sendMessage(new ObjectMessage(u));
								u.setOnline(true);
								online++;
								print(c.nameTime(), u.getID() + " logged in");
							} else {
								c.sendMessage(new StringMessage("login invalid password"));
							}
						} else {
							c.sendMessage(new StringMessage("login invalid username"));
						}
					}
				}
				if (m instanceof RegisterItemMessage) {
					print(c.nameTime(), "sent register item message");
					RegisterItemMessage rim = (RegisterItemMessage) m;
					if (!auctions.containsKey(rim.getID())) {
						synchronized (auctions) {
							auctions.put(rim.getID(), rim.getItem());
						}
						synchronized (runningAuctions) {
							runningAuctions.add(rim.getItem());
						}
						c.sendMessage(new StringMessage("auction success"));
					} else {
						c.sendMessage(new StringMessage("auction exists"));
					}
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.s.equals("auctions")) {
						synchronized (runningAuctions) {
							c.sendMessage(new ObjectMessage(new ArrayList<Item>(runningAuctions)));
						}
					}
				}
				if (m instanceof LogoutMessage) {
					LogoutMessage lo = (LogoutMessage) m;
					synchronized (registeredUsers) {
						User u = registeredUsers.get(lo.getUser().getID());
						u.setOnline(false);
						print(c.nameTime(), u.getID() + " logged out");
					}
				}
			}
			online--;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		print(c.nameTime(), "DISCONNECTED");
		/* If last man standing */
		if (online < 1) {
			synchronized (registeredUsers) {
				for (String u : registeredUsers.keySet()) {
					User user = registeredUsers.get(u);
					user.setOnline(false);
				}
			}
		}

	}

	/* Easier to check that no two users have the same ID */
	private static HashMap<String, User> registeredUsers = new HashMap<>();
	/* Easier to check that no two items have the same ID */
	private static HashMap<String, Item> auctions = new HashMap<>();
	/* Easier to find auctions that have ended */
	private static PriorityQueue<Item> runningAuctions = new PriorityQueue<>((o1, o2) ->
			(int) (o1.getCloseTime().compareTo(o2.getCloseTime())));
	private static int online = 0;
	private static JFrame f = new JFrame();
	private static JTextArea textArea = new JTextArea();
	private final Comms c;
}
