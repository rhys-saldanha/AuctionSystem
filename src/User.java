import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	public User(String ID, String name, String familyName, int hash)
	{
		this.ID = ID;
		this.name = name;
		this.familyName = familyName;
		this.hash = hash;
		this.online = false;
		this.notifications = new ArrayList<>();
	}

	public String getName()
	{
		return name;
	}

	public String getID()
	{
		return ID;
	}

	public String getFamilyName()
	{
		return familyName;
	}

	public int getHash()
	{
		return hash;
	}

	public boolean isOnline()
	{
		return this.online;
	}

	public void setOnline(boolean b)
	{
		this.online = b;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;
		return ID.equals(user.getID()) && hash == (user.getHash());
	}

	public String getNotification()
	{
		try {
			return notifications.remove(0);
		} catch (IndexOutOfBoundsException ex) {
			return "";
		}
	}

	public void sendNotification(String n)
	{
		notifications.add(n);
	}

	private final String name;
	private final String familyName;
	private final int hash;
	private final String ID;
	private final ArrayList<String> notifications;
	private boolean online;
}
