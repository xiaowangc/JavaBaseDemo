package com.chige.io.reactor;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author wangyc
 * @date 2022/4/19
 */
public class EchoServerReactor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocketChannel;

    EchoServerReactor() {
        try {
            selector = Selector.open();
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //将新连接处理器作为附件，绑定到选择键
            selectionKey.attach(new AcceptorHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    dispatch(selectionKey);
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void dispatch(SelectionKey sk) {
        Runnable handler = (Runnable) sk.attachment();
        if (handler != null) {
            handler.run();
        }
    }

    public class AcceptorHandler implements Runnable {

        AcceptorHandler() {
            try {
                // 接受新连接
                SocketChannel socketChannel = serverSocketChannel.accept();
                //需要为新连接，创建一个输入输出的 handler 处理器
                if (socketChannel != null) {
                    new EchoHandler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         *
         */
        @Override
        public void run() {

        }
    }

    //负责数据传输的 Handler 处理器
    class EchoHandler implements Runnable{
        final SocketChannel sc;
        SelectionKey selectionKey;
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        static final int RECIEVING = 0, SENDING = 1;
        int state = RECIEVING;
        EchoHandler(Selector selector, SocketChannel socketChannel) {
            sc = socketChannel;
            try {
                socketChannel.configureBlocking(false);
                selectionKey = sc.register(selector, 0);
                selectionKey.attach(this);
                // 注册通道的可读事件
                selectionKey.interestOps(SelectionKey.OP_READ);
                Thread.currentThread().getState();
                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         */
        @SneakyThrows
        @Override
        public void run() {
            if (state == RECIEVING) {
                //接收状态，从通道读取数据
                int readLength = -1;
                while ((readLength = sc.read(byteBuffer)) != -1) {

                }
            }else if (state == SENDING) {

            }
            // 处理输入输出
            if (selectionKey.isReadable()) {
                // 输入
            }else if (selectionKey.isWritable()) {
                // 输出
            }
        }
    }
}
