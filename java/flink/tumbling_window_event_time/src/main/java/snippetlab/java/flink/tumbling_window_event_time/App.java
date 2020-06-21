package snippetlab.java.flink.tumbling_window_event_time;

import java.sql.Timestamp;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class App
{
	public static void
		main( String[] args ) throws Exception
	{
		// set up the streaming execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
			env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

		DataStream<String> data = env.socketTextStream("localhost", 9527);

		DataStream<Tuple2<Long, String>> sum =
			data
				.map(
					new MapFunction<String, Tuple2<Long, String>>() // String is "timestamp,data"
					{
						public Tuple2<Long, String> map(String s)
						{
							String[] fields = s.split(",", -1);
							return new Tuple2<Long, String>(Long.parseLong(fields[0]), fields[1]);
						}
					})

				.assignTimestampsAndWatermarks(
					new AscendingTimestampExtractor<Tuple2<Long, String>>()
					{
						public long extractAscendingTimestamp(Tuple2<Long, String> t)
						{
							return t.f0;
						}
					})
				.windowAll(TumblingEventTimeWindows.of(Time.seconds(5)))
				.reduce(
					new ReduceFunction<Tuple2<Long, String>>()
					{
						public Tuple2<Long, String> reduce(Tuple2<Long, String> t1, Tuple2<Long, String> t2)
						{
							int num1 = Integer.parseInt(t1.f1);
							int num2 = Integer.parseInt(t2.f1);
							int sum = num1 + num2;

							Timestamp t = new Timestamp(System.currentTimeMillis());
							return new Tuple2<Long, String>(t.getTime(), "" + sum);
						}
					});

		sum.print();

		// execute program
		env.execute("Window");
	}
}
