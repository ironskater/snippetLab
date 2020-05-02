package snippetlab.java.flink.basic_dataset_api;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem.WriteMode;

public class App
{
	/**
	 * 1. maven clean install to create jar file
	 * 2. launch flink cluster
	 * ./deps/bin/start-cluster.sh
	 * 3. execute the following command
	 * ./deps/bin/flink run -c snippetlab.java.flink.basic_dataset_api.App /home/hyde/randomTest/basic_dataset_api-1.0-SNAPSHOT.jar --eventList file:///home/hyde/randomTest/eventList --output file:///home/hyde/randomTest/output
	 * 4. check job status on dashboard: localhost:8081
	 *
	 * Except for using command line,
	 * we also can submit job by using dashboard -> Submit New Job -> Add New
	 * Entry Class: snippetlab.java.flink.basic_dataset_api.App
	 * Program Argument: --eventList file:///home/hyde/randomTest/eventList --output file:///home/hyde/randomTest/output
	 */
	public static void main( String[] args ) throws Exception
	{
		// ExecutionEnvironment is a context in which a program is executed
		// It controls job execution
		ExecutionEnvironment env =
			ExecutionEnvironment.getExecutionEnvironment();

		ParameterTool params = ParameterTool.fromArgs(args);
		env.getConfig().setGlobalJobParameters(params);

		DataSet<String> eventSet = env.readTextFile(params.get("eventList"));

		DataSet<String> filteredEventSet =
			eventSet.filter(
				new FilterFunction<String>()
				{
					public boolean filter(String event)
					{
						return event.startsWith("power");
					}
				});

		DataSet<Tuple2<String, Integer>> numByEvent =
			filteredEventSet.map(new NumByEventMapper());

		DataSet<Tuple2<String, Integer>> counts =
			numByEvent.groupBy(0).sum(1);

		if(params.has("output"))
		{
			counts.writeAsCsv(	params.get("output"),
								"\n",
								",",
								WriteMode.OVERWRITE);

			// "Event counts" is a job name
			env.execute("Event counts");
		}
	}

	private static class NumByEventMapper implements MapFunction<String, Tuple2<String, Integer>>
	{
		@Override
		public Tuple2<String, Integer> map(String event)
		{
			return new Tuple2(event, new Integer(1));
		}
	}
}
