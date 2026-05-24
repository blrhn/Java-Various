package client;

/*import common.message.Message;
import common.message.MessageSerializer;
import common.message.MessageType;
import common.refresh.Updatable;*/
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/*import java.io.File;
import java.nio.file.Files;
import java.util.Base64;*/

public class ClientEventHandler extends SimpleChannelInboundHandler<String> {
    /*private final Updatable updatable;

    public ClientEventHandler(Updatable updatable) {
        this.updatable = updatable;
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        /*Message message = MessageSerializer.decode(msg);
        String time = message.timestamp().substring(11, 16);

        if (MessageType.TEXT.equals(message.messageType())) {
            updatable.update("[" + time + "]" + message.sender() + ": " + message.content());
        } else if (MessageType.FILE.equals(message.messageType())) {
            byte[] downloadedBytes = Base64.getDecoder().decode(message.content());

            File outputFile = new File("downloads", message.timestamp() + "_" + message.fileName());
            Files.write(outputFile.toPath(), downloadedBytes);

            updatable.update("[" + time + "]" + message.sender() + " przesłał(a) załącznik: " + message.fileName());
        }*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
