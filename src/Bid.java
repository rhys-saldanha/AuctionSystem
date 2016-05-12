import java.io.Serializable;
import java.sql.Time;

public class Bid implements Serializable
{
	public Bid(String userID, String itemID, Time time, Integer amount)
	{
		this.userID = userID;
		this.itemID = itemID;
		this.time = time;
		this.amount = amount;
	}

	public Integer getAmount()
	{
		return amount;
	}

	public String getUserID()
	{
		return userID;
	}

	public Time getTime()
	{
		return time;
	}

	private final String userID;
	private final Time time;
	private final Integer amount;
	private final String itemID;
}
