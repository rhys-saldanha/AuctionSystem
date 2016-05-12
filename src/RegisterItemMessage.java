public class RegisterItemMessage implements Message
{
	RegisterItemMessage(Item item)
	{
		this(item.getTitle(), item);
	}

	private RegisterItemMessage(String ID, Item item)
	{
		this.ID = ID;
		this.item = item;
	}

	public String getID()
	{
		return ID;
	}

	public Item getItem()
	{
		return item;
	}

	private final String ID;
	private final Item item;
}
