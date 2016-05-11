import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

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
		p.add(new JLabel("LOGIN"), gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Username"), gbc);

		JTextField tf_username = new JTextField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_username, gbc);

		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 200, 0, 0);
		p.add(new JLabel("Password"), gbc);

		JTextField tf_password = new JPasswordField();
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(0, 0, 0, 200);
		p.add(tf_password, gbc);

		gbc.gridwidth = 1;
		gbc.insets = null;

		JButton b_login = new JButton("Login");
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 0, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("New User?");
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_newUser, gbc);

		b_login.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				checkEmpty(tf_username);
				checkEmpty(tf_password);
				if (!tf_username.getText().equals("") && !tf_password.getText().equals("")) {
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

		JButton b_login = new JButton("Return to login");
		gbc.gridy = 6;
		gbc.insets = new Insets(0, 0, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("Register");
		gbc.gridx = 2;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_newUser, gbc);

		b_newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (!tf_password.getText().equals("") && !tf_password.getText().equals(tf_confirmPassword.getText())) {
					makeRed(tf_confirmPassword);
					makeErrorFrame("Password do not match");
				}
				if (checkEmpty(tf_username) && checkEmpty(tf_name) && checkEmpty(tf_password) && checkEmpty(tf_confirmPassword)
						&& checkEmpty(tf_familyname)) {
					c.sendMessage(new NewUserMessage(tf_username.getText(), tf_name.getText(),
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
		makeTopBar();

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

		gbc.weightx = 0.1;
		gbc.gridx = 0;
		p.add(makeRandomPanel(), gbc);

		gbc.weightx = 0.9;
		gbc.gridx = 1;
		p.add(makeRandomPanel(), gbc);

		this.setPanel(p);
	}

	private void makeTopBar()
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

		JButton b_user = new JButton(u.getName() + " " + u.getFamilyName());
		gbc.gridx = 3;
		p.add(b_user, gbc);

		this.topBar = p;
	}

	private JPanel makeRandomPanel()
	{
		JPanel p = new JPanel();
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		p.setBackground(new Color(r, g, b));
		p.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				makeLoginPage();
			}
		});
		return p;
	}

	public void makeErrorFrame(String str)
	{
		JOptionPane.showMessageDialog(null, str);
	}

	private boolean checkEmpty(JTextField tf)
	{
		if (tf.getText().equals("")) {
			makeRed(tf);
			makeErrorFrame("Cannot leave field empty");
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

	public void refresh()
	{
		f.revalidate();
		f.repaint();
	}

	public void setUser(User u)
	{
		this.u = u;
	}

	public final Comms c;
	public final JFrame f;
	private final Border borDefault = (new JTextField()).getBorder();
	private User u = null;
	private JPanel topBar;
}
