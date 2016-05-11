public class ObjectMessage implements Message
{
	ObjectMessage(Object o)
	{
		this.o = o;
	}

	public Object getObject()
	{
		return o;
	}

	private Object o;
}
