package snippetlab.java.design_pattern.factory;

public class Rectangle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Inside Rectangle::draw() method");
	}
}