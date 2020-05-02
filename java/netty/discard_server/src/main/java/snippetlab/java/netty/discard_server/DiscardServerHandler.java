package snippetlab.java.netty.discard_server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void
		channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		System.out.println(
			String.format(	"6)[%d] channelReadComplete",
							Thread.currentThread().getId()));
	}

	@Override
	public void
	// This method is called with the received message, whenever new data is received from a client.
		channelRead(ChannelHandlerContext ctx,
					Object msg)
	{
		ByteBuf in = (ByteBuf) msg;

		try
		{
			while(in.isReadable())
			{
				System.out.print((char) in.readByte());
				System.out.flush();
			}

			System.out.println(
				String.format(	"4)[%d] channelRead ends",
								Thread.currentThread().getId()));
		}
		finally
		{
			// ByteBuf is a reference-counted object which has to be released explicitly via the release() method.
			// Please keep in mind that it is the handler's responsibility
			// to release any reference-counted object passed to the handler.
			ReferenceCountUtil.release(msg);
			System.out.println(
				String.format(	"5)[%d] ByteBuf release",
								Thread.currentThread().getId()));
			// or do
			// in.release(msg);
		}
	}

	/** General implementation of channelRead
	@Override
	public void
		channelRead(ChannelHandlerContext ctx,
					Object msg)
	{
		try
		{
			// Do something with msg
		}
		finally
		{
			ReferenceCountUtil.release(msg);
		}
	}
	*/

	/**
	 * General actions
	 * 1. log exception msg and close associated channel
	 * 2. send a response msg with error code before closing the connection
	 */
	@Override
	public void
		exceptionCaught(ChannelHandlerContext ctx,
						Throwable cause)
	{
		cause.printStackTrace();

		// close the connection when an exception is raised.
		ctx.close();
	}
}