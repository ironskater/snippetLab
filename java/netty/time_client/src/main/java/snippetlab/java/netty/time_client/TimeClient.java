package snippetlab.java.netty.time_client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * To get time from TimeServer
 *
 * REF: https://netty.io/wiki/user-guide-for-4.x.html#writing-a-time-client
 *
 * The biggest and only difference between a server and a client in Netty is that
 * "different Bootstrap" and "Channel implementations" are used. Please take a look at the following code:
 */
public class TimeClient
{
	public static DateTimeFormatter formatter =
		DateTimeFormatter.ofPattern("hh:mm:ss.SSS");

	public static void
		main(String[] args) throws Exception
	{
		String host = (args.length == 2) ? args[0] : "localhost";
		int port = (args.length == 2) ? Integer.parseInt(args[1]) : 9527;
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try
		{
			// Bootstrap is similar to ServerBootstrap
			// except that it's for non-server channels such as a client-side or connectionless channel.
			Bootstrap bootstrap = new Bootstrap();

			// If you specify only one EventLoopGroup,
			// it will be used both as a boss group and as a worker group.
			// The boss is not used for the client side though.
			bootstrap.group(workerGroup)
						// Instead of NioServerSocketChannel,
						// NioSocketChannel is being used to create a client-side Channel.
						.channel(NioSocketChannel.class)
						// Note that we do not use childOption() here
						// unlike we did with ServerBootstrap because the client-side SocketChannel does not have a parent.
						.option(ChannelOption.SO_KEEPALIVE,
								true)
						.handler(
							new ChannelInitializer<SocketChannel>()
							{
								@Override
								public void initChannel(SocketChannel ch) throws Exception
								{
									// Choose a method as following:
									// (1) Using one handler
									// ch.pipeline().addLast(new TimeClientHandler());

									// (2) Split handler
									ch.pipeline().addLast(	new TimeDecoder(),
															new TimeClientHandler());

									System.out.println(
										String.format(	"2)[%d][%s] after init channel and create pipeline",
														Thread.currentThread().getId(),
														LocalDateTime.now().format(TimeClient.formatter)));
								}
							});

			// Start the client, call the connect() method instead of the bind() method
			System.out.println(
				String.format(	"1)[%d][%s] before connect",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeClient.formatter)));
			ChannelFuture channelFuture =
				bootstrap.connect(	host,
									port).sync();
			System.out.println(
				String.format(	"3)[%d][%s] after connect",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeClient.formatter)));

			// Wait until the connection is closed
			channelFuture.channel().closeFuture().sync();
			System.out.println(
				String.format(	"6)[%d][%s] after channelFuture.channel().closeFuture()",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeClient.formatter)));
		}
		finally
		{
			workerGroup.shutdownGracefully();
		}
	}
}
