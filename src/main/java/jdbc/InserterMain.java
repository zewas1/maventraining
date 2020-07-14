package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
In order to test this code:
1. create a database called "testdb"
2. create a table called furniture with these values: ID (INT), NAME (VARCHAR), PRICE(INT), WEIGHT(INT)
 */

public class InserterMain {
    public static List<Furniture> tempCounter = new ArrayList<>();
    public static List<Furniture> furnitureList = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String username = ""; //  username
        String password = ""; // password
        int selection = 0;

        Scanner scan = new Scanner(System.in);


        do {
            System.out.println("What would you like to do? 1. insert new item, 2. upload item list, 3 show items, 4 exit");
            selection = scan.nextInt();
            if (selection == 1) {
                connection(url, username, password, scan);
            } else if (selection == 2) {
                uploadItems(url, username, password, furnitureList);
                tempCounter = furnitureList;
            } else if (selection == 3) {
                showItems(furnitureList);
            }
        } while (selection != 4);
    }

    private static void connection(String url, String username, String password, Scanner scan) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            insertData(scan, statement);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        };
    }

    private static void uploadItems(String url, String username, String password,
                                    List<Furniture> furnitureList) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();


        if (furnitureList.size() > tempCounter.size() || furnitureList.isEmpty())
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM furniture");
            while (resultSet.next()) {
                Furniture furniture = new Furniture();
                furniture.setId(resultSet.getInt("id"));
                furniture.setName(resultSet.getString("name"));
                furniture.setPrice(resultSet.getInt("price"));
                furniture.setWeight(resultSet.getInt("weight"));
                furnitureList.add(furniture);
            }
            resultSet.close();
            System.out.println("Item list uploaded!");
        }
        else {
            System.out.println("Item list did not change.");
        }
        statement.close();
        connection.close();
    }

    private static void showItems(List<Furniture> furnitureList) {
        for (Furniture furniture : furnitureList) {
            System.out.println("ID: " + furniture.getId() + ", Name: " + furniture.getName() + ", Weight: " +
                    furniture.getWeight() + ", Price: " + furniture.getPrice());
        }
    }

    private static void insertData(Scanner scan, Statement statement) throws SQLException {
        String insertName;
        int insertPrice;
        int insertWeight;
        System.out.println("Insert the name of the item");
        insertName = scan.next();
        System.out.println("Insert price of the item");
        insertPrice = scan.nextInt();
        System.out.println("Insert weight of the item");
        insertWeight = scan.nextInt();
        String sqlString = "INSERT INTO furniture (name,price,weight) VALUES ('" + insertName + "'," +
                insertPrice + "," + insertWeight + ")";
        statement.executeUpdate(sqlString);
    }
}