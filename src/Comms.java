import java.io.*;
import java.net.ServerSocket;
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
			sk = new Socket("localhost", PORT);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		init();
	}

	public static Comms connect()
	{
		Comms c = null;
		try (
				ServerSocket sc = new ServerSocket(PORT)
		) {
			Socket sk = sc.accept();
			c = new Comms(sk);
			System.out.println("Connection accepted for " + c.name());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return c;
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

	public void sendMessage(StringMessage m)
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
		} catch (SocketException ex) {
			close();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return m;
	}

	public void sendMessage(ClientPanel p)
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

	public String name()
	{
		return sk.getRemoteSocketAddress().toString().substring(1);
	}

	public boolean isOpen()
	{
		return open;
	}

	public void close()
	{
		open = false;
		try {
			in.close();
			out.close();
			sk.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static final int PORT = 4444;
	private boolean open = true;
	private Socket sk;
	private InputStream in;
	private OutputStream out;
}
