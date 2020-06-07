package snippetlab.java.design_pattern.command;

import java.util.ArrayList;
import java.util.List;

public final class CommandInvoker
{
	private List<ICommand> orderList = new ArrayList<>();

	public void addCmd(ICommand order) {
		orderList.add(order);
	}

	public void executeCmd() {
		for(ICommand order : orderList) {
			order.execute();
		}

		orderList.clear();
	}
}