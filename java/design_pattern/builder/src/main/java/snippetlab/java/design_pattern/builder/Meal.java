package snippetlab.java.design_pattern.builder;

import java.util.ArrayList;
import java.util.List;

public class Meal
{
	private List<IItem> itemList = new ArrayList<IItem>();

	public void addItem(IItem item) {
		itemList.add(item);
	}

	public float getCost() {
		float cost = 0.0f;

		for(IItem item : itemList) {
			cost += item.price();
		}

		return cost;
	}

	public void showItems() {
		for(IItem item : itemList) {
			System.out.println("Item : " + item.name());
			System.out.println(", Packing : " + item.packing().pack());
			System.out.println(", Price : " + item.price());
		}
	}
}