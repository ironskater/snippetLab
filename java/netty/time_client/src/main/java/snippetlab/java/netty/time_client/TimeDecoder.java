package snippetlab.java.netty.time_client;

import java.time.LocalDateTime;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

// ByteToMessageDecoder is an implementation of ChannelInboundHandler which makes it easy to deal with the fragmentation issue.
public class TimeDecoder extends ByteToMessageDecoder
{
	@Override
	protected void
	// ByteToMessageDecoder calls the decode() method with an internally maintained cumulative buffer whenever new data is received.
		decode(	ChannelHandlerContext ctx,
				ByteBuf in,
				List<Object> out)
	{
		if (in.readableBytes() < 8)
		{
			// decode() can decide to add nothing to out where there is not enough data in the cumulative buffer.
			// ByteToMessageDecoder will call decode() again when there is more data received.
			System.out.println(
				String.format(	"4)[%d][%s] insuffient number of bytes",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeClient.formatter)));
			return;
		}

		// If decode() adds an object to out, it means the decoder decoded a message successfully.
		// ByteToMessageDecoder will discard the read part of the cumulative buffer.
		// Please remember that you don't need to decode multiple messages.
		// ByteToMessageDecoder will keep calling the decode() method until it adds nothing to out.
		out.add(in.readBytes(4));
		out.add(in.readBytes(4));

		System.out.println(
			String.format(	"5)[%d][%s] after add a msg",
							Thread.currentThread().getId(),
							LocalDateTime.now().format(TimeClient.formatter)));
	}
}