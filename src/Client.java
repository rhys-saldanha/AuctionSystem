import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Client implements Runnable
{
	Client()
	{
		this.c = new Comms();
		this.f = new JFrame();
	}

	public static void main(String[] args)
	{
		(new Client()).run();
	}

	/*@Override
	public void run()
	{
		this.init();
		Scanner s = new Scanner(System.in);
		while (c.isOpen()) {
			//String input = s.nextLine();
			String input = "";
			if (input.contains("NAME")) {
				c.sendMessage(new StringMessage(1, input.replace("NAME ", "")));
			} else if (input.equals("LIST")) {
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
				continue;
			} else {
				c.sendMessage(new StringMessage(0, input));
			}
			Message m = c.getMessage();
			if (m != null) {
				if (m instanceof ClientPanel) {
					ClientPanel p = (ClientPanel) m;
					f.setContentPane(p);
					f.revalidate();
					f.repaint();
				}
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					System.out.println(sm.s);
				}
			}
		}
	}*/

	@Override
	public void run()
	{
		this.init();
	}

	private void setPanel(JPanel p)
	{
		f.setContentPane(p);
		f.revalidate();
		f.repaint();
	}

	private void exit()
	{
		c.close();
		f.dispose();
		System.exit(0);
	}

	private void init()
	{
		c.init();
		f.setTitle("Client");
		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event)
			{
				exit();
			}
		});
		setPanel(makeButtonPanel());
		f.setSize(500, 500);
		f.setVisible(true);
	}

	private JPanel makeScrollPanel()
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

	private JPanel makeButtonPanel()
	{
		JPanel p = new JPanel();
		JButton b_1 = new JButton("Add name");
		JButton b_2 = new JButton("View names");

		p.add(b_1);
		p.add(b_2);

		b_1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				c.sendMessage(new StringMessage(1, "dan"));
			}
		});
		b_2.addActionListener(new ActionListener()
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

	private final JFrame f;
	private final Comms c;
}