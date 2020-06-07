package snippetlab.java.design_pattern.command;

public final class SellStock implements ICommand
{
	private Stock abcStock;

	public SellStock(Stock abcStock) {
		this.abcStock = abcStock;
	}

	@Override
	public void execute() {
		abcStock.sell();
	}
}