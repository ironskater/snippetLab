package snippetlab.java.netty.echo_server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void
	/**
	 * Please note that we did not release the received message unlike we did in the DISCARD example.
	 * It is because Netty releases it for you when it is written out to the wire.
	 */
		channelRead(ChannelHandlerContext ctx,
					Object msg)
	{
		// A ChannelHandlerContext object ctx provides various operations that enable you to trigger various I/O events and operations.
		ctx.write(msg);

		System.out.println(
			String.format(	"4)[%d] Echo msg has been written into buffer",
							Thread.currentThread().getId()));

		ctx.flush();

		System.out.println(
			String.format(	"5)[%d] Echo msg has been flushed out to the wire",
							Thread.currentThread().getId()));

		// or
		// Alternatively, you could call ctx.writeAndFlush(msg) for brevity.
	}

	@Override
	public void
		exceptionCaught(ChannelHandlerContext ctx,
						Throwable cause)
	{
		cause.printStackTrace();

		ctx.close();
	}
}