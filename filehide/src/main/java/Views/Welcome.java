package Views;

import java.sql.*;
import java.util.Scanner;

import db.DatabaseManager;

public class Welcome {
    public static void welcomeScreen() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Scanner scanner = new Scanner(System.in);
            System.out.println("1. Register\n2. Login");
            int choice = scanner.nextInt();
           

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void registerUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter The Name: ");
        String name=scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        DatabaseManager dbManager = new DatabaseManager();
        boolean userExists = dbManager.userExists(email);

        if (userExists) {
            System.out.println("User already exists!");
        } else {
            dbManager.registerUser(name,email, password);
            System.out.println("User registered successfully!");
        }
    }

    private static void loginUser() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        DatabaseManager dbManager = new DatabaseManager();
        boolean loginSuccessful = dbManager.loginUser(email, password);

        if (loginSuccessful) {    
            System.out.println("Login successful!");
            new UserView(email).home();
        } else {
            System.out.println("Invalid email or password!");
        }
    }
}
