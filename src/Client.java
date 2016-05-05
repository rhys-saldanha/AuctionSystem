import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class Client
{
	Client()
	{
		this.c = new Comms();
		this.f = new JFrame();
	}

	public static void main(String[] args)
	{
		(new Client()).run();
	}

	public void run()
	{
		this.init();
		Scanner s = new Scanner(System.in);
		/*while (true) {
			String input = s.nextLine();
			c.sendMessage(new Message(0, input));
		}*/
		while (c.isOpen()) {
			ClientPanel p = c.getPanel();
			if (p != null) {
				f.setContentPane(p);
				f.revalidate();
				f.repaint();
			}
		}
	}

	public void exit()
	{
		c.close();
		f.dispose();
		System.exit(0);
	}

	private void init()
	{
		f.setTitle("Client");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event)
			{
				exit();
			}
		});

		f.setSize(500, 500);
		f.setVisible(true);
	}

	private JFrame f;
	private Comms c;
}