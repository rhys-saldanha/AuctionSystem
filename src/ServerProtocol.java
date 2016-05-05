public class ServerProtocol implements Runnable
{
	ServerProtocol(Comms c)
	{
		this.c = c;
	}

	@Override
	public void run()
	{
		System.out.println("Server thread created");
		while (true) {
			Message m = c.getMessage();
			if (m != null) {
				System.out.println(m.s);
				break;
			}
		}
	}

	private Comms c;
}
