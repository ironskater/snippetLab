package snippetlab.java.netty.time_server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Send time data to client and close the connection on completion
 *
 * REF: https://netty.io/wiki/user-guide-for-4.x.html#writing-a-time-server
 *
 * Use this command to receive time from this server example
 * $ rdate -o <port> -p <host>
 * where <port> is the port number you specified in the main() method and <host> is usually localhost.
 */
public class TimeServer
{
	public static DateTimeFormatter formatter =
		DateTimeFormatter.ofPattern("hh:mm:ss.SSS");

	private int port;

	private TimeServer(int port)
	{
		this.port = port;
	}

	public void
		run() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try
		{
			ServerBootstrap serverBootStrap = new ServerBootstrap();

			serverBootStrap.group(	bossGroup,
									workerGroup)
							.channel(NioServerSocketChannel.class)
							.childHandler(
								new ChannelInitializer<SocketChannel>()
								{
									@Override
									public void initChannel(SocketChannel ch)
									{
										// When incoming connection is accepted, the program will start execute here
										System.out.println(
											String.format(	"4)[%d][%s] Creating a channel",
															Thread.currentThread().getId(),
															LocalDateTime.now().format(TimeServer.formatter)));

										ch.pipeline().addLast(new TimeServerHandler());
									}
								})
							.option(ChannelOption.SO_BACKLOG,
									128)
							.childOption(	ChannelOption.SO_KEEPALIVE,
											true);

			System.out.println(
				String.format(	"1)[%d][%s] before port binding",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeServer.formatter)));

			ChannelFuture channelFuture = serverBootStrap.bind(this.port).sync();

			System.out.println(
				String.format(	"2)[%d][%s] after port binding",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeServer.formatter)));

			System.out.println(
				String.format(	"3)[%d][%s] Server running, waiting for incoming connection",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeServer.formatter)));
			Thread.sleep(5000);
			channelFuture.channel().closeFuture().sync();

			System.out.println(
				String.format(	"8)[%d][%s] after channelFuture.channel().closeFuture()",
								Thread.currentThread().getId(),
								LocalDateTime.now().format(TimeServer.formatter)));
		}
		finally
		{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void
		main(String[] args) throws Exception
	{
		int port = (args.length > 0) ?
						Integer.parseInt(args[0]) :
						9527;

		new TimeServer(port).run();
	}
}
