package snippetlab.java.design_pattern.intercepting_filter;

import java.util.ArrayList;
import java.util.List;

public final class FilterChain
{
	private List<IFilter> filterList = new ArrayList<>();
	private Target target;

	public void addFilter(IFilter filter) {
		this.filterList.add(filter);
	}

	public void execute(String request) {
		for(IFilter filter : this.filterList) {
			filter.execute(request);
		}

		this.target.execute(request);
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}