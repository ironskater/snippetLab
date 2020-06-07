package snippetlab.java.design_pattern.filter;

import java.util.ArrayList;
import java.util.List;

public final class CriteriaSingle implements ICriteria
{
	@Override
	public List<Person> meetCriteria(List<Person> persons) {
		List<Person> singlePersons = new ArrayList<Person>();

		for (Person person : persons) {
			if(person.getMaritalStatus().equalsIgnoreCase("SINGLE")) {
				singlePersons.add(person);
			}
		}

		return singlePersons;
	}
}