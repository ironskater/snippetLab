package snippetlab.java.urlclassloader.general;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

// Ref
// https://examples.javacodegeeks.com/core-java/net/urlclassloader/java-net-urlclassloader-example/
// https://examples.javacodegeeks.com/core-java/net/urlclassloader/java-net-urlclassloader-example-2/

public final class App
{
	private App() {}
	public static void
		main(String[] args) throws Exception
	{
		// Getting the jar URL which contains target class
		URL[] classLoaderUrls =
			// java.net.MalformedURLException: no !/ in spec
			// new URL[]{ new URL("jar:file:/home/iot/data/Atelier/snippetLab/java/urlclassloader/general/target/general-1.0-SNAPSHOT.jar") }; // it not works
			// new URL[]{ new URL("jar:file:/home/iot/data/Atelier/snippetLab/java/urlclassloader/general/target/general-1.0-SNAPSHOT.jar!/") }; // it works
			new URL[]{ new URL("file:target/general-1.0-SNAPSHOT.jar") }; // it works

		// Create a new URLClassLoader
		URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

		// Load the target class
		Class<?> beanClass = urlClassLoader.loadClass("snippetlab.java.urlclassloader.general.pojos.Bean");
		Class<?> ceanClass = urlClassLoader.loadClass("snippetlab.java.urlclassloader.general.pojos.Cean");

		// Create a new instance from the loaded class
		Constructor<?> beanCtor = beanClass.getConstructor();
		Object beanObj = beanCtor.newInstance();

		Constructor<?> ceanCtor = ceanClass.getConstructor();
		Object ceanObj = ceanCtor.newInstance();

		// Getting a method from the loaded class and invoke it
		Method beanMethod = beanClass.getMethod("saySomething",
												String.class);
		beanMethod.invoke(	beanObj,
							"Hello");

		Method ceanMethod = ceanClass.getMethod("saySomething",
												String.class);
		ceanMethod.invoke(	ceanObj,
							"world");

		urlClassLoader.close();
	}
}
