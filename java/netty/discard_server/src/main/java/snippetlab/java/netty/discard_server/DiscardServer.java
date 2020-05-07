package snippetlab.java.netty.discard_server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discard any incoming data
 *
 * REF: https://netty.io/wiki/user-guide-for-4.x.html#getting-started
 */
public class DiscardServer
{
	private int port;

	private DiscardServer(int port)
	{
		this.port = port;
	}

	public void
		run() throws Exception
	{
		/**
		 * NioEventLoopGroup is a multithreaded event loop that handles I/O operation.
		 *
		 * [boss]
		 * "boss" handles incoming connection
		 * once the boss accepts the connection, it registers the accepted connection to the worker.
		 *
		 * [worker]
		 * "worker" handles the traffic of the accepted connection
		 *
		 * How many Threads are used and
		 * how they are mapped to the created Channels
		 * depends on the EventLoopGroup implementation and may be even configurable via a constructor.
		 */
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // if no parameter, default value is 2*CPU cores
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ChannelInitializer<SocketChannel> channelHandler =
			new WorkerChannelInitializer();

		try
		{
			/**
			 * ServerBootstrap is a helper class that sets up a server.
			 */
			ServerBootstrap serverBootStrap = new ServerBootstrap();

			serverBootStrap.group(	bossGroup,
									workerGroup)
							// we specify to use the NioServerSocketChannel class which is used to instantiate a new Channel to accept incoming connections.
							.channel(NioServerSocketChannel.class)
							// The childHandler specified here will always be evaluated by a newly accepted Channel.
							.childHandler(channelHandler)
							// option() is for the NioServerSocketChannel that accepts incoming connections.
							.option(ChannelOption.SO_BACKLOG,
									128)
							// childOption() is for the Channels accepted by the parent ServerChannel, which is NioServerSocketChannel in this case.
							.childOption(	ChannelOption.SO_KEEPALIVE,
											true);

			// Bind and start to accept incoming connections
			ChannelFuture channelFuture = serverBootStrap.bind(this.port).sync();
			System.out.println(
				String.format(	"1)[%d] finish port binding",
								Thread.currentThread().getId()));

			/**
			 * Wait until the server socket is closed.
			 * In this example, this does not happen, but you can do that to gracefully shutdown server
			 */
			System.out.println(
				String.format(	"2)[%d] Server running",
								Thread.currentThread().getId()));
			channelFuture.channel().closeFuture().sync();// DefaultChannelPromise.sync()
			System.out.println(
				String.format(	"7)[%d] Server running",
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

		new DiscardServer(port).run();
	}
}
