import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerComms
{
	public void connect()
	{
		try (
				ServerSocket sc = new ServerSocket(PORT)
		) {
			while (true) {
				Socket sk = sc.accept();
				System.out.println("Connection accepted");
				Comms c = new Comms(sk);
				Thread x = new Thread(new ServerProtocol(c));
				x.start();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static final int PORT = 4444;
}