package snippetlab.java.design_pattern.abstract_factory;

public class FactoryProducer
{
	public static AbstractFactory getFactory(boolean rounded)
	{
		return rounded ? new RoundedShapeFactory() : new ShapeFactory();
	}
}