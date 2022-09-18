package com.xiaoyou99.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.xiaoyou99.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer02 {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        try(Channel channel = RabbitMqUtils.createChannel()) {
            /**
             * 定义队列
             * 参数1：队列名称
             * 参数2：是否支持持久化。若为false，存在内存中
             * 参数3：该队列是否只供一个消费者进行消费，是否进行共享。true即为可共享
             * 参数4：是否自动删除，即最后一个消费者断开连接之后，是否自动删除队列。true为自动删除
             * 参数5：其他参数
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // 从控制台获取message
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                /**
                 * 发送消息
                 * 参数1：Exchange
                 * 参数2：router key
                 * 参数3：其他参数信息
                 * 参数4：要发送的消息
                 */
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println("send message: " + message);
            }
        }
    }
}
