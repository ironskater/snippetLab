package snippetlab.java.netty.time_client;

import java.time.LocalDateTime;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter
{
	// Solution1: internal buf(start)
	// private ByteBuf buf;

	// @Override
	// public void handlerAdded(ChannelHandlerContext ctx)
	// {
	// 	buf = ctx.alloc().buffer(4); // (1)
	// }

	// @Override
	// public void handlerRemoved(ChannelHandlerContext ctx)
	// {
	// 	buf.release(); // (1)
	// 	buf = null;
	// }

	// @Override
	// public void channelRead(ChannelHandlerContext ctx, Object msg)
	// {
	// 	ByteBuf m = (ByteBuf) msg;
	// 	buf.writeBytes(m); // (2)
	// 	m.release();

	// 	if (buf.readableBytes() >= 4)
	// 	{ // (3)
	// 		long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
	// 		System.out.println(new Date(currentTimeMillis));
	// 		ctx.close();
	// 	}
	// }
	// Solution1: internal buf(end)
	@Override
	public void
		channelActive(ChannelHandlerContext ctx) throws Exception
	{
		System.out.println(
			String.format(	"000000000000000)[%d][%s] start channelActive",
							Thread.currentThread().getId(),
							LocalDateTime.now().format(TimeClient.formatter)));
	}

	@Override
	public void
		channelRead(ChannelHandlerContext ctx, Object msg)
	{
		System.out.println(
			String.format(	"6)[%d][%s] start channelRead",
							Thread.currentThread().getId(),
							LocalDateTime.now().format(TimeClient.formatter)));

		// In TCP/IP, Netty reads the data sent from a peer into a ByteBuf.
		ByteBuf byteBuf = (ByteBuf)msg;

		try
		{
			long currentTimeMillis = (byteBuf.readUnsignedInt() - 2208988800L) * 1000L;

			System.out.println(new Date(currentTimeMillis));

			// System.out.println(
			// 	String.format(	"7)[%d][%s] before ctx.close",
			// 					Thread.currentThread().getId(),
			// 					LocalDateTime.now().format(TimeClient.formatter)));
			// ctx.close();
		}
		finally
		{
			byteBuf.release();
		}
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