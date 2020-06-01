package snippetlab.java.design_pattern.prototype;

public class Rectangle extends Shape
{
	public Rectangle() {
		type = "Rectangel";
	}

	@Override
	public void draw() {
		System.out.println("Inside Rectangle::draw() method");
	}
}