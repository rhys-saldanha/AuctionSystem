public class RegisterItemMessage implements Message
{

	RegisterItemMessage(Item item)
	{
		this.item = item;
	}

	public Item getItem()
	{
		return item;
	}

	private final Item item;
}
