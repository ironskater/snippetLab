package snippetlab.java.design_pattern.factory;

public class Circle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Inside Circle::draw() method");
	}
}