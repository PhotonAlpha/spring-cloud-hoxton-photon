package top.photon.echo;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }
    
    public static void main(String[] args) throws Exception {
//        if(args.length != 1) {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
//            return ;
//        }
//        int port = Integer.parseInt(args[0]); //1
//        new EchoServer(port).start();   //2
        Double a = 123.1;
        Double b = 123.1;
        System.out.println(EchoServer.multiply(a, b));
    }
    public static Double multiply(Double a, Double b) {
        return   a * b;
    }
    
    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup(); //3
        
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)  //4
                .channel(NioServerSocketChannel.class) //5
                .localAddress(new InetSocketAddress(port)) //6
                .childHandler(new ChannelInitializer<SocketChannel>() { //7
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });
            
            ChannelFuture f = bootstrap.bind().sync();  //8
            System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());
            f.channel().closeFuture().sync();   //9
        } finally {
            group.shutdownGracefully().sync();  //10
        }
    }
    
    
}
