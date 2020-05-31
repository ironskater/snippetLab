package snippetlab.java.design_pattern.abstract_factory;

/**
 * Category: Creational Pattern
 * REF: https://www.tutorialspoint.com/design_pattern/abstract_factory_pattern.htm
 *
 * In Abstract Factory pattern an interface is responsible for creating a factory of related objects
 * without explicitly specifying their classes.
 * Each generated factory can give the objects as per the Factory pattern.
 */
public class App
{
	public static void
		main( String[] args )
	{
		//get non-rounded shape factory
		AbstractFactory shapeFactory = FactoryProducer.getFactory(false);
		//get an object of IShape Rectangle
		IShape shape1 = shapeFactory.getShape("RECTANGLE");
		//call draw method of IShape Rectangle
		shape1.draw();
		//get an object of IShape Square
		IShape shape2 = shapeFactory.getShape("SQUARE");
		//call draw method of IShape Square
		shape2.draw();

		//get rounded shape factory
		AbstractFactory shapeFactory1 = FactoryProducer.getFactory(true);
		//get an object of IShape Rectangle
		IShape shape3 = shapeFactory1.getShape("RECTANGLE");
		//call draw method of IShape Rectangle
		shape3.draw();
		//get an object of IShape Square
		IShape shape4 = shapeFactory1.getShape("SQUARE");
		//call draw method of IShape Square
		shape4.draw();
	}
}
