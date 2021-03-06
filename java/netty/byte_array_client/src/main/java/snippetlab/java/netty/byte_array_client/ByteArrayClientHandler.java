package snippetlab.java.netty.byte_array_client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ByteArrayClientHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void
		channelRead(ChannelHandlerContext ctx, Object msg)
	{
		throw new UnsupportedOperationException();
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