package snippetlab.java.design_pattern.decorator;

public class Circle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Shape: Circle");
	}
}