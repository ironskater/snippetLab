package snippetlab.java.generic.iface;

public class App implements iface<String>
{
	public static void main( String[] args ) throws Exception
	{
		App app = new App();

		System.out.println(app.getValue());
	}

	@Override
	public String getValue()
	{
		return "Hello world";
	}
}
