package snippetlab.java.design_pattern.builder;

/**
 * Category: Creational Pattern
 *
 * Builder pattern builds a complex object using simple objects and using a step by step approach.
 * A Builder class builds the final object step by step. This builder is independent of other objects.
 *
 * https://www.tutorialspoint.com/design_pattern/builder_pattern.htm
 */
public class App
{
	public static void
		main( String[] args )
	{
		MealBuilder mealBuilder = new MealBuilder();

		Meal vegMeal = mealBuilder.prepareVegMeal();
		System.out.println("Veg Meal");
		vegMeal.showItems();
		System.out.println("Total Cost: " + vegMeal.getCost());

		Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
		System.out.println("\n\nNon-Veg Meal");
		nonVegMeal.showItems();
		System.out.println("Total Cost: " + nonVegMeal.getCost());
	}
}
