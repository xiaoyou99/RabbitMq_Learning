package com.xiaoyou99.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionHolder implements AutoCloseable {
    private ConnectionFactory connectionFactory;

    private Connection connection;

    private ConnectionHolder() {
        connectionFactory = new ConnectionFactory();
    }

    public static ConnectionHolder build(RabbitMqConfig rabbitMqConfig) {
        ConnectionHolder holder = new ConnectionHolder();
        holder.connectionFactory.setHost(rabbitMqConfig.getHost());
        holder.connectionFactory.setUsername(rabbitMqConfig.getUserName());
        holder.connectionFactory.setPassword(rabbitMqConfig.getPassword());
        try {
            holder.connection = holder.connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return holder;
    }

    public Channel createChannel() throws IOException {
        return connection.createChannel();
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
