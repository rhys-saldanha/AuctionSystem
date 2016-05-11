import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentSkipListSet;

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
					exists:
					{
						for (User registeredUser : registeredUsers) {
							if (registeredUser.equals(user)) {
								break exists;
							}
						}
						registeredUsers.add(user);
						print(c.nameTime() + " : " + user.getID() + " was registered");
					}
					//return error, user already exists
				}
				if (m instanceof LoginMessage) {
					LoginMessage lm = (LoginMessage) m;
					exists:
					{
						for (User registeredUser : registeredUsers) {
							if (registeredUser.getID().equals(lm.ID)) {
								if (registeredUser.getHash() == lm.hash) {
									c.sendMessage(new StringMessage("success"));
									break exists;
								} else {
									c.sendMessage(new StringMessage("invalid password"));
									break exists;
								}
							}
						}
						c.sendMessage(new StringMessage("invalid username"));
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

	private static ConcurrentSkipListSet<User> registeredUsers = new ConcurrentSkipListSet<>(
			(o1, o2) -> o1.hashCode() - o2.hashCode());
	private static int online = 0;
	private static JFrame f = new JFrame();
	private static JTextArea textArea = new JTextArea();
	private final Comms c;
}
