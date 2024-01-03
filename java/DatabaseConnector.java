package com.example.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load the MySQL JDBC driver. Make sure it is in the classpath.");
        }
    }

    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/databasetest";
        String username = "root";
        String password = "Mysql123";

        return DriverManager.getConnection(url, username, password);
    }

    public static void insertProduct(String productId, String productName, int quantity, String category, double price, String expiryDate) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO Product (productId, productName, quantity, category, price, expiryDate) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, productId);
                preparedStatement.setString(2, productName);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setString(4, category);
                preparedStatement.setDouble(5, price);
                preparedStatement.setString(6, expiryDate);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public static void deleteProduct(String productId) {
        try (Connection connection = connect()) {
            String query = "DELETE FROM Product WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, productId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Product deleted successfully");
                } else {
                    System.out.println("Product not found with ID: " + productId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public static List<Product> getProductsFromDatabase() throws SQLException {
        List<Product> products = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT * FROM Product";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Product product = new Product();
                        product.setProductId(resultSet.getString("productId"));
                        product.setProductName(resultSet.getString("productName"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setCategory(resultSet.getString("category"));
                        product.setPrice(resultSet.getDouble("price"));
                        product.setExpiryDate(resultSet.getString("expiryDate"));
                        products.add(product);
                    }
                }
            }
        }

        return products;
    }

    public static void insertIntoCart(String username, String productId, String productName, int quantity) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO cart (username, productid, productname, quantity) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, productId);
                preparedStatement.setString(3, productName);
                preparedStatement.setInt(4, quantity);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }


    public static void updateProductQuantity(String productId, int quantity) {
        try (Connection connection = connect()) {
            String query = "UPDATE product SET quantity = quantity - ? WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, quantity);
                preparedStatement.setString(2, productId);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public static boolean doesProductExist(String productId) {
        try (Connection connection = connect()) {
            String query = "SELECT COUNT(*) FROM Product WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, productId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return false; // Default to false in case of an exception
    }

//    public static List<Product> getCartContent(String loggedInUsername) {
//        List<Product> cartContent = new ArrayList<>();
//
//        try (Connection connection = connect()) {
//            String query = "SELECT * FROM cart WHERE username = ?";
//            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, loggedInUsername);
//
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    while (resultSet.next()) {
//                        Product product = new Product();
//                        product.setProductId(resultSet.getString("productid"));
//                        product.setProductName(resultSet.getString("productname"));
//                        product.setQuantity(resultSet.getInt("quantity"));
//                        // Add additional properties as needed
//
//                        cartContent.add(product);
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle the exception according to your needs
//        }
//
//        return cartContent;
//    }


    public static void insertIntoPayment(String username, String accountNumber, String bankName, String amount) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO payment (username, accountNo, bankName, amount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, accountNumber);
                preparedStatement.setString(3, bankName);

                // Assuming amount is a string, use setString
                preparedStatement.setString(4, amount);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

    public static void insertIntoAddress(String username, String addressLine1, String addressLine2, String city, String zipCode, String contactNumber) {
        try (Connection connection = connect()) {
            String query = "INSERT INTO address (username, addressLine1, addressLine2, city, zipCode, contactNo) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, addressLine1);
                preparedStatement.setString(3, addressLine2);
                preparedStatement.setString(4, city);
                preparedStatement.setString(5, zipCode);
                preparedStatement.setString(6, contactNumber);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Address inserted successfully!");
                } else {
                    System.out.println("Failed to insert address.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs, log it, or show a user-friendly message
        }
    }

    public static List<Product> getCartContent(String loggedInUsername) {
        List<Product> cartContent = new ArrayList<>();

        try (Connection connection = connect()) {
            String query = "SELECT c.productid, c.productname, c.quantity, p.price, (c.quantity * p.price) AS total_price " +
                    "FROM cart c " +
                    "JOIN product p ON c.productid = p.productid " +
                    "WHERE c.username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, loggedInUsername);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Product product = new Product();
                        product.setProductId(resultSet.getString("productid"));
                        product.setProductName(resultSet.getString("productname"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setPrice(resultSet.getDouble("price"));
                        product.setTotalPrice(resultSet.getDouble("total_price"));

                        cartContent.add(product);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }

        return cartContent;
    }

}
