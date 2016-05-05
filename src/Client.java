import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class Client implements Runnable
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

	@Override
	public void run()
	{
		this.init();
		Scanner s = new Scanner(System.in);
		while (c.isOpen()) {
			//String input = s.nextLine();
			String input = "";
			if (input.contains("NAME")) {
				c.sendMessage(new StringMessage(1, input.replace("NAME ", "")));
			} else if (input.equals("LIST")) {
				c.sendMessage(new StringMessage(2, ""));
				StringMessage sm;
				while (true) {
					Message m = c.getMessage();
					if (m instanceof StringMessage) {
						sm = (StringMessage) m;
						if (sm.i == 0) {
							break;
						}
						System.out.println(sm.s);
					}
				}
				continue;
			} else {
				c.sendMessage(new StringMessage(0, input));
			}
			Message m = c.getMessage();
			if (m != null) {
				if (m instanceof ClientPanel) {
					ClientPanel p = (ClientPanel) m;
					f.setContentPane(p);
					f.revalidate();
					f.repaint();
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					System.out.println(sm.s);
				}
			}
		}
	}

	private void exit()
	{
		c.close();
		f.dispose();
		System.exit(0);
	}

	private void init()
	{
		c.init();
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

	private final JFrame f;
	private final Comms c;
}