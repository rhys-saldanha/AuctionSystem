import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Comms implements Message, Serializable
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

	public static void serverStart()
	{
		try {
			sc = new ServerSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Comms connect()
	{
		Comms c = null;
		try {
			Socket sk = sc.accept();
			c = new Comms(sk);
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

	public synchronized void sendMessage(Message m)
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

	public synchronized Message getMessage()
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
					if (m instanceof ExitMessage)
						close(false);
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

	public String nameTime()
	{
		return String.format("%s @ %02d:%02d", this.name(), LocalDateTime.now().getHour(),
				LocalDateTime.now().getMinute());
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
		if (isOpen()) {
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
			open = false;
		}
	}
	public static final int PORT = 2244;
	private static ServerSocket sc;
	private boolean open = true;
	private Socket sk;
	private InputStream in;
	private OutputStream out;
	private ConcurrentLinkedQueue<Message> messages;
}
