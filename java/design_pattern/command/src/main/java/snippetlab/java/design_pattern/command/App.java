package snippetlab.java.design_pattern.command;

/**
 * Category: Behavioral Pattern
 *
 * Command pattern is a data driven design pattern and falls under behavioral pattern category.
 * A request is wrapped under an object as command and passed to invoker object.
 * Invoker object looks for the appropriate object which can handle this command
 * and passes the command to the corresponding object which executes the command.
 *
 * https://www.tutorialspoint.com/design_pattern/command_pattern.htm
 */
public class App
{
	public static void
		main( String[] args )
	{
		Stock abcStock = new Stock();

		ICommand buyStockOrder = new BuyStock(abcStock);
		ICommand sellStockOrder = new SellStock(abcStock);

		CommandInvoker cmdInvoker = new CommandInvoker();
		cmdInvoker.addCmd(buyStockOrder);
		cmdInvoker.addCmd(sellStockOrder);

		cmdInvoker.executeCmd();
	}
}
