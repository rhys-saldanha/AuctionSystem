import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ServerProtocol implements Runnable
{
	ServerProtocol(Comms c)
	{
		this.c = c;
	}

	@Override
	public void run()
	{
		System.out.println("Server thread created for " + c.name());
		/*while (true) {
			Message m = c.getMessage();
			if (m != null) {
				System.out.printf("%s : %s\n", c.name(), m.s);
			}
		}*/
		try {
			while (c.isOpen()) {
				ClientPanel p = this.makeRandomPane();
				c.sendPanel(p);
				Thread.sleep(100);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private ClientPanel makeScrollPane()
	{
		ClientPanel panels = new ClientPanel();
		ClientPanel out = new ClientPanel();
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

	private ClientPanel makeRandomPane()
	{
		ClientPanel p = new ClientPanel();
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		p.setBackground(new Color(r, g, b));
		return p;
	}

	private final Comms c;
}
