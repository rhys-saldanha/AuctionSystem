import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Server implements Runnable
{
	Server(Comms c)
	{
		this.c = c;
	}

	public static void main(String[] args)
	{
		Server.init();
	}

	public static void init()
	{
		registeredUsers = (DataPersistence.load("registeredUsers") == null)
				? new HashMap<>()
				: (HashMap<String, User>) DataPersistence.load("registeredUsers");

		auctions = (DataPersistence.load("auctions") == null)
				? new HashMap<>()
				: (HashMap<String, Item>) DataPersistence.load("auctions");

		runningAuctions = (DataPersistence.load("runningAuctions") == null)
				? new ArrayList<>()
				: (ArrayList<Item>) DataPersistence.load("runningAuctions");

		textArea.setText((DataPersistence.load("textArea") == null)
				? ""
				: (String) DataPersistence.load("textArea"));

		f.setTitle("Server");
		f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				DataPersistence.save("registeredUsers", registeredUsers);
				DataPersistence.save("auctions", auctions);
				DataPersistence.save("runningAuctions", runningAuctions);
				DataPersistence.save("textArea", textArea.getText());
				System.exit(0);
			}
		});
		f.setSize(500, 500);
		f.setContentPane(new ScrollPane());
		textArea.setEditable(false);
		f.getContentPane().add(textArea);
		f.setVisible(true);

		Comms.serverStart();
		while (true) {
			Comms c = Comms.connect();
			print(c.nameTime(), "ACCEPTED CONNECTION");
			c.init();
			Thread x = new Thread(new Server(c));
			x.start();
		}
	}

	private static void print(String name, String str)
	{
		print(name + " : " + str);
	}

	private static void print(String str)
	{
		textArea.append(str + "\n");
	}

	@Override
	public void run()
	{
		try {
			while (c.isOpen()) {
				synchronized (runningAuctions) {
					Item i;
					while (runningAuctions.size() > 0 && (i = runningAuctions.get(0)).isEnded()) {
						synchronized (registeredUsers) {
							if (i.getUserID().equals(i.getHighestBid().getUserID())) {
								User u = registeredUsers.get(i.getUserID());
								u.sendNotification("Auction for \"" + i.getTitle() + "\" failed with no bids");
							} else {
								User u = registeredUsers.get(i.getHighestBid().getUserID());
								u.sendNotification("You won the auction for \"" + i.getTitle() + "\"!");
							}
						}
						runningAuctions.remove(i);
					}
				}
				Message m = c.getMessage();
				if (m instanceof RegisterUserMessage) {
					RegisterUserMessage u = (RegisterUserMessage) m;
					User user = new User(u.ID, u.name, u.familyName, u.hash);
					synchronized (registeredUsers) {
						if (registeredUsers.containsKey(user.getID())) {
							c.sendMessage(new StringMessage("register exists"));
						} else {
							registeredUsers.put(user.getID(), user);
							c.sendMessage(new StringMessage("register success"));
							print(c.nameTime(), user.getID() + " was registered");
						}
					}
				}
				if (m instanceof LoginMessage) {
					LoginMessage lm = (LoginMessage) m;
					synchronized (registeredUsers) {
						if (registeredUsers.containsKey(lm.ID)) {
							User u = registeredUsers.get(lm.ID);
							if (u.isOnline()) {
								c.sendMessage(new StringMessage("login invalid logged in"));
							} else if (u.getHash() == lm.hash) {
								c.sendMessage(new StringMessage("login success"));
								c.sendMessage(new ObjectMessage(u));
								u.setOnline(true);
								online++;
								print(c.nameTime(), u.getID() + " logged in");
							} else {
								c.sendMessage(new StringMessage("login invalid password"));
							}
						} else {
							c.sendMessage(new StringMessage("login invalid username"));
						}
					}
				}
				if (m instanceof RegisterItemMessage) {
					RegisterItemMessage rim = (RegisterItemMessage) m;
					synchronized (auctions) {
						int n = 1;
						String ID;
						do {
							ID = n + rim.getItem().getUserID() + rim.getItem().getTitle();
							n++;
						} while (auctions.containsKey(ID));
						rim.getItem().setID(ID);
						auctions.put(ID, rim.getItem());
					}

					synchronized (runningAuctions) {
						runningAuctions.add(rim.getItem());
						Collections.sort(runningAuctions, (o1, o2) ->
								o1.getCloseTime().compareTo(o2.getCloseTime()));
					}
					print(c.nameTime(), "registered item, ID: " + rim.getItem().getID());
					c.sendMessage(new StringMessage("auction success"));
				}
				if (m instanceof RequestItem) {
					RequestItem ri = (RequestItem) m;
					c.sendMessage(new ObjectMessage(auctions.get(ri.getItem())));
				}
				if (m instanceof RegisterBid) {
					RegisterBid rb = (RegisterBid) m;
					Item item;
					synchronized (auctions) {
						item = auctions.get(rb.getID());
						item.addBid(rb.getBid());
					}
					synchronized (runningAuctions) {
						runningAuctions.remove(item);
						runningAuctions.add(item);
						Collections.sort(runningAuctions, (o1, o2) ->
								o1.getCloseTime().compareTo(o2.getCloseTime()));
					}
				}
				if (m instanceof RequestUserList) {
					RequestUserList rul = (RequestUserList) m;
					ArrayList<Item> items = new ArrayList<>();
					synchronized (auctions) {
						for (Item i : auctions.values()) {
							if (i.getUserID().equals(rul.getUser().getID())) {
								items.add(i);
							}
						}
					}
					Collections.sort(items, (o1, o2) ->
							o1.getCloseTime().compareTo(o2.getCloseTime()));
					ArrayList<Item> bids = new ArrayList<>();
					synchronized (runningAuctions) {
						for (Item i : runningAuctions) {
							if (rul.getUser().getID().equals(i.getHighestBid().getUserID())
									&& !rul.getUser().getID().equals(i.getUserID())) {
								bids.add(i);
							}
						}
					}
					Collections.sort(bids, (o1, o2) ->
							o1.getCloseTime().compareTo(o2.getCloseTime()));
					c.sendMessage(new UserListsMessage(items, bids));
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.s.equals("auctions")) {
						synchronized (runningAuctions) {
							c.sendMessage(new ObjectMessage(runningAuctions));
						}
					}
				}
				if (m instanceof LogoutMessage) {
					LogoutMessage lo = (LogoutMessage) m;
					synchronized (registeredUsers) {
						User u = registeredUsers.get(lo.getUser().getID());
						u.setOnline(false);
						print(c.nameTime(), u.getID() + " logged out");
					}
				}
				if (m instanceof GetNotificationMessage) {
					GetNotificationMessage nm = (GetNotificationMessage) m;
					synchronized (registeredUsers) {
						User u = registeredUsers.get(nm.getUser().getID());
						String notif = u.getNotification();
						if (!notif.equals("")) {
							c.sendMessage(new NotificationMessage(notif));
						}
					}
				}
			}
			online--;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		print(c.nameTime(), "DISCONNECTED");
		/* If last man standing */
		if (online < 1) {
			synchronized (registeredUsers) {
				for (String u : registeredUsers.keySet()) {
					User user = registeredUsers.get(u);
					user.setOnline(false);
				}
			}
		}
	}

	private static final JFrame f = new JFrame();
	private static final JTextArea textArea = new JTextArea();
	/* Easier to check that no two users have the same ID */
	private static HashMap<String, User> registeredUsers;
	/* Easier to check that no two items have the same ID */
	private static HashMap<String, Item> auctions;
	/* Easier to find auctions that have ended */
	private static ArrayList<Item> runningAuctions;
	private static int online = 0;
	private final Comms c;
}
