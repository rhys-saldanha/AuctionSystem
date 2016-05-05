import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ClientOld
{

	ClientOld()
	{
		f = new JFrame();
	}

	public static void main(String[] args)
	{
		ClientOld c = new ClientOld();
		SwingUtilities.invokeLater(c::init);
	}

	private void init()
	{
		f.setTitle("Client");
		f.setContentPane(this.makeScrollPane());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setSize(500, 500);
		f.setVisible(true);
	}

	private JScrollPane makeScrollPane()
	{
		JPanel panels = new JPanel();
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

		return new JScrollPane(panels);
	}

	private final JFrame f;
}
