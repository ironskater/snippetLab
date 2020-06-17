package snippetlab.java.design_pattern.decorator;

public abstract class ShapeDecorator implements IShape
{
	protected IShape decoratedShape;

	public ShapeDecorator(IShape decoratedShape) {
		this.decoratedShape = decoratedShape;
	}

	@Override
	public void draw() {
		decoratedShape.draw();
	}
}