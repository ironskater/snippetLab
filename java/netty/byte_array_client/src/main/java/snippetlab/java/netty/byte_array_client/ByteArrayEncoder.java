package snippetlab.java.netty.byte_array_client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ByteArrayEncoder extends MessageToByteEncoder<Object>
{
	@Override
	protected void
		encode(	ChannelHandlerContext ctx,
				Object msg,
				ByteBuf out)
	{
		throw new UnsupportedOperationException();
	}
}