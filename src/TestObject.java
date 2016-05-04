import java.io.Serializable;

class TestObject implements Serializable
{
	TestObject(int v, String s)
	{
		this.value = v;
		this.id = s;
	}
	int value;
	String id;
}