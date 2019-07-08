package org.netty.core.protocol.lineBasedProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.LineEncoder;

import java.util.concurrent.TimeUnit;


/**
 * 需要注意的是，在client端，我们并没有使用LineEncoder进行编码，原因在于我们要模拟粘包、拆包。
 * 如果使用LineEncoder，那么每次调用ctx.write或者ctx.writeAndflush，LineEncoder都会自动添加换行符，无法模拟拆包问题。
 *
 *      我们通过自定义了一个ChannelInboundHandler，用于在连接建立后，发送3个请求报文req1、req2、req3。其中req1和req2都是一个完整的报文，因为二者都包含一个换行符；req3分两次发送，第一次发送req3_1，第二次发送req3_2。
 *
 * 首先我们将req1、req2和req3_1一起发送：
 *
 *    +------------------------+
 *    | hello1\nhello2\nhello3_the first half |
 *    +------------------------+
 *
 * 而服务端经过解码后，得到两个完整的请求req1、req2、以及req3的部分数据：
 *
 *    +----------+----------+--------
 *    | hello1\n | hello2\n | hello3_the first half
 *    +----------+----------+--------
 *
 * 由于req1、req2都是一个完整的请求，因此可以直接处理。而req3由于只接收到了一部分(半包)，
 * 需要等到2秒后，接收到另一部分才能处理。
 *
 * 因此当我们先后启动server端和client之后，在server端的控制台将会有类似以下输出：
 *
 * LineBasedFrameDecoderServer Started on 8080...
 * 2018-9-8 12:49:02:hello1
 * 2018-9-8 12:49:02:hello2
 * 2018-9-8 12:49:04:hello3_the first half, hello3_the second half
 * 可以看到hello1和hello2是同一时间打印出来的，而hello3是2秒之后才打印。
 * 说明LineBasedFrameDecoder成功帮我们处理了粘包和半包问题。
 *
 * 最后，有几点进行说明：
 *
 * 部分同学可能认为调用一个writeAndFlush方法就是发送了一个请求，这是对协议的理解不够深刻。一个完整的请求是由协议规定的，例如我们在这里使用了LineBasedFrameDecoder，潜在的含义就是：一行数据才算一个完整的报文。因此当你调用writeAndFlush方法，如果发送的数据有多个换行符，意味着相当于发送了多次有效请求；而如果发送的数据不包含换行符，意味着你的数据还不足以构成一个有效请求。
 *
 * 对于粘包问题，例如是两个有效报文粘在一起，那么服务端解码后，可以立即处理这两个报文。
 *
 * 对于拆包问题，例如一个报文是完整的，另一个只是半包，netty会对半包的数据进行缓存，等到可以构成一个完整的有效报文后，才会进行处理。这意味着么netty需要缓存每个client的半包数据，如果很多client都发送半包，缓存的数据就会占用大量内存空间。因此我们在实际开发中，不要像上面案例那样，有意将报文拆开来发送。
 *
 * 此外，如果client发送了半包，而剩余部分内容没有发送就关闭了，对于这种情况，netty服务端在销毁连接时，会自动清空之前缓存的数据，不会一直缓存。
 */
public class LineBasedFrameDecoderClient {

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap(); // (1)
            bootstrap.group(workerGroup);   // (2)
            bootstrap.channel(NioSocketChannel.class); // (3)
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // socketChannel.pipeline().addLast(new LineEncoder()); // 自己添加换行符,不使用LineEncoder
                    socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        // 在与server建立连接后，即发送请求报文
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            byte[] req1 = ("hello1" + System.getProperty("line.separator")).getBytes();
                            byte[] req2 = ("hello2" + System.getProperty("line.separator")).getBytes();
                            byte[] req3_1 = ("hello3_the first half").getBytes();

                            ByteBuf byteBuf = Unpooled.buffer();
                            byteBuf.writeBytes(req1);
                            byteBuf.writeBytes(req2);
                            byteBuf.writeBytes(req3_1);
                            ctx.writeAndFlush(byteBuf);

                            byte[] req3_2 = (", hello3_the second half" + System.getProperty("line.separator")).getBytes();
                            TimeUnit.SECONDS.sleep(2);
                            byteBuf = Unpooled.buffer();
                            byteBuf.writeBytes(req3_2);
                            ctx.writeAndFlush(byteBuf);
                        }
                    });
                }
            });

            // start the client
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8080).sync(); // (5)

            // wait until the connection is closed.
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
