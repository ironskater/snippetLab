package snippetlab.java.design_pattern.factory;

public class ShapeFactory
{
	public IShape getShape(String shapeType) {

		if(shapeType == null) {
			return null;
		}

		switch(shapeType)
		{
			case "CIRCLE":
				return new Circle();

			case "RECTANGLE":
				return new Rectangle();

			case "SQUARE":
				return new Square();

			default:
				return null;
		}
	}
}