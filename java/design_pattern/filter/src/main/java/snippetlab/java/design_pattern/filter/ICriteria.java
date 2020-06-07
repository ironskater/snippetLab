package snippetlab.java.design_pattern.filter;

import java.util.List;

public interface ICriteria
{
	public List<Person> meetCriteria(List<Person> persons);
}