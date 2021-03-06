import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ClientGUI
{
	ClientGUI(JFrame f, Comms c)
	{
		this.f = f;
		this.c = c;
	}

	public void init()
	{
		/* Sets initial panel to login page */
		makeLoginPage();
	}

	public void makeLoginPage()
	{
		JPanel p = new JPanel(new GridBagLayout());
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints gbc = new GridBagConstraints();

		/* Sets GridBagConstraints used for whole pane */
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		p.add(new JLabel("LOGIN"), gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		p.add(new JLabel("Username"), gbc);

		JTextField tf_username = new JTextField();
		tf_username.requestFocusInWindow();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		gbc.gridy = 1;
		p.add(tf_username, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		p.add(new JLabel("Password"), gbc);

		JTextField tf_password = new JPasswordField();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		gbc.gridy = 2;
		p.add(tf_password, gbc);

		gbc.gridwidth = 1;
		gbc.gridy = 3;

		JButton b_login = new JButton("Login");
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 0, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("New User?");
		gbc.gridx = 3;
		gbc.insets = new Insets(0, 20, 0, 0);
		p.add(b_newUser, gbc);

		gbc.gridheight = 4;

		gbc.gridx = 0;
		gbc.gridy = 0;
		p.add(new JPanel(), gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		p.add(new JPanel(), gbc);

		b_login.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (checkEmpty(tf_username) && checkEmpty(tf_password)) {
					c.sendMessage(new LoginMessage(tf_username.getText(), tf_password.getText()));
				}
			}
		});
		b_newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				makeNewUserPage();
			}
		});

		/*button = new JButton("5");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		p.add(button, c);*/

		this.setPanel(p);
	}

	public void makeNewUserPage()
	{
		JPanel p = new JPanel();
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/* Sets GridBagConstraints used for whole pane */
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("REGISTER"), gbc);

		/* Name */
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Name"), gbc);

		JTextField tf_name = new JTextField();
		tf_name.requestFocusInWindow();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_name, gbc);

		/* Family name */
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Family name"), gbc);

		JTextField tf_familyname = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_familyname, gbc);

		/* User name */
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Username"), gbc);

		JTextField tf_username = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_username, gbc);

		/* Password */
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Password"), gbc);

		JTextField tf_password = new JPasswordField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_password, gbc);

		/* Confirm password */
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Confirm Password"), gbc);

		JTextField tf_confirmPassword = new JPasswordField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_confirmPassword, gbc);

		gbc.gridwidth = 1;
		gbc.insets = null;

		JButton b_newUser = new JButton("Register");
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 0, 20);
		p.add(b_newUser, gbc);

		JButton b_login = new JButton("Return to login");
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_login, gbc);

		b_newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!tf_password.getText().equals("") && !tf_password.getText().equals(tf_confirmPassword.getText())) {
					makeRed(tf_confirmPassword);
					makePopUpFrame("Password do not match");
				}
				if (checkEmpty(tf_username) && checkEmpty(tf_name) && checkEmpty(tf_password) && checkEmpty(tf_confirmPassword)
						&& checkEmpty(tf_familyname)) {
					c.sendMessage(new RegisterUserMessage(tf_username.getText(), tf_name.getText(),
							tf_familyname.getText(), tf_password.getText()));
				}
			}
		});

		b_login.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				makeLoginPage();
			}
		});

		this.setPanel(p);
	}

	public void makeMainPage()
	{
		JPanel p = new JPanel(new GridBagLayout());
		p.setBackground(Color.GREEN);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		gbc.weighty = 0.1;
		gbc.gridy = 0;

		gbc.weightx = 0.1;
		gbc.gridx = 0;
		p.add(new JLabel("AUCTIONS"));

		gbc.weightx = 0.9;
		gbc.gridx = 1;
		p.add(topBar, gbc);

		gbc.weighty = 0.9;
		gbc.gridy = 1;

		JPanel side = new JPanel();
		side.setBackground(Color.GREEN);
		gbc.weightx = 0.1;
		gbc.gridx = 0;
		p.add(side, gbc);

		gbc.weightx = 0.9;
		gbc.gridx = 1;
		p.add(content, gbc);

		this.setPanel(p);
	}

	public void makeAuctionContent(ArrayList<Item> auctions)
	{
		JPanel p = new JPanel(new BorderLayout());

		DefaultTableModel model = new DefaultTableModel(new Object[]{
				"ItemID",
				"UserID",
				"Title",
				"Description",
				"Highest bid",
				"Time left"
		}, 0)
		{
			@Override
			public boolean isCellEditable(int r, int c)
			{
				return false;
			}
		};
		for (Object o : auctions) {
			Item i = (Item) o;
			model.addRow(new Object[]{
					i.getID(),
					i.getUserID(),
					i.getTitle(),
					i.getDescription(),
					"£" + i.getHighestBid().getAmount(),
					i.timeLeft()
			});
		}

		JTable t = new JTable(model);

		p.add(t.getTableHeader(), BorderLayout.NORTH);
		p.add(t, BorderLayout.CENTER);

		t.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent ev)
			{
				int row = t.getSelectedRow();
				int column = 0;
				if (!(row < 0)) {
					c.sendMessage(new RequestItem((String) t.getValueAt(row, column)));
				}
			}
		});

		this.content = p;
	}

	public void makeAddItemContent()
	{
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;

		/* Title */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		p.add(new JLabel("Title"), gbc);

		JTextField tf_title = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		gbc.gridy = 1;
		p.add(tf_title, gbc);

		/* Description */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy++;
		p.add(new JLabel("Description"), gbc);

		JTextField tf_description = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		p.add(tf_description, gbc);

		/* Reserve Price */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy++;
		p.add(new JLabel("Reserve price (£)"), gbc);

		JTextField tf_reservePrice = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		p.add(tf_reservePrice, gbc);

		/* Category */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy++;
		p.add(new JLabel("Category"), gbc);

		JComboBox<String> c_category = new JComboBox<>();
		for (String s : Item.ALLOWEDCATEGORIES) {
			c_category.addItem(s);
		}
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		c_category.setSelectedItem("other");
		p.add(c_category, gbc);

		/* Close Time */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy++;
		p.add(new JLabel("Close Time"), gbc);

		JPanel time = new JPanel(new GridBagLayout());
		GridBagConstraints gbc2 = new GridBagConstraints();

		/* Sets GridBagConstraints used for time section */
		gbc2.weightx = 0.5;
		gbc2.fill = GridBagConstraints.HORIZONTAL;

		Calendar timeShow = Calendar.getInstance();
		timeShow.add(1, Calendar.MINUTE);

		/* Hour */
		JComboBox<Integer> c_hour = new JComboBox<>();
		for (int i = 0; i < 24; i++) {
			c_hour.addItem(i);
		}
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		c_hour.setSelectedItem(timeShow.get(Calendar.HOUR));
		time.add(c_hour, gbc2);

		/* : */
		gbc2.gridx++;
		time.add(new JLabel(" :"), gbc2);

		/* Minute */
		JComboBox<Integer> c_min = new JComboBox<>();
		for (int i = 0; i < 60; i++) {
			c_min.addItem(i);
		}
		gbc2.gridx++;
		c_min.setSelectedItem(timeShow.get(Calendar.MINUTE));
		time.add(c_min, gbc2);

		/* - */
		gbc2.gridx++;
		time.add(new JLabel(" -"), gbc2);

		/* Day */
		JComboBox<Integer> c_day = new JComboBox<>();
		for (int i = 1; i < 32; i++) {
			c_day.addItem(i);
		}
		gbc2.gridx++;
		c_day.setSelectedItem(timeShow.get(Calendar.DAY_OF_MONTH));
		time.add(c_day, gbc2);

		/* / */
		gbc2.gridx++;
		time.add(new JLabel(" /"), gbc2);

		/* Month */
		JComboBox<Integer> c_month = new JComboBox<>();
		for (int i = 1; i < 13; i++) {
			c_month.addItem(i);
		}
		gbc2.gridx++;
		c_month.setSelectedItem(timeShow.get(Calendar.MONTH) + 1);
		time.add(c_month, gbc2);

		/* / */
		gbc2.gridx++;
		time.add(new JLabel(" /"), gbc2);

		/* Year */
		JComboBox<Integer> c_year = new JComboBox<>();
		for (int i = LocalDate.now().getYear(); i < LocalDate.now().getYear() + 2; i++) {
			c_year.addItem(i);
		}
		gbc2.gridx++;
		c_year.setSelectedItem(LocalDate.now().getYear());
		time.add(c_year, gbc2);

		gbc.gridwidth = 2;
		gbc.gridx = 2;
		p.add(time, gbc);

		JButton b_newUser = new JButton("Register Item");
		gbc.gridy++;
		p.add(b_newUser, gbc);

		/* Padding */
		gbc.gridheight = gbc.gridy + 1;
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 0;
		p.add(new JPanel(), gbc);

		gbc.gridx = 4;
		p.add(new JPanel(), gbc);

		b_newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (checkEmpty(tf_title) && checkEmpty(tf_reservePrice)) {
					String regex = "[0-9]+([.][0-9]{1,2})?";
					if (tf_reservePrice.getText().matches(regex)) {
						int year = (Integer) c_year.getSelectedItem();
						int month = (Integer) c_month.getSelectedItem();
						int day = (Integer) c_day.getSelectedItem();
						if (isDateValid(year, month, day)) {
							Double price = Double.parseDouble(tf_reservePrice.getText());
							int hour = (Integer) c_hour.getSelectedItem();
							int min = (Integer) c_min.getSelectedItem();
							String categoryChosen = (String) c_category.getSelectedItem();
							Calendar closeTime = new GregorianCalendar(year, month - 1, day, hour, min);
							c.sendMessage(new RegisterItemMessage(
											new Item(
													tf_title.getText(),
													tf_description.getText(),
													user.getID(),
													price,
													categoryChosen,
													Calendar.getInstance(),
													closeTime
											)
									)
							);
						} else {
							makePopUpFrame("Invalid date");
						}
					} else {
						makePopUpFrame("Incorrect price format, use '.' separator");
					}
				}
			}
		});

		this.content = p;
	}

	public void makeAddBidContent(Item item)
	{
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;

		/* Title */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 1;
		p.add(new JLabel(item.getTitle() + " sold by " + item.getUserID()), gbc);

		/* Current highest */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy++;
		p.add(new JLabel("Current highest bid value"), gbc);
		gbc.gridx++;
		p.add(new JLabel("" + item.getHighestBid().getAmount()), gbc);

		/* Bid Value */
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy++;
		p.add(new JLabel("Bid value (£)"), gbc);

		JTextField tf_bidValue = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 2;
		p.add(tf_bidValue, gbc);

		/* Submit */
		JButton b_submit = new JButton("Submit");
		gbc.gridwidth = 1;
		gbc.gridx = 2;
		gbc.gridy++;
		p.add(b_submit, gbc);

		/* Padding */
		gbc.gridheight = gbc.gridy + 1;
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = 0;
		p.add(new JPanel(), gbc);

		gbc.gridx = 4;
		p.add(new JPanel(), gbc);

		b_submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (checkEmpty(tf_bidValue)) {
					if (user.getID().equals(item.getUserID())) {
						makePopUpFrame("You cannot bid on your own item");
					} else {
						String regex = "[0-9]+([.][0-9]{1,2})?";
						if (tf_bidValue.getText().matches(regex)) {
							Bid bid = new Bid(user.getID(),
									Calendar.getInstance(),
									Double.parseDouble(tf_bidValue.getText())
							);
							if (item.addBid(bid)) {
								c.sendMessage(new RegisterBid(item.getID(), bid));
								makePopUpFrame("Bid successful");
							} else {
								makePopUpFrame("Bid value below highest bid");
							}
						} else {
							makePopUpFrame("BInvalid bid value");
						}
					}
				}
			}
		});

		this.content = p;
	}

	public void makeUserContent(ArrayList<Item> items, ArrayList<Item> bids)
	{
		JPanel c = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;

		DefaultTableModel itemModel = new DefaultTableModel(new Object[]{
				"ItemID",
				"Title",
				"Description",
				"Highest bid",
				"Time left"
		}, 0)
		{
			@Override
			public boolean isCellEditable(int r, int c)
			{
				return false;
			}
		};
		for (Object o : items) {
			Item i = (Item) o;
			itemModel.addRow(new Object[]{
					i.getID(),
					i.getTitle(),
					i.getDescription(),
					"£" + i.getHighestBid().getAmount(),
					i.timeLeft()
			});
		}

		JTable t = new JTable(itemModel);
		JPanel p = new JPanel(new BorderLayout());
		p.add(t.getTableHeader(), BorderLayout.NORTH);
		p.add(t, BorderLayout.CENTER);
		c.add(p, gbc);
		gbc.gridy++;

		DefaultTableModel bidModel = new DefaultTableModel(new Object[]{
				"UserID",
				"Title",
				"Description",
				"Highest bid",
				"Time left"
		}, 0)
		{
			@Override
			public boolean isCellEditable(int r, int c)
			{
				return false;
			}
		};
		for (Object o : bids) {
			Item i = (Item) o;
			bidModel.addRow(new Object[]{
					i.getUserID(),
					i.getTitle(),
					i.getDescription(),
					"£" + i.getHighestBid().getAmount(),
					i.timeLeft()
			});
		}

		t = new JTable(itemModel);
		p = new JPanel(new BorderLayout());
		p.add(t.getTableHeader(), BorderLayout.NORTH);
		p.add(t, BorderLayout.CENTER);
		c.add(p, gbc);
		this.content = c;
	}

	public void makeNavBar()
	{
		JPanel p = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JButton b_items = new JButton("Show auctions");
		gbc.gridx = 1;
		p.add(b_items, gbc);

		JButton b_sell = new JButton("Sell an item");
		gbc.gridx = 2;
		p.add(b_sell, gbc);

		JButton b_user = new JButton(user.getName() + " " + user.getFamilyName());
		gbc.gridx = 3;
		p.add(b_user, gbc);

		JButton b_signout = new JButton("Sign out");
		gbc.gridx = 4;
		p.add(b_signout, gbc);

		b_items.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.sendMessage(new StringMessage("auctions"));
			}
		});
		b_sell.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				makeAddItemContent();
				makeMainPage();
			}
		});
		b_user.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.sendMessage(new RequestUserList(user));
			}
		});
		b_signout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				logOut();
				makeLoginPage();
			}
		});

		this.topBar = p;
	}

	public void makePopUpFrame(String str)
	{
		JOptionPane.showMessageDialog(null, str);
	}

	private boolean checkEmpty(JTextField tf)
	{
		if (tf.getText().equals("")) {
			makeRed(tf);
			makePopUpFrame("Cannot leave field empty");
			return false;
		}
		makeDefault(tf);
		return true;
	}

	public void makeRed(JTextField tf)
	{
		tf.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
	}

	public void makeDefault(JTextField tf)
	{
		tf.setBorder(borDefault);
	}

	private void setPanel(JPanel p)
	{
		f.setContentPane(p);
		f.revalidate();
		f.repaint();
	}

	public void logOut()
	{
		if (user != null) {
			c.sendMessage(new LogoutMessage(user));
			user = null;
		}
	}

	private boolean isDateValid(int year, int month, int day)
	{
		boolean dateIsValid = true;
		try {
			LocalDate.of(year, month, day);
		} catch (DateTimeException e) {
			dateIsValid = false;
		}
		return dateIsValid;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User u)
	{
		this.user = u;
	}

	public final Comms c;
	public final JFrame f;
	private final Border borDefault = (new JTextField()).getBorder();
	private User user = null;
	private JPanel topBar;
	private JPanel content;
}
