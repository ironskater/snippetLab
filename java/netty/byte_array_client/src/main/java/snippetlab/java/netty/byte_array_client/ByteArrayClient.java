package snippetlab.java.netty.byte_array_client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.DatatypeConverter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
/**
 * use nc -kl 9527 as server
 */
public class ByteArrayClient
{
	public static DateTimeFormatter formatter =
		DateTimeFormatter.ofPattern("hh:mm:ss.SSS");

	private static boolean isRunning = true;

	public ChannelFuture
		start(String host, int port) throws InterruptedException
	{
		Bootstrap boostrap = new Bootstrap();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		boostrap.group(workerGroup)
				.channel(NioSocketChannel.class)
				.handler(
					new ChannelInitializer<SocketChannel>()
					{
						@Override
						protected void initChannel(SocketChannel ch)
						{
							ch.pipeline().addLast(new ByteArrayClientHandler());
						}
					});

		boostrap.remoteAddress(host, port);

		return boostrap.connect().sync();
	}

	public static void
		main(String[] args) throws Exception
	{
		int port = 9527;
		String remoteHost = "localhost";

		ByteArrayClient client = new ByteArrayClient();

		ChannelFuture future = client.start(remoteHost, port);

		byte[] output = readByteArray("src/resources/bytes");

		System.out.println("prepare to send:");

		for(int ix = 0; ix < output.length; ix++)
		{
			System.out.printf("0x%02X ", output[ix]);

			if(((ix + 1)%8 == 0))
			{
				System.out.println();
			}
		}

		System.out.println("start send...");

		while(isRunning)
		{
			future.channel().writeAndFlush(Unpooled.copiedBuffer(output));
			future.channel().writeAndFlush(Unpooled.copiedBuffer(new byte[]{(byte)'\n'}));

			Thread.sleep(10L);
		}

		future.channel().closeFuture().sync();
	}

	private static byte[]
		readByteArray(String filePath)
	{
		try
		{
			StringBuilder sb = new StringBuilder();
			int iy = 0;
			byte[] rawDataArray = Files.readAllBytes(Paths.get(filePath));
			byte[] tmp = new byte[rawDataArray.length];

			for(int ix = 0; ix < rawDataArray.length; ix++)
			{
				if ((rawDataArray[ix] == ' ') || (rawDataArray[ix] == '\n') || (rawDataArray[ix] == '\r'))
				{
					continue;
				}

				tmp[iy] = rawDataArray[ix];
				iy++;
			}

			for(byte b : tmp)
			{
				if(b == 0)
				{
					break;
				}

				sb.append((char)b);
			}

			return DatatypeConverter.parseHexBinary(sb.toString());
		}
		catch(IOException ex)
		{
			return new byte[0];
		}
	}
}
