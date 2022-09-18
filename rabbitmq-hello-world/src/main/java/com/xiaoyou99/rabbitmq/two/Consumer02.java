package com.xiaoyou99.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.xiaoyou99.rabbitmq.utils.RabbitMqUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Consumer02 implements Runnable {
    private static final String QUEUE_NAME = "hello";

    @Override
    public void run() {
        System.out.println("Consumer Thread begin: " + Thread.currentThread().getName());
        try (Channel channel = RabbitMqUtils.createChannel()) {
            String threadName = Thread.currentThread().getName();
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                System.out.println("Theard " + threadName
                        + " delivers: " + new String(message.getBody(), StandardCharsets.UTF_8));
            };
            CancelCallback cancelCallback = (consumerTag) -> {
                System.out.println("Theard " + threadName + " cancels");
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
            Thread.sleep(50 * 1000);
            System.out.println("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(new Consumer02(), String.format("Thread[%s]", i));
            list.add(thread);
        }
        list.stream().forEach(Thread::start);
        try {
            for (Thread thread : list) {
                thread.join();
            }
        } finally {
            RabbitMqUtils.closeConnection();
        }
    }
}
