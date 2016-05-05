import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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
		} catch (SocketException ex) {
			close();
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

	public void sendPanel(ClientPanel p)
	{
		try {
			ObjectOutputStream writeOut = new ObjectOutputStream(out);
			writeOut.writeObject(p);
			writeOut.flush();
		} catch (SocketException ex) {
			close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ClientPanel getPanel()
	{
		ClientPanel p = null;
		try {
			ObjectInputStream readIn = new ObjectInputStream(in);
			p = (ClientPanel) readIn.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return p;
	}

	public String name()
	{
		return sk.getRemoteSocketAddress().toString();
	}

	public boolean isOpen()
	{
		return open;
	}

	public void close()
	{
		open = false;
		try {
			sk.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private boolean open = true;
	private Socket sk;
	private InputStream in;
	private OutputStream out;
}
