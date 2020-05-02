package snippetlab.java.flink.cep_socket_source.sinker;

import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

public class AliceSinker extends RichSinkFunction<String>
{
	public void invoke(String msg, Context ctx)
	{
		System.out.println(
			String.format(	"[%s] I'am alice",
							Thread.currentThread().getId()));
	}
}