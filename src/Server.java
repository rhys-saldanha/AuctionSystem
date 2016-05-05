public class Server
{
	Server()
	{
		sc = new ServerComms();
	}

	public static void main(String[] args)
	{
		Server s = new Server();
		s.init();
	}

	private void init()
	{
		sc.connect();
	}

	private final ServerComms sc;
}
