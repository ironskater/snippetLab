package snippetlab.java.design_pattern.intercepting_filter;

public final class FilterManager
{
	private FilterChain filterChain;

	public FilterManager(Target target) {
		this.filterChain = new FilterChain();
		this.filterChain.setTarget(target);
	}

	public void setFilter(IFilter filter) {
		this.filterChain.addFilter(filter);
	}

	public void filterRequest(String request) {
		filterChain.execute(request);
	}
}