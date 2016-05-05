import java.util.Iterator;
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
				c.init();
				Thread x = new Thread(new Server(c));
				x.start();
			}
		}
	}

	@Override
	public void run()
	{
		System.out.println(c.nameTime() + " : STARTED");
		try {
			while (c.isOpen()) {
				Message m = c.getMessage();
				if (m instanceof NewUserMessage) {
					NewUserMessage u = (NewUserMessage) m;
					registeredUsers.add(new User(u.ID, u.name, u.familyName, u.hash));
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.i == 2) {
						Iterator<User> it = registeredUsers.iterator();
						while (it.hasNext()) {
							c.sendMessage(new StringMessage(1, it.next().getID()));
						}
						c.sendMessage(new StringMessage(0, ""));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(c.nameTime() + " : DISCONNECTED");
	}

	private static ConcurrentSkipListSet<User> registeredUsers = new ConcurrentSkipListSet<>(
			(o1, o2) -> o1.hashCode() - o2.hashCode());
	private final Comms c;
}
