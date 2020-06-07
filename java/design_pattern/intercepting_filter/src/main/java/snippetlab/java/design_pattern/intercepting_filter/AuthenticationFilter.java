package snippetlab.java.design_pattern.intercepting_filter;

public final class AuthenticationFilter implements IFilter
{
	public void execute(String request) {
		System.out.println("Authenticating request: " + request);
	}
}