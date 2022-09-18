package com.xiaoyou99.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtils {
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();

    private static Connection connection;

    static {
        // 设置host，user和passowrd
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("password");
    }

    public static Channel createChannel() throws IOException, TimeoutException {
        synchronized (RabbitMqUtils.class){
            if (connection == null) {
                connection = connectionFactory.newConnection();
            }
        }
        return connection.createChannel();
    }

    public static void closeConnection() throws IOException {
        synchronized (RabbitMqUtils.class) {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
