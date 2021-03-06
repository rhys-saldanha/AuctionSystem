public class RegisterUserMessage implements Message
{
	RegisterUserMessage(String ID, String name, String familyName, String password)
	{
		this.ID = ID;
		this.name = name;
		this.familyName = familyName;
		this.hash = (ID + password).hashCode();
	}

	final String ID, name, familyName;
	final int hash;
}
