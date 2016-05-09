import javax.swing.*;
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
		setPanel(makeButtonPanel());
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
		f.revalidate();
		f.repaint();
	}

	private final Comms c;
	private final JFrame f;
}
