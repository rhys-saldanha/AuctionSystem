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
		setPanel(makeLoginPage());
	}

	public JPanel makeLoginPage()
	{
		JPanel p = new JPanel();
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/* Sets GridBagConstraints used for whole pane */
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 200, 0, 200);
		gbc.gridx = 0;

		JLabel l_login = new JLabel("LOGIN");
		gbc.gridy = 0;
		p.add(l_login, gbc);

		JTextField tf_username = new JTextField("Username");
		gbc.gridy = 1;
		p.add(tf_username, gbc);

		JTextField tf_password = new JPasswordField("Password");
		gbc.gridy = 2;
		p.add(tf_password, gbc);

		gbc.gridwidth = 1;
		gbc.insets = null;

		JButton b_login = new JButton("Login");
		gbc.gridy = 3;
		gbc.insets = new Insets(0, 200, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("New User");
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_newUser, gbc);

		tf_username.addMouseListener(new DeleteDefault(tf_username));
		tf_password.addMouseListener(new DeleteDefault(tf_password));

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
				setPanel(makeNewUserPage());
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

		return p;
	}

	public JPanel makeNewUserPage()
	{
		JPanel p = new JPanel();
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/* Sets GridBagConstraints used for whole pane */
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 200, 0, 200);
		gbc.gridx = 0;

		JLabel l_login = new JLabel("REGISTER");
		gbc.gridy = 0;
		p.add(l_login, gbc);

		JTextField tf_name = new JTextField("Name");
		gbc.gridy = 1;
		p.add(tf_name, gbc);

		JTextField tf_familyname = new JTextField("Family name");
		gbc.gridy++;
		p.add(tf_familyname, gbc);

		JTextField tf_username = new JTextField("Username");
		gbc.gridy++;
		p.add(tf_username, gbc);

		JTextField tf_password = new JPasswordField("Password");
		gbc.gridy++;
		p.add(tf_password, gbc);

		JTextField tf_confirmPassword = new JPasswordField("Password");
		gbc.gridy++;
		p.add(tf_confirmPassword, gbc);

		gbc.gridwidth = 1;
		gbc.insets = null;

		JButton b_login = new JButton("Login");
		gbc.gridy++;
		gbc.insets = new Insets(0, 200, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("Register");
		gbc.gridx = 1;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_newUser, gbc);

		tf_username.addMouseListener(new DeleteDefault(tf_username));
		tf_name.addMouseListener(new DeleteDefault(tf_name));
		tf_familyname.addMouseListener(new DeleteDefault(tf_familyname));
		tf_password.addMouseListener(new DeleteDefault(tf_password));
		tf_confirmPassword.addMouseListener(new DeleteDefault(tf_confirmPassword));

		b_newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (checkEmpty(tf_password) && checkEmpty(tf_confirmPassword)
						&& !tf_password.getText().equals(tf_confirmPassword.getText())) {
					makeRed(tf_confirmPassword);
					makeErrorFrame("Password do not match");
				} else if (checkEmpty(tf_username) && checkEmpty(tf_name)
						&& checkEmpty(tf_familyname)) {
					c.sendMessage(new NewUserMessage(tf_username.getText(), tf_name.getText(),
							tf_familyname.getText(), tf_password.getText()));
					setPanel(makeLoginPage());
				}
			}
		});

		return p;
	}

	public JPanel makeRandomPanel()
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
				setPanel(makeLoginPage());
			}
		});
		return p;
	}

	public JPanel makeScrollPanel()
	{
		JPanel panels = new JPanel();
		JPanel out = new JPanel();
		panels.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 50;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;

		for (int i = 0; i < 20; i++) {
			JPanel x = new JPanel();
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			x.setBackground(new Color(r, g, b));
			panels.add(x, gbc);
			gbc.gridy++;
		}

		out.add(new JScrollPane(panels));

		return out;
	}

	public void makeErrorFrame(String str)
	{
		JOptionPane.showMessageDialog(null, str);
	}

	private boolean checkEmpty(JTextField tf)
	{
		if (tf.getText().equals("")) {
			makeRed(tf);
			makeErrorFrame("Cannot leave " + tf.getName() + " field empty");
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

	public void setPanel(JPanel p)
	{
		f.setContentPane(p);
		f.revalidate();
		f.repaint();
	}

	private class DeleteDefault extends MouseAdapter
	{
		DeleteDefault(JTextField tf)
		{
			this(tf, tf.getText());
		}

		DeleteDefault(JTextField tf, String str)
		{
			this.tf = tf;
			this.str = str;
		}

		public void mouseClicked(MouseEvent ev)
		{
			if (tf.getText().equals(str)) {
				tf.setText("");
				f.revalidate();
				f.repaint();
			}
		}

		private final JTextField tf;

		private final String str;
	}

	public final Comms c;
	public final JFrame f;
	private final Border borDefault = (new JTextField()).getBorder();
}
