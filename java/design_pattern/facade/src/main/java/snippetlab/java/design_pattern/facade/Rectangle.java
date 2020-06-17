package snippetlab.java.design_pattern.facade;

public class Rectangle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Rectangle::draw()");
	}
}