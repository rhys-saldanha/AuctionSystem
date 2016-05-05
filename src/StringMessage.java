public class StringMessage implements Message
{
	StringMessage(int i, String s)
	{
		this.i = i;
		this.s = s;
	}

	public final int i;
	public final String s;
}
