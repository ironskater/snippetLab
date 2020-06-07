package snippetlab.java.design_pattern.intercepting_filter;

public final class DebugFilter implements IFilter
{
	public void execute(String request) {
		System.out.println("request log: " + request);
	}
}