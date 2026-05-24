module server {
    exports server;
    requires io.netty.transport;
    requires io.netty.codec;
    requires io.netty.handler;
    requires io.netty.common;
    requires common;
}