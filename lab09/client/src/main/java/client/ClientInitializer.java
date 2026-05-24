package client;

import common.refresh.Updatable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
// import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;
    private final int port;
    private final String host;
    //private final Updatable updatable;

    public ClientInitializer(int port, String host, SslContext sslCtx/*, Updatable updatable*/) {
        this.port = port;
        this.host = host;
        this.sslCtx = sslCtx;
        //this.updatable = updatable;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslCtx.newHandler(ch.alloc(), host, port));
        //pipeline.addLast(new LineBasedFrameDecoder(5242880));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(5242880, 0, 4, 0, 4));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new ClientEventHandler(/*updatable*/));
    }
}
