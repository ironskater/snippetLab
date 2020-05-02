package snippetlab.java.flink.custom_source;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

public class CustomParallelSource extends RichParallelSourceFunction<String> implements ICustomSource
{
	private transient AtomicBoolean isRunning;
	private transient ConcurrentLinkedQueue<String> queue;

	public CustomParallelSource()
	{
		this.isRunning = new AtomicBoolean(true);
		this.queue = new ConcurrentLinkedQueue<String>();
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
		run(SourceContext<String> ctx)
	{
		while(isRunning.get())
		{
			while(!queue.isEmpty())
			{
				ctx.collect(queue.poll());
			}
		}
	}

	// Cancel: because the run is an infinite loop, the cancel method defines a way to stop the loop.
	// We chose to use a (transient) atomic boolean set in open method to true,
	// read in open method as a condition for the infinite loop, and set to false in cancel method.
	@Override
	public void
		cancel()
	{
		isRunning.set(false);
		System.out.println("cancelling");
	}

	@Override
	public void
		close() throws Exception
	{
		System.out.println("closing");
		super.close();
	}

	@Override
	public void
		sendMsg(String msg)
	{
		this.queue.add(msg);
	}
}