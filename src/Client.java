import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client
{
	Client()
	{
		this.c = new Comms();
	}

	public static void main(String[] args)
	{
		(new Client()).init();
	}

	private void init()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		this.f = new JFrame();
		c.init();
		gui = new ClientGUI(f, c);
		gui.init();
		f.setTitle("Client");
		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event)
			{
				exit();
			}
		});
		f.setSize(1000, 800);
		f.setVisible(true);
	}

	private void exit()
	{
		c.close();
		f.dispose();
		System.exit(0);
	}

	private final Comms c;
	private JFrame f;
	private ClientGUI gui;
}