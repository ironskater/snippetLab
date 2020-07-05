package snippetlab.java.pitfalls.calling_method_from_constructor;

/**
 * https://stackoverflow.com/questions/18138397/calling-method-from-constructor
 *
 * !!!!!!!
 * Never call a non-final public method of a non-final class in it's constructor.
 * !!!!!!!
 */
public class App
{
	public static void
		main( String[] args )
	{
		Baseclass obj = new Subclass();

		obj.print();
	}
}
