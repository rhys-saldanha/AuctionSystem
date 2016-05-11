import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

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
			print(c.nameTime() + " : ACCEPTED CONNECTION");
			online++;
			c.init();
			Thread x = new Thread(new Server(c));
			x.start();
		}
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
							print(c.nameTime() + " : " + user.getID() + " was registered");
						}
					}
				}
				if (m instanceof LoginMessage) {
					LoginMessage lm = (LoginMessage) m;
					synchronized (registeredUsers) {
						if (registeredUsers.containsKey(lm.ID)) {
							User u = registeredUsers.get(lm.ID);
							if (u.getHash() == lm.hash) {
								c.sendMessage(new StringMessage("login success"));
								c.sendMessage(new ObjectMessage(u));
							} else {
								c.sendMessage(new StringMessage("login invalid password"));
							}
						} else {
							c.sendMessage(new StringMessage("login invalid username"));
						}
					}
				}
			}
//			online--;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		print(c.nameTime() + " : DISCONNECTED");
		if (online < 1)
			System.exit(0);
	}

	private static HashMap<String, User> registeredUsers = new HashMap<>();
	private static HashMap<String, Item> auctions = new HashMap<>();
	private static int online = 0;
	private static JFrame f = new JFrame();
	private static JTextArea textArea = new JTextArea();
	private final Comms c;
}
