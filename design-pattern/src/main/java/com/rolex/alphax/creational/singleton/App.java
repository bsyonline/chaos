/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.creational.singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author rolex
 * @since 2020
 */
public class App {

    public static void main(String[] args) {
        System.out.println("饿汉式：" + (Singleton1.getInstance() == Singleton1.getInstance()));
        System.out.println("懒汉式（双重校验）：" + (Singleton2.getInstance() == Singleton2.getInstance()));
        System.out.println("静态内部类：" + (Singleton3.getInstance() == Singleton3.getInstance()));
        System.out.println("枚举：" + (Singleton4.INSTANCE == Singleton4.INSTANCE));
        new App().query();
    }

    public void query(){
        Connection conn = JDBCSingleton.INSTANCE.getConnection("jdbc:mysql:///test", "root", "123456");
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("select * from t_user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
