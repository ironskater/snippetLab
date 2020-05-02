package snippetlab.java.flink.custom_source;

import java.util.Arrays;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.IterativeCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class App
{
	private static String[] strArray =
		new String[]
		{
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"start",
			"xtart",
			"xtart",
			"xtart",
			"xtart",
			"xtart",
			"ytart",
		};

	public static void
		main(String[] args) throws Exception
	{
		final StreamExecutionEnvironment environment =
			StreamExecutionEnvironment.getExecutionEnvironment();

		DataStreamSource<String> stream =
			environment.setParallelism(1).addSource(new CustomSingleSource());

		Pattern<String, String> pattern =
			Pattern.<String>begin("start")
				.where(
					new IterativeCondition<String>()
					{
						@Override
						public boolean filter(String s, Context<String> context)
						{
							return s.startsWith("x");
						}
					})
				.or(
					new IterativeCondition<String>()
					{
						@Override
						public boolean filter(String s, Context<String> context) throws Exception
						{
							return s.startsWith("y");
						}
					});

		CEP.pattern(stream, pattern)
			.select((map -> Arrays.toString(map.get("start").toArray())))
			.addSink(new SinkFunction<String>()
			{
				@Override
				// public void invoke(String value, Context ontext)
				public void invoke(String value)
				{
					System.out.println("Output string is: " + value);
				}
			});

		stream.print("Input string is: ");

		environment.executeAsync();

		Thread.sleep(1000L);

		System.out.println("[MAIN] ThreadId: " + Thread.currentThread().getId());
		for(String str : strArray)
		{
			CustomSingleSource.sendMsg(str);
		}
	}
}