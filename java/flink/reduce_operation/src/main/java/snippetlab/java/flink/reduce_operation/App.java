package snippetlab.java.flink.reduce_operation;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class App
{
	// This process generates the result [month, meterType, dataType, avg]
	public static void
		main( String[] args ) throws Exception
	{
		// set up the stream execution environment
		final StreamExecutionEnvironment env =
			StreamExecutionEnvironment.getExecutionEnvironment();

		// read meterData file, and each row is a data stream element.
		DataStream<String> meterDataStream = env.readTextFile("meterData");

		// Tuple4<datetime, meterType, dataType, value>
		DataStream<Tuple5<String, String, String, Integer, Integer>> map1 =
			meterDataStream.map(new RowToTuple5());

		// keyBy datetime, meterType, dataType
		KeyedStream<Tuple5<String, String, String, Integer, Integer>, Tuple> keyed = map1.keyBy(0,1,2);

		DataStream<Tuple5<String, String, String, Integer, Integer>> reduce =
			keyed.reduce(new Reduce());

		DataStream<Tuple4<String, String, String, Double>> result =
			reduce.map(new RowToTuple4());

		result.print();

		System.out.println("process start");
		env.execute();
	}

	@SuppressWarnings("unchecked")
	public static final class RowToTuple4 implements MapFunction<Tuple5<String, String, String, Integer, Integer>, Tuple4<String, String, String, Double>>
	{
		public Tuple4<String, String, String, Double>
			map(Tuple5<String, String, String, Integer, Integer> tuple)
		{
			return new Tuple4(	tuple.f0,
								tuple.f1,
								tuple.f2,
								(tuple.f3 * 1.0)/tuple.f4);
		}
	}

	@SuppressWarnings("unchecked")
	public static final class RowToTuple5 implements MapFunction<String, Tuple5<String, String, String, Integer, Integer>>
	{
		public Tuple5<String, String, String, Integer, Integer>
			map(String row)
		{
			String[] fields = row.split(",",
										-1);

			return new Tuple5(	fields[0].substring(0,
													6),
								fields[1],
								fields[2],
								Integer.parseInt(fields[3]),
								1);
		}
	}

	public static class Reduce implements ReduceFunction<Tuple5<String, String, String, Integer, Integer>>
	{
		public Tuple5<String, String, String, Integer, Integer> reduce(Tuple5<String, String, String, Integer, Integer> currentResult,
																	   Tuple5<String, String, String, Integer, Integer> previousResult)
		{
			return new Tuple5<String, String, String, Integer, Integer>(currentResult.f0,
																		currentResult.f1,
																		currentResult.f2,
																		currentResult.f3 + previousResult.f3,
																		currentResult.f4 + previousResult.f4);
		}
	}
}
