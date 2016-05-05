public class Server
{
	public static void main(String[] args)
	{
		Server s = new Server();
		s.init();
	}

	private void init()
	{
		while (true) {
			Comms c = Comms.connect();
			if (c != null) {
				Thread x = new Thread(new ServerProtocol(c));
				x.start();
			}
		}
	}
}
