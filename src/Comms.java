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
		try {
			in = sk.getInputStream();
			out = sk.getOutputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void sendMessage(Message m)
	{
		try {
			ObjectOutputStream writeOut = new ObjectOutputStream(out);
			writeOut.writeObject(m);
			writeOut.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Message getMessage()
	{
		Message m = null;
		try {
			ObjectInputStream readIn = new ObjectInputStream(in);
			m = (Message) readIn.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return m;
	}

	public String name()
	{
		return sk.getRemoteSocketAddress().toString();
	}

	private Socket sk;
	private InputStream in;
	private OutputStream out;
}
