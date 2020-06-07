package snippetlab.java.design_pattern.command;

public final class BuyStock implements ICommand
{
	private Stock abcStock;

	public BuyStock(Stock abcStock) {
		this.abcStock = abcStock;
	}

	@Override
	public void execute() {
		abcStock.buy();
	}
}