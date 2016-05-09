import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

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
		c.init();
		this.f = new JFrame();
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
		f.setSize(500, 500);
		f.setVisible(true);
		setPanel(makeButtonPanel());
	}

	public void setPanel(JPanel p)
	{
		f.setContentPane(p);
		f.revalidate();
		f.repaint();
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
				Random rand = new Random();
				float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
				p.setBackground(new Color(r, g, b));
				c.sendMessage(new StringMessage(0, "spam"));
			}
		});
		return p;
	}

	public JPanel makeButtonPanel()
	{
		JPanel p = new JPanel();
		JButton b_addUser = new JButton("Add user");
		JButton b_viewUsers = new JButton("View users");

		p.add(b_addUser);
		p.add(b_viewUsers);

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

	private void exit()
	{
		c.close();
		f.dispose();
		System.exit(0);
	}

	private final Comms c;
	private JFrame f;
}