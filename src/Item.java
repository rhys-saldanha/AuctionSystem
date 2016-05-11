import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Item implements Serializable
{
	public Item(String title, String description, String userID, Integer reservePrice, String category, Time startTime, Time closeTime)
	{
		this(userID + title, title, description, userID, reservePrice, category, startTime, closeTime);
	}

	public Item(String ID, String title, String description, String userID, Integer reservePrice, String category, Time startTime, Time closeTime)
	{
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.userID = userID;
		this.reservePrice = reservePrice;
		this.startTime = startTime;
		this.closeTime = closeTime;
		this.category = category;

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

	public boolean setCategory(String c)
	{
		return !(category = (ALLOWEDCATEGORIES.contains(c.toLowerCase())) ? c : "").equals("");
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

	public String getID()
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
		Collections.addAll(ALLOWEDCATEGORIES, cat);
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
	private final String ID;
	private final Integer reservePrice;
	private final String description;
	private final PriorityQueue<Bid> bids;
	private String category;
}
