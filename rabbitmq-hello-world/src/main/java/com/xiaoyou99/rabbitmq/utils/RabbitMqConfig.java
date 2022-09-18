package com.xiaoyou99.rabbitmq.utils;

public class RabbitMqConfig {
    private String host;

    private String userName;

    private String password;

    public RabbitMqConfig(String host, String userName, String password) {
        this.host = host;
        this.userName = userName;
        this.password = password;
    }


    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
