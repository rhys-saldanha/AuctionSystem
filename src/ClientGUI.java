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
		setPanel(makeLogin());
	}

	public JPanel makeLogin()
	{
		JPanel p = new JPanel();
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 200, 0, 200);
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;

		JLabel l_login = new JLabel("LOGIN");
		gbc.gridy++;
		p.add(l_login, gbc);

		JTextField tf_username = new JTextField("Username");
		gbc.gridy++;
		p.add(tf_username, gbc);

		JTextField tf_password = new JPasswordField("Password");
		gbc.gridy++;
		p.add(tf_password, gbc);

		gbc.gridwidth = 1;
		gbc.insets = null;

		JButton b_login = new JButton("Login");
		gbc.gridy++;
		gbc.insets = new Insets(0, 200, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("New User");
		gbc.gridx++;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_newUser, gbc);

		Border bor_default = (new JTextField()).getBorder();

		b_login.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (tf_username.getText().equals("")) {
					tf_username.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				} else {
					tf_username.setBorder(bor_default);
				}
				if (tf_password.getText().equals("")) {
					tf_password.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				} else {
					tf_password.setBorder(bor_default);
				}
				if (!tf_username.getText().equals("") && !tf_password.getText().equals("")) {

				}
			}
		});

		b_newUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setPanel(makeNewUserPanel());
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

	private JPanel makeNewUserPanel()
	{
		JPanel p = new JPanel();
		p.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 200, 0, 200);
		gbc.weightx = 0.5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;

		JLabel l_login = new JLabel("LOGIN");
		gbc.gridy++;
		p.add(l_login, gbc);

		JTextField tf_username = new JTextField("Username");
		gbc.gridy++;
		p.add(tf_username, gbc);

		JTextField tf_password = new JPasswordField("Password");
		gbc.gridy++;
		p.add(tf_password, gbc);

		gbc.gridwidth = 1;
		gbc.insets = null;

		JButton b_login = new JButton("Login");
		gbc.gridy++;
		gbc.insets = new Insets(0, 200, 0, 20);
		p.add(b_login, gbc);

		JButton b_newUser = new JButton("New User");
		gbc.gridx++;
		gbc.insets = new Insets(0, 20, 0, 200);
		p.add(b_newUser, gbc);

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
				setPanel(makeButtonPanel());
			}
		});
		return p;
	}

	public JPanel makeButtonPanel()
	{
		JPanel p = new JPanel();
		JButton b_addUser = new JButton("Add user");
		JButton b_viewUsers = new JButton("View users");
		JButton b_randomPanel = new JButton("Random Panel");

		p.add(b_addUser);
		p.add(b_viewUsers);
		p.add(b_randomPanel);

		b_addUser.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Random r = new Random();
				c.sendMessage(new NewUserMessage(String.format("%s", r.nextInt()),
						String.format("%s", r.nextInt()),
						String.format("%s", r.nextInt()),
						r.nextInt()));
			}
		});
		b_viewUsers.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
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
			}
		});
		b_randomPanel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setPanel(makeRandomPanel());
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

	public void setPanel(JPanel p)
	{
		f.setContentPane(p);
		f.repaint();
	}

	private final Comms c;
	private final JFrame f;
}
