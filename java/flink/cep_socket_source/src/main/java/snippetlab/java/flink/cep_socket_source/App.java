package snippetlab.java.flink.cep_socket_source;

import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.flink.cep.CEP;
import org.apache.flink.cep.pattern.Pattern;
import org.apache.flink.cep.pattern.conditions.SimpleCondition;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class App
{
	public static void main(String[] args) throws Exception
	{
		StreamExecutionEnvironment env =
			StreamExecutionEnvironment.getExecutionEnvironment();

		// It can changes the number of threads applying for sink function
		// env.setParallelism(2);
		/**
		 * if it's 1:
		 * 1 thread for BobSinker
		 * 1 thread for AliceSinker
		 *
		 * if is's 4:
		 * 4 thread for BobSinker
		 * 4 thread for AliceSinker
		 * These 8 threads are totally different threads
		 */

		DataStreamSource<String> socketStreamSource =
			env.socketTextStream("localhost", 9527);

		definePattern1(socketStreamSource);
		definePattern2(socketStreamSource);
		definePattern3(socketStreamSource);

		socketStreamSource.print("[" + LocalDateTime.now() + "] Get data: ");

		env.executeAsync();

		System.out.println("Start Program");
	}

	private static void
		definePattern1(DataStreamSource<String> socketStreamSource)
	{
		Pattern<String, ?> pattern1 =
			Pattern.<String>begin("start")
				.where(
					new SimpleCondition<String>()
					{
						@Override
						public boolean filter(String s)
						{
							return s.startsWith("x");
						}
					});

		CEP.pattern(socketStreamSource, pattern1)
			.select((event -> event.get("start").get(0)))
			.addSink(new SinkFunction<String>()
			{
				@Override
				public void invoke(String str)
				{
					System.out.println("Pattern matched[^x]: " + str);
				}
			});
	}

	private static void
		definePattern2(DataStreamSource<String> socketStreamSource)
	{
		Pattern<String, ?> pattern2 =
			Pattern.<String>begin("start")
				.where(
					new SimpleCondition<String>()
					{
						@Override
						public boolean filter(String s)
						{
							return s.startsWith("y1");
						}
					})
				.next("next")
				.where(new SimpleCondition<String>()
				{
					@Override
					public boolean filter(String s)
					{
						return s.startsWith("y2");
					}
				});


		CEP.pattern(socketStreamSource, pattern2)
			.select(
				(event -> event.get("start").get(0) + event.get("next").get(0)))
			.addSink(new SinkFunction<String>()
			{
				@Override
				public void invoke(String str)
				{
					System.out.println("Pattern matched[^y1 + ^y2]: " + str);
				}
			});
	}

	private static void
		definePattern3(DataStreamSource<String> socketStreamSource) throws Exception
	{
		Pattern<String, ?> patternBob =
			Pattern.<String>begin("start")
				.where(
					new SimpleCondition<String>()
					{
						@Override
						public boolean filter(String s)
						{
							return s.startsWith("bob");
						}
					});

		Pattern<String, ?> patternAlice =
			Pattern.<String>begin("start")
				.where(
					new SimpleCondition<String>()
					{
						@Override
						public boolean filter(String s)
						{
							return s.startsWith("alice");
						}
					});

		Map<String, Pattern<String, ?>> patternByName = new HashMap<String, Pattern<String, ?>>();
		patternByName.put("bob", patternBob);
		patternByName.put("alice", patternAlice);

		/** load class */
		// REF: https://stackoverflow.com/questions/11759414/java-how-to-load-different-versions-of-the-same-class
		URL[] classLoaderUrls =
			new URL[]
			{
				new URL("file:target/cep_socket_source-1.0-SNAPSHOT.jar")
			};

		Map<String, String> sinkerByName = new HashMap<String, String>();
		sinkerByName.put("bob", "snippetlab.java.flink.cep_socket_source.sinker.BobSinker");
		sinkerByName.put("alice", "snippetlab.java.flink.cep_socket_source.sinker.AliceSinker");

		try(URLClassLoader urlClassLoader =
			new URLClassLoader(	classLoaderUrls,
								Thread.currentThread().getContextClassLoader()))
		{
			for(Map.Entry<String, String> entry : sinkerByName.entrySet())
			{
				Class<?> cls = urlClassLoader.loadClass(entry.getValue());
				SinkFunction<String> obj =
					(SinkFunction<String>)cls.newInstance();

				CEP.pattern(socketStreamSource,
							patternByName.get(entry.getKey()))
					.select((event -> event.get("start").get(0)))
					.addSink(obj);
			}
		}
	}
}
