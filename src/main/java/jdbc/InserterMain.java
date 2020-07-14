package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InserterMain {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false";
        String username = ""; //  username
        String password = ""; // password
        int selection = 0;

        Scanner scan = new Scanner(System.in);
        List<Baldai> baldusarasas = new ArrayList<>();

        do {
            System.out.println("What would you like to do? 1. insert item, 2. upload item list, 3 show items, 4 exit");
            selection = scan.nextInt();
            if (selection == 1) {
                connection(url, username, password, scan);
            } else if (selection == 2) {
                uploadItems(url, username, password, baldusarasas);
            } else if (selection == 3) {
                showItems(baldusarasas);
            }

        } while (selection != 4);
        System.out.println("psl nx");
    }

    private static void connection(String url, String username, String password, Scanner scan) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            insertData(scan, statement);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static void uploadItems(String url, String username, String password,
                                    List<Baldai> baldusarasas) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM baldai");
        while (resultSet.next()) {
            Baldai baldai = new Baldai();
            baldai.setId(resultSet.getInt("id"));
            baldai.setName(resultSet.getString("name"));
            baldai.setKaina(resultSet.getInt("kaina"));
            baldai.setSvoris(resultSet.getInt("svoris"));
            baldusarasas.add(baldai);
        }
    }

    private static void showItems(List<Baldai> baldusarasas) {
        for (Baldai baldas : baldusarasas) {
            System.out.println("ID: " + baldas.getId() + " Name: " + baldas.getName() + "Weight: " +
                    baldas.getSvoris() + "Price: " + baldas.getKaina());
        }
    }

    private static void insertData(Scanner scan, Statement statement) throws SQLException {
        String insertName;
        int insertPrice;
        int insertWeight;
        System.out.println("Iveskite baldo pavadinima");
        insertName = scan.next();
        System.out.println("Iveskite baldo kaina");
        insertPrice = scan.nextInt();
        System.out.println("Iveskite baldo svori");
        insertWeight = scan.nextInt();
        String sqlString = "INSERT INTO baldai (name,kaina,svoris) VALUES ('" + insertName + "'," +
                insertPrice + "," + insertWeight + ")";
        statement.executeUpdate(sqlString);
    }
}