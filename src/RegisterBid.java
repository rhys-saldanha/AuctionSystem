public class RegisterBid implements Message
{
	public RegisterBid(String id, Bid bid)
	{
		this.ID = id;
		this.bid = bid;
	}

	public String getID()
	{
		return ID;
	}

	public Bid getBid()
	{
		return bid;
	}

	private final String ID;
	private final Bid bid;
}
