package snippetlab.java.netty.time_server;

import java.time.LocalDateTime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter
{
	/**
	 * The channelActive() method will be invoked
	 * when a connection is established and ready to generate traffic.
	 */
	@Override
	public void
		channelActive(final ChannelHandlerContext ctx) throws Exception
	{
		// Get the current ByteBufAllocator via ChannelHandlerContext.alloc() and allocate a new buffer.
		final ByteBuf timeByteBuf = ctx.alloc().buffer(4);

		timeByteBuf.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));
		timeByteBuf.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));
		timeByteBuf.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));
		timeByteBuf.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));

		System.out.println(
			String.format(	"5)[%d][%s] before writeAndFlush",
							Thread.currentThread().getId(),
							LocalDateTime.now().format(TimeServer.formatter)));
		// A ChannelFuture represents an I/O operation which has not yet occurred.
		// It means, any requested operation might not have been performed yet because all operations are asynchronous in Netty.
		final ChannelFuture writeAndFlushFuture = ctx.writeAndFlush(timeByteBuf.readBytes(4));
		Thread.sleep(1000);
		 ctx.writeAndFlush(timeByteBuf.readBytes(4));
		Thread.sleep(1000);
		 ctx.writeAndFlush(timeByteBuf.readBytes(4));
		Thread.sleep(1000);
		 ctx.writeAndFlush(timeByteBuf.readBytes(4));
		Thread.sleep(1000);

		// you need to call the close() method after the ChannelFuture is complete,
		// which was returned by the write() method, and it notifies its listeners when the write operation has been done.
		writeAndFlushFuture.addListener(
			new ChannelFutureListener()
			{
				@Override
				public void operationComplete(ChannelFuture future) throws Exception
				{
					System.out.println(
						String.format(	"6)[%d][%s] writeAndFlush operationComplete",
										Thread.currentThread().getId(),
										LocalDateTime.now().format(TimeServer.formatter)));

					if(writeAndFlushFuture != future)
					{
						throw new Exception("Different entities");
					}

					// Please note that, close() also might not close the connection immediately, and it returns a ChannelFuture.
					System.out.println(
						String.format(	"7)[%d][%s] before ctx.close()",
										Thread.currentThread().getId(),
										LocalDateTime.now().format(TimeServer.formatter)));
					ctx.close();
				}
			});
		System.out.println(
			String.format(	"8)[%d][%s] after addListener",
							Thread.currentThread().getId(),
							LocalDateTime.now().format(TimeServer.formatter)));
		// Alternatively, you could simplify the code using a pre-defined listener:
		// channelFuture.addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * Because we are going to ignore any received data but to send a message as soon as a connection is established,
	 * we cannot use the channelRead() method this time.
	 * Instead, we should override the channelActive() method.
	 */
	@Override
	public void	channelRead(ChannelHandlerContext ctx, Object msg) {}

	@Override
	public void
		exceptionCaught(ChannelHandlerContext ctx,
						Throwable cause)
	{
		cause.printStackTrace();

		ctx.close();
	}
}