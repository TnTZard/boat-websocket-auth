package xx.mrxie.websocket.auth.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author: anzhi
 * @Date: 2021/3/11 11:23
 */
@Slf4j
public class MyNettyClient {

    private int port;
    private String host;
    private Channel socketChannel;
    private Thread thread;
    private boolean isConnect = true;

    public MyNettyClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    private boolean isConnected() {
        return isConnect;
    }

    public void stop() {
        try {
            if (thread.isAlive()) {
                thread.destroy();
            }
        } catch (Exception e) {
            log.info("线程异常关闭" + e.toString());
        }
    }


    public void start() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.channel(NioSocketChannel.class)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .group(eventLoopGroup).remoteAddress(host, port)
                        .handler(new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel channel) throws Exception {
                                channel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8))
                                        .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                        .addLast(new MyNettyClientHandler());
                            }
                        });
                ChannelFuture channelFuture;
                try {
                    channelFuture = bootstrap.connect(host, port).sync();
                    if (channelFuture.isSuccess()) {
                        // 得到管道  通信
                        socketChannel = channelFuture.channel();
                        isConnect = true;
                        log.info("客户端启动成功---- ");
                    } else {
                        log.error("客户端开启失败----");
                    }
                    channelFuture.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    log.info("连接断开，退出！");
                    isConnect = false;
                    eventLoopGroup.shutdownGracefully();
                }
            }
        });
        thread.start();
    }


    public void sendMessage(Object msg) {
        if (socketChannel != null) {
            if (msg instanceof String) {
                socketChannel.writeAndFlush(msg);
            }
        }
    }




}
