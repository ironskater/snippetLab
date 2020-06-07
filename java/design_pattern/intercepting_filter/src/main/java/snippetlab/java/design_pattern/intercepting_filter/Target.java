package snippetlab.java.design_pattern.intercepting_filter;

public final class Target
{
	public void execute(String request) {
		System.out.println("Executing request: " + request);
	}
}