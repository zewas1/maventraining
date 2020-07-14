package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionMain {
    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        connection.close();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM baldai");
        List<Furniture> baldusarasas = getBaldai(resultSet);
        printBaldai(baldusarasas);
    }

    private static void printBaldai(List<Furniture> baldusarasas) {
        for (Furniture baldas : baldusarasas) {
            System.out.println("ID: " + baldas.getId() + " Name: " + baldas.getName());
        }
    }

    private static List<Furniture> getBaldai(ResultSet resultSet) throws SQLException {
        List<Furniture> baldusarasas = new ArrayList<>();
        while (resultSet.next()) {
            Furniture furniture = new Furniture();
            furniture.setId(resultSet.getInt("id"));
            furniture.setName(resultSet.getString("name"));
            System.out.println(resultSet.getString("name"));
            baldusarasas.add(furniture);
        }
        return baldusarasas;
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String username = "root";
        String password = "5aVkiujLjdimfnY";
        return DriverManager.getConnection(url, username, password);
    }
}