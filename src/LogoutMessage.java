public class LogoutMessage implements Message
{
	LogoutMessage(User u)
	{
		this.user = u;
	}

	public User getUser()
	{
		return user;
	}

	private final User user;
}
