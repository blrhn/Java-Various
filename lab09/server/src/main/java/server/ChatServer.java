package server;

import common.refresh.Updatable;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

public class ChatServer {
    private final int port;
    private final SslContext sslCtx;
    private final Updatable updatable;

    public ChatServer(int port, SslContext sslCtx, Updatable updatable) {
        this.port = port;
        this.sslCtx = sslCtx;
        this.updatable = updatable;
    }

    public void run() throws Exception {
        // boss accepts an incoming connection
        // worker handles the traffic of the accepted connection once
        // the boss accepts the connection and registers  the accepted connection
        // to the worker
        try (/*EventLoopGroup bossGroup = new MultiThreadIoEventLoopGroup(1, NioIoHandler.newFactory());*/
             // default: 2 * cpu cores
             EventLoopGroup workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory())) {

            // helper class that sets up a server
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(/*bossGroup,*/ workerGroup)
                    .channel(NioServerSocketChannel.class) //NioServerSocketChannel is being used to instantiate a new Channel to accept incoming connections
                    .childHandler(new ServerInitializer(sslCtx, updatable)) // handlers to help a user to configure a new Channel
                    .handler(new LoggingHandler(LogLevel.INFO));

            // binding to the port and starting the server
            ChannelFuture f = bootstrap.bind(port).sync();

            f.channel().closeFuture().sync();
        }
    }
}
