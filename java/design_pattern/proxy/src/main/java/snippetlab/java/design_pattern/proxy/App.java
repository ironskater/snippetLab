package snippetlab.java.design_pattern.proxy;

/**
 * Category: Structural Pattern
 *
 * In proxy pattern, a class represents functionality of another class.
 * we create object having original object to interface its functionality to outer world.
 *
 * https://www.tutorialspoint.com/design_pattern/proxy_pattern.htm
 */
public class App
{
	public static void
		main( String[] args )
	{
		IImage image = new ProxyImage("test_img.jpg");

		// image will be loaded from disk
		image.display();
		System.out.println("");

		// image will not be loaded from disk
		image.display();
	}
}
