package snippetlab.java.design_pattern.abstract_factory;

public class ShapeFactory extends AbstractFactory
{
	@Override
	public IShape getShape(String shapeType) {

		if(shapeType == null) {
			return null;
		}

		switch(shapeType)
		{
			case "RECTANGLE":
				return new Rectangle();

			case "SQUARE":
				return new Square();

			default:
				return null;
		}
	}
}