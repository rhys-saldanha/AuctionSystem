public class RequestItem implements Message
{
	public RequestItem(String item)
	{
		this.item = item;
	}

	public String getItem()
	{
		return item;
	}

	private final String item;
}
