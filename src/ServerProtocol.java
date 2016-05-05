public class ServerProtocol implements Runnable
{
	ServerProtocol(Comms c)
	{
		this.c = c;
	}

	@Override
	public void run()
	{
		System.out.println("Server thread created for " + c.name());
		while (true) {
			Message m = c.getMessage();
			if (m != null) {
				System.out.printf("%s : %s\n", c.name(), m.s);
			}
		}
	}

	private Comms c;
}
