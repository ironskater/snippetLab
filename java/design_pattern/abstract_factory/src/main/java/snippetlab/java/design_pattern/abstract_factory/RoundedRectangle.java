package snippetlab.java.design_pattern.abstract_factory;

public class RoundedRectangle implements IShape
{
	@Override
	public void draw() {
		System.out.println("Inside RoundedRectangle::draw() method");
	}
}