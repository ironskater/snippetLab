package snippetlab.java.design_pattern.decorator;

public class Rectangle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Shape: Rectangle");
	}
}