import java.util.ArrayList;

public class UserListsMessage implements Message
{
	public UserListsMessage(ArrayList<Item> items, ArrayList<Item> bids)
	{
		this.items = items;
		this.bids = bids;
	}

	public ArrayList<Item> getItems()
	{
		return items;
	}

	public ArrayList<Item> getBids()
	{
		return bids;
	}

	private final ArrayList<Item> items;
	private final ArrayList<Item> bids;
}
