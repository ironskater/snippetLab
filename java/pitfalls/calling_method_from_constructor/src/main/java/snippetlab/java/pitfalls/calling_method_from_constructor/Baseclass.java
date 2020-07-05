package snippetlab.java.pitfalls.calling_method_from_constructor;

public abstract class Baseclass
{
	public Baseclass() {
		this.load();
	}

	public abstract void print();

	protected void load() {

	}
}