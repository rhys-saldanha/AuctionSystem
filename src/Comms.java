import java.io.*;
import java.net.Socket;

public class Comms
{
	Comms(Socket sk)
	{
		this.sk = sk;
		init();
	}

	Comms()
	{
		try {
			sk = new Socket("localhost", ServerComms.PORT);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		init();
	}

	private void init()
	{
		System.out.println("Initialising comms");
		try {
			in = sk.getInputStream();
			out = sk.getOutputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Comms initialised");
	}

	public void sendMessage(Message m)
	{
		System.out.println("Sending message");
		try {
			ObjectOutputStream writeOut = new ObjectOutputStream(out);
			writeOut.writeObject(m);
			writeOut.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		System.out.println("Message sent!");
	}

	public Message getMessage()
	{
		System.out.println("Getting message");
		Message m = null;
		try {
			ObjectInputStream readIn = new ObjectInputStream(in);
			m = (Message) readIn.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		System.out.println("Message received!");
		return m;
	}

	private Socket sk;
	private InputStream in;
	private OutputStream out;
}
