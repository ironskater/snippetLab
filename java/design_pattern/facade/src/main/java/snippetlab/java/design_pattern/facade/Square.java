package snippetlab.java.design_pattern.facade;

public class Square implements IShape
{
	@Override
	public void draw() {
		System.out.println("Square::draw()");
	}
}