import java.io.IOException;
import java.net.Socket;

public class ServerComms
{
	ServerComms()
	{
		this(false);
	}

	ServerComms(boolean b)
	{
		isServer = b;
	}

	public void init()
	{
		if (isServer) {

		}
	}

	public void sendMessage(Message m)
	{

	}

	public void addSocket() throws IOException
	{
		this.addSocket(new Socket(hostname, portNumber));
	}

	public void addSocket(Socket s)
	{
		this.socket = s;
	}

	private final String hostname = "localhost";
	private final int portNumber = 4444;
	private Socket socket;
	private boolean isServer;
}
