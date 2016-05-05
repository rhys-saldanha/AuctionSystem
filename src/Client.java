public class Client
{
	Client()
	{
		this.c = new Comms();
		c.sendMessage(new Message(5, "Hello World!"));
	}

	public static void main(String[] args)
	{
		new Client();
	}

	private Comms c;
}