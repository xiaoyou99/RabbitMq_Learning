package com.xiaoyou99.rabbitmq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */
public class Producer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置ip，user，password
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("password");
        // 获取Connection 和 Channel，它们实现了 autoClosable
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            /**
             * 定义队列
             * 参数1：队列名称
             * 参数2：是否支持持久化。若为false，存在内存中
             * 参数3：该队列是否只供一个消费者进行消费，是否进行共享。true即为可共享
             * 参数4：是否自动删除，即最后一个消费者断开连接之后，是否自动删除队列。true为自动删除
             * 参数5：其他参数
             */
            channel.queueDeclare(QUEUE_NAME, false, false,false, null);
            // 定义消息
            String message = "hello world!";
            /**
             * 发送消息
             * 参数1：Exchange
             * 参数2：router key
             * 参数3：其他参数信息
             * 参数4：要发送的消息
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
        System.out.println("Send Message done...");
    }
}
