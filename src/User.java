import java.io.Serializable;

public class User implements Serializable
{
	public User(String ID, String name, String familyName, int hash)
	{
		this.ID = ID;
		this.name = name;
		this.familyName = familyName;
		this.hash = hash;
		this.online = false;
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

	private final String name;
	private final String familyName;
	private final int hash;
	private final String ID;
	private boolean online;
}
