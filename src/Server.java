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

	private static void init()
	{
		while (true) {
			Comms c = Comms.connect();
			if (c != null) {
				online++;
				c.init();
				Thread x = new Thread(new Server(c));
				x.start();
			}
		}
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
					exists: {
						for (User registeredUser : registeredUsers) {
							if (registeredUser.equals(user)) {
								break exists;
							}
						}
						registeredUsers.add(user);
						System.out.println(c.nameTime() + " : " + user.getID() + " was registered");
					}
					//return error, user already exists
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.i == 2) {
						for (User registeredUser : registeredUsers) {
							c.sendMessage(new StringMessage(1, registeredUser.getID()));
						}
						c.sendMessage(new StringMessage(0, ""));
					}
				}
			}
			online--;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(c.nameTime() + " : DISCONNECTED");
		if (online < 1)
			System.exit(0);
	}

	private static ConcurrentSkipListSet<User> registeredUsers = new ConcurrentSkipListSet<>(
			(o1, o2) -> o1.hashCode() - o2.hashCode());
	private final Comms c;
	private static int online = 0;
}
