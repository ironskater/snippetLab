package snippetlab.java.design_pattern.facade;

public class ShapeMaker
{
	private IShape circle;
	private IShape rectangle;
	private IShape square;

	public ShapeMaker() {
		circle = new Circle();
		rectangle = new Rectangle();
		square = new Square();
	}

	public void drawCircle() {
		circle.draw();
	}
	public void drawRectangle() {
		rectangle.draw();
	}
	public void drawSquare() {
		square.draw();
	}
}