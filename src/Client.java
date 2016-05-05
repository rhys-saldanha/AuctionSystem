import java.util.Scanner;

public class Client
{
	Client()
	{
		this.c = new Comms();
	}

	public static void main(String[] args)
	{
		(new Client()).run();
	}

	public void run()
	{
		Scanner s = new Scanner(System.in);
		while (true) {
			String input = s.nextLine();
			c.sendMessage(new Message(0, input));
		}
	}

	private Comms c;
}