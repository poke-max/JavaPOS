package org.fcyt.model;

import java.sql.*;
public class ConnectDB {
    public Connection connection;

    public ConnectDB() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/punto_venta",
                    "postgres",
                    "onukitaeko78"
            );
            System.out.println("Conexión exitosa.");


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error de conexión.");
        }
    }

    public static void main(String[] args) {
        new ConnectDB();
    }
}
