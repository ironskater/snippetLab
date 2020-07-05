package snippetlab.java.pitfalls.calling_method_from_constructor;

public class Subclass extends Baseclass
{
	private String testString = null;

	public Subclass() {
		super();
	}

	public void print() {
		System.out.println("testString is " + this.testString);
	}

	@Override
	protected void load() {
		testString = "test";
		System.out.println("testString is " + this.testString);
	}
}