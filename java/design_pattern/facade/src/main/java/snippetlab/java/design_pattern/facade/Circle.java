package snippetlab.java.design_pattern.facade;

public class Circle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Circle::draw()");
	}
}