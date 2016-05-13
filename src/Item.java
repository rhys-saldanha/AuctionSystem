import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class Item implements Serializable
{

	Item(String title, String description, String userID, Double reservePrice, String category, Calendar startTime, Calendar closeTime)
	{
		this.title = title;
		this.description = description;
		this.userID = userID;
		this.startTime = startTime;
		this.closeTime = closeTime;
		this.category = category;
		this.bids = new ArrayList<>();
		bids.add(new Bid(this.userID, this.startTime, reservePrice));
	}

	/**
	 * Adds bid to item, min increase is £1
	 *
	 * @param b bid to place
	 * @return true if successful
	 */
	public boolean addBid(Bid b)
	{
		if (b.getAmount() - bids.get(0).getAmount() >= 1.0) {
			bids.add(b);
			Collections.sort(bids, (o1, o2) -> (int) (o2.getAmount() - o1.getAmount()));
			return true;
		}
		return false;
	}

	public Bid getHighestBid()
	{
		return this.bids.get(0);
	}

	public String getTitle()
	{
		return title;
	}

	public String getUserID()
	{
		return userID;
	}

	public Calendar getStartTime()
	{
		return startTime;
	}

	public Calendar getCloseTime()
	{
		return closeTime;
	}

	public String getID()
	{
		return ID;
	}

	public void setID(String id)
	{
		this.ID = id;
	}

	public String getDescription()
	{
		return description;
	}

	public String getCategory()
	{
		return category;
	}

	public String timeLeft()
	{
		if (this.isEnded()) {
			return "ENDED";
		} else {
			long diff = this.closeTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

			long day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			diff -= TimeUnit.MILLISECONDS.convert(day, TimeUnit.DAYS);

			long hour = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
			diff -= TimeUnit.MILLISECONDS.convert(hour, TimeUnit.HOURS);

			long min = Math.max(TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS), 1L);
			return String.format("%02d days, %02d hours, %02d minutes", day, hour, min);
		}
	}

	public boolean isEnded()
	{
		return Calendar.getInstance().getTimeInMillis() > this.closeTime.getTimeInMillis();
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Item i = (Item) o;
		return ID.equals(i.getID());
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
	private final Calendar startTime;
	private final Calendar closeTime;
	private final String description;
	private final ArrayList<Bid> bids;
	private final String category;
	private String ID;
}
