public class GetNotificationMessage implements Message
{
	GetNotificationMessage(User u)
	{
		this.user = u;
	}

	public User getUser()
	{
		return user;
	}

	private final User user;
}
