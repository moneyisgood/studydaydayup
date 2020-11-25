package org.example;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MyConsumer {
    private final static String EXCHANGE_NAME = "SIMPLE_EXCHANGE";
    private final static String QUEUE_NAME = "SIMPLE_QUEUE";

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
        //声明交换机
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        //申明队列
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        System.out.println("Waiting for message...");
        //绑定队列和交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"gupao.best");
        //创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,UTF_8);
                System.out.println("Received Message:'" + msg + "'");
                System.out.println("consumerTag:" + consumerTag);
                System.out.println("dcliveryTag:" + envelope.getDeliveryTag());
            }
        };
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(QUEUE_NAME,true,consumer);



    }

}
