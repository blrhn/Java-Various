package server;

import common.refresh.Updatable;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
// import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class ServerInitializer extends ChannelInitializer<Channel> {
    private final SslContext sslCtx;
    private final Updatable updatable;

    public ServerInitializer(SslContext sslCtx, Updatable updatable) {
        this.sslCtx = sslCtx;
        this.updatable = updatable;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // ssl handler first to encrypt and decrypt everything
        pipeline.addLast(sslCtx.newHandler(channel.alloc()));
        // LineBasedFrameDecoder - splits the received ByteBuf on line endings ("\n i \r\n)
        // ~5mb dla plikow
        //pipeline.addLast(new LineBasedFrameDecoder(5242880));
        pipeline.addLast(new LengthFieldBasedFrameDecoder(5242880, 0, 4, 0, 4));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast(new StringEncoder());

        pipeline.addLast(new ServerEventHandler(updatable)); // business logic
    }
}
