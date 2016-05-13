import java.io.Serializable;
import java.util.Calendar;

public class Bid implements Serializable
{
	public Bid(String userID, Calendar time, Double amount)
	{
		this.userID = userID;
		this.time = time;
		this.amount = amount;
	}

	public Double getAmount()
	{
		return amount;
	}

	public String getUserID()
	{
		return userID;
	}

	public Calendar getTime()
	{
		return time;
	}

	private final String userID;
	private final Calendar time;
	private final Double amount;
}
