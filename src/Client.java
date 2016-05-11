import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

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
		f.setResizable(true);
		/* Shows window */
		f.setVisible(true);
		while (c.isOpen()) {
			Message m = c.getMessage();
			if (m != null) {
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.s.contains("register")) {
						if (sm.s.contains("success")) {
							gui.makeLoginPage();
						}
						if (sm.s.contains("exists")) {
							gui.makeErrorFrame("User already exists");
						}
					}
					if (sm.s.contains("login")) {
						if (sm.s.contains("success")) {
							gui.makeErrorFrame("Logged in successfully");
						}
						if (sm.s.contains("invalid")) {
							if (sm.s.contains("password")) {
								gui.makeErrorFrame("Invalid password");
							}
							if (sm.s.contains("username")) {
								gui.makeErrorFrame("Invalid username");
							}
							if (sm.s.contains("logged in")) {
								gui.makeErrorFrame("User already logged in");
							}
						}
					}
				}
				if (m instanceof ObjectMessage) {
					ObjectMessage om = (ObjectMessage) m;
					if (om.getObject() instanceof User) {
						gui.setUser((User) om.getObject());
						gui.makeNavBar();
						c.sendMessage(new StringMessage("auctions"));
					}
					if (om.getObject() instanceof HashMap) {
						gui.setAuctions((HashMap<String, Item>) om.getObject());
						gui.makeAuctionContent();
						gui.makeMainPage();
					}
				}
			}
		}
		this.exit();
	}

	private void exit()
	{
		/* Closes Comms */
		c.close();
		/* Logs out user */
		gui.logOut();
		/* Disposes window */
		f.dispose();
		/* Forces close of thread and all connected threads */
		System.exit(0);
	}

	private final Comms c;
	private JFrame f;
	private ClientGUI gui;
}