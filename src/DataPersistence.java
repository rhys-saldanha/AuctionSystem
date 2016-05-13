import java.io.*;

public class DataPersistence
{
	public static void save(String str, Object o)
	{
		synchronized (sync) {
			try {
				FileOutputStream out = new FileOutputStream("files/" + str);
				ObjectOutputStream oos = new ObjectOutputStream(out);
				oos.writeObject(o);
				oos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Object load(String str)
	{
		synchronized (sync) {
			try {
				FileInputStream in = new FileInputStream("files/" + str);
				ObjectInputStream ois = new ObjectInputStream(in);
				return ois.readObject();
			} catch (FileNotFoundException ex) {
				return null;
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	private static final Integer sync = 0;
}
