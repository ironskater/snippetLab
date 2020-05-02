package snippetlab.java.flink.map_operation;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class App
{
	public static void
		main( String[] args ) throws Exception
	{
		// set up the stream execution environment
		final StreamExecutionEnvironment env =
			StreamExecutionEnvironment.getExecutionEnvironment();

		DataStream<String> text = env.socketTextStream(	"localhost",
														9527);

		DataStream<Tuple2<String, Integer>> counts =
			text.filter(
				new FilterFunction<String>()
				{
					@Override
					public boolean filter(String value)
					{
						return value.startsWith("h");
					}
				})
 			// to generate a map in which its entry is such like ["hyper", 1]
			.map(new CountByWordMapper())
			// group by the tuple field "0" and
			.keyBy(0)
			// sum up tuple field "1"
			.sum(1);

			counts.print();

		// execute program
		env.executeAsync();

		System.out.println("process start");
	}

	public static final class CountByWordMapper implements MapFunction<String, Tuple2<String, Integer>>
	{
		public Tuple2<String, Integer>
			map(String value)
		{
			return new Tuple2(value, 1);
		}
	}
}
