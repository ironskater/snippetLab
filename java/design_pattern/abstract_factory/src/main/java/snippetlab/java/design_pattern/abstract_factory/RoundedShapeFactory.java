package snippetlab.java.design_pattern.abstract_factory;

public class RoundedShapeFactory extends AbstractFactory
{
	@Override
	public IShape getShape(String shapeType) {

		if(shapeType == null) {
			return null;
		}

		switch(shapeType)
		{
			case "RECTANGLE":
				return new RoundedRectangle();

			case "SQUARE":
				return new RoundedSquare();

			default:
				return null;
		}
	}
}