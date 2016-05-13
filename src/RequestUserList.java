public class RequestUserList implements Message
{
	RequestUserList(User u)
	{
		this.user = u;
	}

	public User getUser()
	{
		return user;
	}

	private final User user;
}
