import javax.swing.*;
import java.awt.*;
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
			online++;
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
				if (m instanceof NewUserMessage) {
					NewUserMessage u = (NewUserMessage) m;
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
								print(c.nameTime(), u.getID() + " was logged in");
							} else {
								c.sendMessage(new StringMessage("login invalid password"));
							}
						} else {
							c.sendMessage(new StringMessage("login invalid username"));
						}
					}
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.s.equals("auctions")) {
						c.sendMessage(new ObjectMessage(auctions));
					}
				}
				if (m instanceof LogoutMessage) {
					LogoutMessage lo = (LogoutMessage) m;
					User u = registeredUsers.get(lo.getUser().getID());
					u.setOnline(false);
				}
			}
//			online--;
		} catch (Exception ex) {
			exit();
			ex.printStackTrace();
		}
		print(c.nameTime(), "DISCONNECTED");
		if (online < 1)
			System.exit(0);
	}

	private void exit()
	{
		/* Closes Comms */
		c.close();
		/* Disposes window */
		f.dispose();
		/* Forces close of thread and all connected threads */
		System.exit(0);
	}

	private static HashMap<String, User> registeredUsers = new HashMap<>();
	private static HashMap<String, Item> auctions = new HashMap<>();
	private static PriorityQueue<Item> runningAuctions = new PriorityQueue<>((o1, o2) -> (int) (o1.getCloseTime().getTime() - o2.getCloseTime().getTime()));
	private static int online = 0;
	private static JFrame f = new JFrame();
	private static JTextArea textArea = new JTextArea();
	private final Comms c;
}
