public class LoginMessage implements Message
{
	LoginMessage(String ID, String password)
	{
		this.ID = ID;
		this.hash = (ID + password).hashCode();
	}

	final String ID;
	final int hash;
}
