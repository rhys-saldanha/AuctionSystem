import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
			if (gui.getUser() != null) {
				c.sendMessage(new GetNotificationMessage(gui.getUser()));
			}
			Message m = c.getMessage();
			if (m != null) {
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.s.contains("register")) {
						if (sm.s.contains("success")) {
							gui.makePopUpFrame("Registered successfully");
							gui.makeLoginPage();
						}
						if (sm.s.contains("exists")) {
							gui.makePopUpFrame("User already exists");
						}
					}
					if (sm.s.contains("login")) {
						if (sm.s.contains("success")) {
							gui.makePopUpFrame("Logged in successfully");
						}
						if (sm.s.contains("invalid")) {
							if (sm.s.contains("password")) {
								gui.makePopUpFrame("Invalid password");
							}
							if (sm.s.contains("username")) {
								gui.makePopUpFrame("Invalid username");
							}
							if (sm.s.contains("logged in")) {
								gui.makePopUpFrame("User already logged in");
							}
						}
					}
					if (sm.s.contains("auction")) {
						if (sm.s.contains("success")) {
							gui.makePopUpFrame("Registered item successfully");
							gui.makeMainPage();
						}
						if (sm.s.contains("exists")) {
							gui.makePopUpFrame("You already have an item with this name");
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
					if (om.getObject() instanceof ArrayList) {
						gui.setAuctions((ArrayList<Item>) om.getObject());
						gui.makeAuctionContent();
						gui.makeMainPage();
					}
				}
				if (m instanceof NotificationMessage) {
					NotificationMessage notif = (NotificationMessage) m;
					gui.makePopUpFrame(notif.getNotification());
				}
			}
		}
		this.exit();
	}

	private void exit()
	{
		/* Logs out user */
		gui.logOut();
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