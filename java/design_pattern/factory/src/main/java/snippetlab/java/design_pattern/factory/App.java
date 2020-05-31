package snippetlab.java.design_pattern.factory;

/**
 * Category: Creational Pattern
 *
 * In Factory pattern,
 * we create object without exposing the creation logic to the client
 * and refer to newly created object using a common interface.
 */
public class App
{
	public static void
		main( String[] args )
	{
		ShapeFactory shapeFactory = new ShapeFactory();

		//get an object of Circle and call its draw method.
		IShape shape1 = shapeFactory.getShape("CIRCLE");

		//call draw method of Circle
		shape1.draw();

		//get an object of Rectangle and call its draw method.
		IShape shape2 = shapeFactory.getShape("RECTANGLE");

		//call draw method of Rectangle
		shape2.draw();

		//get an object of Square and call its draw method.
		IShape shape3 = shapeFactory.getShape("SQUARE");

		//call draw method of square
		shape3.draw();
	}
}
