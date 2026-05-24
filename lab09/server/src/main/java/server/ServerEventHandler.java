package server;

import common.message.Message;
import common.message.MessageSerializer;
import common.message.MessageType;
import common.refresh.Updatable;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ServerEventHandler extends SimpleChannelInboundHandler<String> {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private final Updatable updatable;

    public ServerEventHandler(Updatable updatable) {
        this.updatable = updatable;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // send a greeting and register the channel to the global channel list
        // so the channel received the message from others
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(future -> channels.add(ctx.channel()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws IOException {
        Message message = MessageSerializer.decode(msg);
        String time = message.timestamp().substring(11, 16);

        if (MessageType.TEXT.equals(message.messageType())) {
            updatable.update("[" + time + "]" + message.sender() + ": " + message.content());
        } else if (MessageType.FILE.equals(message.messageType())) {
            byte[] downloadedBytes = Base64.getDecoder().decode(message.content());

            File outputFile = new File("downloads", message.timestamp() + "_" + message.fileName());
            Files.write(outputFile.toPath(), downloadedBytes);

            updatable.update("[" + time + "]" + message.sender() + " przesłał(a) załącznik: " + message.fileName());
        }

        // sending received message to all channels but the current one
        for (Channel c : channels) {
            if (c != ctx.channel()) {
                c.writeAndFlush(msg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
