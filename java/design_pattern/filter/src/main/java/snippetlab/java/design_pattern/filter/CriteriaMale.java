package snippetlab.java.design_pattern.filter;

import java.util.ArrayList;
import java.util.List;

public final class CriteriaMale implements ICriteria
{
	@Override
	public List<Person> meetCriteria(List<Person> personList) {
		List<Person> malePersons = new ArrayList<Person>();

		for(Person person : personList) {
			if(person.getGender().equalsIgnoreCase(("MALE"))) {
				malePersons.add(person);
			}
		}

		return malePersons;
	}
}