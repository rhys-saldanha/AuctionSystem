import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Item
{
	public Item(Integer ID, String title, String description, String userID, Integer reservePrice, Time startTime, Time closeTime)
	{
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.userID = userID;
		this.reservePrice = reservePrice;
		this.startTime = startTime;
		this.closeTime = closeTime;
		categories = new ArrayList<>();

		bids = new PriorityQueue<>(new Comparator<Bid>()
		{
			@Override
			public int compare(Bid o1, Bid o2)
			{
				return o2.getAmount() - o1.getAmount();
			}
		});
	}

	public boolean addBid(Bid b)
	{
		if (bids.peek() == null || b.getAmount() > bids.peek().getAmount()) {
			bids.add(b);
			return true;
		}
		return false;
	}

	public boolean addCategory(String c)
	{
		if (ALLOWEDCATEGORIES.contains(c.toLowerCase())) {
			return categories.add(c.toLowerCase());
		}
		return false;
	}

	public boolean removeCategory(String c)
	{
		return categories.remove(c);
	}

	public String getTitle()
	{
		return title;
	}

	public String getUserID()
	{
		return userID;
	}

	public Time getStartTime()
	{
		return startTime;
	}

	public Time getCloseTime()
	{
		return closeTime;
	}

	public Integer getID()
	{
		return ID;
	}

	public Integer getReservePrice()
	{
		return reservePrice;
	}

	public String getDescription()
	{
		return description;
	}

	public static final ArrayList<String> ALLOWEDCATEGORIES;

	static {
		ALLOWEDCATEGORIES = new ArrayList<>();
		String[] cat = new String[]{"antiques", "art", "books", "photography", "vehicles", "clothing", "computing",
				"film", "health", "home", "jewellery", "communication", "music", "instruments", "pet supplies",
				"sports", "toys", "other"};
		for (String i : cat) {
			ALLOWEDCATEGORIES.add(i);
		}
		ALLOWEDCATEGORIES.sort(new Comparator<String>()
		{
			@Override
			public int compare(String o1, String o2)
			{
				if (o1.equals("other")) {
					return 1;
				}
				if (o2.equals("other")) {
					return -1;
				}
				return o1.compareToIgnoreCase(o2);
			}
		});
	}

	private final String title;
	private final String userID;
	private final Time startTime;
	private final Time closeTime;
	private final Integer ID;
	private final Integer reservePrice;
	private String description;
	private ArrayList<String> categories;
	private PriorityQueue<Bid> bids;
}
