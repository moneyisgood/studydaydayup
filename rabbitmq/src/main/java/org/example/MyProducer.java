package org.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MyProducer {
    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectFactory = new ConnectionFactory();
        //设置IP
        connectFactory.setHost("192.168.31.218");
        //设置监听端口
        connectFactory.setPort(5672);
        //设置访问的用户 guest只能访问本机rabbitmq
        connectFactory.setUsername("test");
        connectFactory.setPassword("test");
        //设置VHost
        connectFactory.setVirtualHost("/");
        //建立连接
        Connection conn = connectFactory.newConnection();
        //建立消息通道
        Channel channel = conn.createChannel();
        String msg = "Hello world,RabbitMQ";
        channel.basicPublish(EXCHANGE_NAME,"gupao.best",null,msg.getBytes());
        channel.close();
        conn.close();
    }

}
