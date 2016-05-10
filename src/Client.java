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
		/* Changes UI theme */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
		/* Creates Client window */
		this.f = new JFrame();
		/* Initiated Comms */
		c.init();
		/* Creates and initiates instance of class for managing GUI */
		gui = new ClientGUI(f, c);
		gui.init();
		/* Sets title of window */
		f.setTitle("Client");
		/* Removes default close operation in favour of custom exit method */
		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event)
			{
				exit();
			}
		});
		/* Sets the size of the window */
		f.setSize(1000, 800);
		/* Makes the window not resizable */
		f.setResizable(false);
		/* Shows window */
		f.setVisible(true);
	}

	private void exit()
	{
		/* Closes Comms */
		c.close();
		/* Disposes window */
		f.dispose();
		/* Forces close of thread and all connected threads */
		System.exit(0);
	}

	private final Comms c;
	private JFrame f;
	private ClientGUI gui;
}