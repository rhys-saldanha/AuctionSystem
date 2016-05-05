import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

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

	private static void init()
	{
		while (true) {
			Comms c = Comms.connect();
			if (c != null) {
				c.init();
				Thread x = new Thread(new Server(c));
				x.start();
			}
		}
	}

	@Override
	public void run()
	{
		System.out.println(c.name() + " : STARTED");
		try {
			while (c.isOpen()) {
				Message m = c.getMessage();
				if (m instanceof StringMessage) {
					StringMessage sm = (StringMessage) m;
					if (sm.i == 1) {
						Server.names.add(sm.s);
					} else if (sm.i == 2) {
						Iterator<String> it = names.iterator();
						while (it.hasNext()) {
							c.sendMessage(new StringMessage(2, it.next()));
						}
						c.sendMessage(new StringMessage(0, ""));
					} else {
						System.out.printf("%s : %s\n", c.name(), sm.s);
					}
				}
				ClientPanel p = this.makeRandomPane();
				c.sendMessage(p);
				Thread.sleep(100);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println(c.name() + " : DISCONNECTED");
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

	private static ConcurrentSkipListSet<String> names = new ConcurrentSkipListSet<>();
	private final Comms c;
}
