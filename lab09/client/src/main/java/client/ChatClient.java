package client;

// import common.refresh.Updatable;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;

public class ChatClient {
    private final int port;
    private final SslContext sslCtx;
    //private final Updatable updatable;

    private Channel channel;
    private EventLoopGroup workerGroup;

    public ChatClient(int port, SslContext sslCtx/*, Updatable updatable*/) {
        this.port = port;
        this.sslCtx = sslCtx;
        //this.updatable = updatable;
    }

    public void connect() throws Exception {
        workerGroup = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(port, "localhost", sslCtx/*, updatable*/));

        // start the connection attempt
        channel = bootstrap.connect("localhost", port).sync().channel();
    }

    public void send(String msg) {
        channel.writeAndFlush(msg + "\n");
    }

    public void disconnect() {
        channel.close();
        workerGroup.shutdownGracefully();
    }
}
