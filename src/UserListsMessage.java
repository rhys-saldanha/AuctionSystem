import java.util.ArrayList;

/**
 * Created by Rhys on 13/05/2016.
 */
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
