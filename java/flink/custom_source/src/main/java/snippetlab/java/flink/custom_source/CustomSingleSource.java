package snippetlab.java.flink.custom_source;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

public class CustomSingleSource extends RichSourceFunction<String>
{
	private boolean isRunning = true;
	private static CustomSingleSource sourceObj;
	private SourceContext<String> ctx;

	public CustomSingleSource()
	{
	}

	@Override
	public void
		open(Configuration configuration)
	{
	}

	// Run: process an incoming message.
	// The structure of the code is generally an infinite loop waiting for messages and
	// adding them into the source context (that has been passed as a parameter).
	@Override
	public void
		run(SourceContext<String> ctx) throws InterruptedException
	{
		this.ctx = ctx;
		sourceObj = this;

		System.out.println("The sourceObj is: " + this);

		System.out.println("ThreadId: " + Thread.currentThread().getId());

		while(isRunning)
		{
			Thread.sleep(100L);
		}
	}

	// Cancel: because the run is an infinite loop, the cancel method defines a way to stop the loop.
	// We chose to use a (transient) atomic boolean set in open method to true,
	// read in open method as a condition for the infinite loop, and set to false in cancel method.
	@Override
	public void
		cancel()
	{
		this.isRunning = false;
		System.out.println("cancelling");
	}

	@Override
	public void
		close()
	{
		System.out.println("close");
	}

	public static void
		sendMsg(String msg)
	{
		System.out.println("ThreadId: " + Thread.currentThread().getId());
		System.out.println("================== The sourceObj is: " + CustomSingleSource.sourceObj);
		CustomSingleSource.sourceObj.ctx.collect(msg);
	}
}