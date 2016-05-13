public class NotificationMessage implements Message
{
	NotificationMessage(String s)
	{
		this.notification = s;
	}

	public String getNotification()
	{
		return notification;
	}

	private final String notification;
}
