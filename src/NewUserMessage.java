public class NewUserMessage implements Message
{
	NewUserMessage(String ID, String name, String familyName, int hash)
	{
		this.ID = ID;
		this.name = name;
		this.familyName = familyName;
		this.hash = hash;
	}

	String ID, name, familyName;
	int hash;
}
