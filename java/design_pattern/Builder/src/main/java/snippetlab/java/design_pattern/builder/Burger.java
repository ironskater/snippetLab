package snippetlab.java.design_pattern.builder;

// Create abstract classes implementing the item interface providing default functionalities.
public abstract class Burger implements IItem
{
	@Override
	public IPacking packing() {
		return new Wrapper();
	}

	@Override
	public abstract String name();

	@Override
	public abstract float price();
}