package snippetlab.java.design_pattern.factory;

public class Square implements IShape
{
	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method");
	}
}