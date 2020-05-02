package snippetlab.java.netty.echo_server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Echo any incoming data
 *
 * REF: https://netty.io/wiki/user-guide-for-4.x.html#writing-an-echo-server
 *
 * The only difference with DiscardServer is the Handler Implementation
 */
public class EchoServer
{
	private int port;

	private EchoServer(int port)
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
										System.out.println(String.format(	"3)[%d] Creating a channel",
																			Thread.currentThread().getId()));
										ch.pipeline().addLast(new EchoServerHandler());
									}
								})
							.option(ChannelOption.SO_BACKLOG,
									128)
							.childOption(	ChannelOption.SO_KEEPALIVE,
											true);

			ChannelFuture channelFuture = serverBootStrap.bind(this.port).sync();
			System.out.println(String.format(	"1)[%d] finish port binding",
												Thread.currentThread().getId()));

			System.out.println(String.format(	"2)[%d] Server running",
												Thread.currentThread().getId()));
			channelFuture.channel().closeFuture().sync();
			System.out.println(String.format(	"6)[%d] DiscardServer ends",
												Thread.currentThread().getId()));
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

		new EchoServer(port).run();
	}
}
