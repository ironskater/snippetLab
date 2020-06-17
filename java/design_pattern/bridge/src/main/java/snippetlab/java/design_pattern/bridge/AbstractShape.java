package snippetlab.java.design_pattern.bridge;

public abstract class AbstractShape
{
	protected IDraw drawAPI;

	protected AbstractShape(IDraw drawAPI) {
		this.drawAPI = drawAPI;
	}

	public abstract void draw();
}