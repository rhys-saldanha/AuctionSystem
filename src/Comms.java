import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Comms
{
	Comms(Socket sk)
	{
		this.sk = sk;
	}

	Comms()
	{
		try {
			sk = new Socket("localhost", PORT);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static Comms connect()
	{
		Comms c = null;
		try (
				ServerSocket sc = new ServerSocket(PORT)
		) {
			Socket sk = sc.accept();
			c = new Comms(sk);
			System.out.println(c.name() + " : ACCEPTED CONNECTION");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return c;
	}

	public void init()
	{
		try {
			messages = new ConcurrentLinkedQueue<>();
			in = sk.getInputStream();
			out = sk.getOutputStream();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		startMessageThread();
	}

	public void sendMessage(Message m)
	{
		try {
			ObjectOutputStream writeOut = new ObjectOutputStream(out);
			writeOut.writeObject(m);
			writeOut.flush();
		} catch (SocketException ex) {
			close(false);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Message getMessage()
	{
		return messages.poll();
	}

	private void startMessageThread()
	{
		Thread x = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true) {
					Message m = null;
					try {
						ObjectInputStream readIn = new ObjectInputStream(in);
						m = (Message) readIn.readObject();
					} catch (SocketException ex) {
						close(false);
					} catch (IOException | ClassNotFoundException ex) {
						ex.printStackTrace();
					}
					if (m instanceof ExitMessage) {
						close(false);
					}
					if (m != null)
						messages.add(m);
				}
			}
		});
		x.start();
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
		this.close(true);
	}

	private void close(boolean b)
	{
		open = false;
		if (b) {
			this.sendMessage(new ExitMessage());
		}
		try {
			in.close();
			out.close();
			sk.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static final int PORT = 2244;
	private boolean open = true;
	private Socket sk;
	private InputStream in;
	private OutputStream out;
	private ConcurrentLinkedQueue<Message> messages;
}
