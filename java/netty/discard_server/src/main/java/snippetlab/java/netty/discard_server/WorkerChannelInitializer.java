package snippetlab.java.netty.discard_server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

// ChannelInitializer is purposed to help a user configure a new Channel
public class WorkerChannelInitializer extends ChannelInitializer<SocketChannel>
{
	public WorkerChannelInitializer() {}

	@Override
	public void
		initChannel(SocketChannel ch)
	{
		System.out.println(
			String.format(	"3)[%d] Creating a channel",
							Thread.currentThread().getId()));

		ch.pipeline().addLast(new DiscardServerHandler());
	}
}