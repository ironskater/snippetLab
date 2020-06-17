package snippetlab.java.design_pattern.composite;

/**
 * Category: structural pattern
 *
 * Composite pattern is used where we need to treat a group of objects in similar way as a single object.
 * Composite pattern composes objects in term of a tree structure to represent part as well as whole hierarchy.
 * This type of design pattern comes under structural pattern as this pattern creates a tree structure of group of objects.
 *
 * This pattern creates a class that contains group of its own objects.
 * This class provides ways to modify its group of same objects.
 *
 * https://www.tutorialspoint.com/design_pattern/composite_pattern.htm
 */
public class App
{
	public static void
		main( String[] args )
	{
		Employee CEO = new Employee("John","CEO", 30000);

		Employee headSales = new Employee("Robert","Head Sales", 20000);

		Employee headMarketing = new Employee("Michel","Head Marketing", 20000);

		Employee clerk1 = new Employee("Laura","Marketing", 10000);
		Employee clerk2 = new Employee("Bob","Marketing", 10000);

		Employee salesExecutive1 = new Employee("Richard","Sales", 10000);
		Employee salesExecutive2 = new Employee("Rob","Sales", 10000);

		CEO.add(headSales);
		CEO.add(headMarketing);

		headSales.add(salesExecutive1);
		headSales.add(salesExecutive2);

		headMarketing.add(clerk1);
		headMarketing.add(clerk2);

		//print all employees of the organization
		System.out.println(CEO);

		System.out.println("======================");

		for (Employee headEmployee : CEO.getSubordinates()) {
			System.out.println(headEmployee);
			System.out.println("----------------------");

			for (Employee employee : headEmployee.getSubordinates()) {
				System.out.println(employee);
			}
			System.out.println("======================");
		}
	}
}
