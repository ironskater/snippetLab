package snippetlab.java.flink.tumbling_window_processing_time;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class App
{
	public static void
		main( String[] args ) throws Exception
	{
		// set up the stream execution environment
		final StreamExecutionEnvironment env =
			StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);

		// read data from data_server
		DataStream<String> data = env.socketTextStream("localhost", 9527);

		// Tuple5<month, product, category, profit, count>
		DataStream<Tuple5<String, String, String, Integer, Integer>> mapped =
			data.map(new Splitter()); 	// to create
										// tuple [June, Category5, Bat, 	12, 1]
										// tuple [June, Category4, Perfume, 10, 1]

		// mapped.print();

		// groupBy month
		DataStream<Tuple5<String, String, String, Integer, Integer>> reduced =
			mapped
			.keyBy(0)
			.window(TumblingProcessingTimeWindows.of(Time.seconds(2)))
			.reduce(new Reduce1());

		reduced.print();

		System.out.println("process start");
		env.execute();
	}

	public static final class Splitter implements MapFunction<String, Tuple5<String, String, String, Integer, Integer>>
	{
		public Tuple5<String, String, String, Integer, Integer>
			map(String row) // 01-06-2018,June,Category5,Bat,12
		{
			String[] fields = row.split(",",
										-1); // fields = [{01-06-2018},{June},{Category5},{Bat},{12}]

			// Ignore time stamp, we don't need it for any calculations
			return new Tuple5<>(fields[1], // June
								fields[2], // Category5
								fields[3], // Bat
								Integer.parseInt(fields[4]), // 12
								1);
		}
	}

	// Reduce operator is doing SUM of all profits & counts by month within each window
	public static class Reduce1 implements ReduceFunction<Tuple5<String, String, String, Integer, Integer>>
	{
		public Tuple5<String, String, String, Integer, Integer> reduce(Tuple5<String, String, String, Integer, Integer> currentResult,
																	   Tuple5<String, String, String, Integer, Integer> previousResult)
		{
			System.out.println("=========================== currentResult: " + currentResult.toString());
			System.out.println("=========================== previousResult: " + previousResult.toString());
			System.out.println();
			return new Tuple5<String, String, String, Integer, Integer>(currentResult.f0,
																		"",
																		"",
																		currentResult.f3 + previousResult.f3,
																		currentResult.f4 + previousResult.f4);
		}
	}
}
