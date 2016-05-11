import java.sql.Time;

public class Bid
{
	public Bid(String userID, Time time, Integer amount)
	{
		this.userID = userID;
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
}
