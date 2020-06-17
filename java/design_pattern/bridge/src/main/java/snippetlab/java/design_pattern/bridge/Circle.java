package snippetlab.java.design_pattern.bridge;

public class Circle extends AbstractShape
{
	private int x, y, radius;

	public Circle(int x, int y, int radius, IDraw drawAPI) {
		super(drawAPI);

		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	public void draw() {
		drawAPI.drawCircle(radius, x, y);
	}
}