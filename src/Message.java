import java.io.Serializable;

public class Message implements Serializable
{
	Message(int i, String s)
	{
		this.i = i;
		this.s = s;
	}

	public final int i;
	public final String s;
}
