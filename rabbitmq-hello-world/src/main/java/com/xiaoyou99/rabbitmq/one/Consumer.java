package com.xiaoyou99.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class Consumer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置host，user和passowrd
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("password");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            // 设置回调
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                System.out.println("deliverCallback: " + new String(message.getBody(), StandardCharsets.UTF_8));
            };
            CancelCallback cancelCallback = (consumerTag) -> {
                System.out.println("cancelCallback: " + consumerTag);
            };
            /**
             * String basicConsume(String queue, boolean autoAck, DeliverCallback deliverCallback, CancelCallback cancelCallback) throws IOException;
             * 参数1：队列名
             * 参数2：是否自动应答。true为自动应答。
             * true if the server should consider messages acknowledged once delivered; false if the server should expect explicit acknowledgements
             * 参数3：接收到消息时的回调
             * callback when a message is delivered
             * 参数4：消费被取消时的回调
             * callback when the consumer is cancelled
             */
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        }
    }
}
