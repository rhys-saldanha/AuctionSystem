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
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.i == 1) {
						Server.names.add(sm.s);
					} else if (sm.i == 2) {
						Iterator<String> it = names.iterator();
						while (it.hasNext()) {
							c.sendMessage(new StringMessage(2, it.next()));
						}
						c.sendMessage(new StringMessage(0, ""));
					} else {
						System.out.printf("%s : %s\n", c.nameTime(), sm.s);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(c.nameTime() + " : DISCONNECTED");
	}

	private static ConcurrentSkipListSet<String> names = new ConcurrentSkipListSet<>();
	private final Comms c;
}
