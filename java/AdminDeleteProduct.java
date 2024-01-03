package com.example.java;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class AdminDeleteProduct {

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField dateField;

    @FXML
    private void initialize() {
        // Populate ComboBox components with options (e.g., days, months, years)
        // Implement logic to fill the ComboBox components
    }

    @FXML
    private void handleConfirmButton() {
        String productId = productIdField.getText();

        // Check if any required field is empty
        if (productId.isEmpty()) {
            hiddenLabel.setText("Error: Product ID cannot be empty");
            hiddenLabel.setVisible(true);
            return; // Do not proceed with deletion
        }

        // Now, proceed with the deletion
        try (Connection connection = DatabaseConnector.connect()) {
            // Check if the product is in the cart
            String checkCartQuery = "SELECT COUNT(*) FROM Cart WHERE productId = ?";
            try (PreparedStatement checkCartStatement = connection.prepareStatement(checkCartQuery)) {
                checkCartStatement.setString(1, productId);

                // Execute the query
                try (ResultSet cartResult = checkCartStatement.executeQuery()) {
                    // Check if the product is in the cart
                    if (cartResult.next() && cartResult.getInt(1) > 0) {
                        // Product is in the cart, delete from cart first
                        String deleteCartQuery = "DELETE FROM Cart WHERE productId = ?";
                        try (PreparedStatement deleteCartStatement = connection.prepareStatement(deleteCartQuery)) {
                            deleteCartStatement.setString(1, productId);
                            deleteCartStatement.executeUpdate();
                        }
                    }
                }
            }

            // Now, delete from the product table
            String deleteProductQuery = "DELETE FROM Product WHERE productId = ?";
            try (PreparedStatement deleteProductStatement = connection.prepareStatement(deleteProductQuery)) {
                deleteProductStatement.setString(1, productId);

                // Execute the deletion
                int rowsDeleted = deleteProductStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    // Deletion successful
                    hiddenLabel.setText("Product deleted successfully");
                    hiddenLabel.setVisible(true);
                } else {
                    // No rows were deleted (product not found)
                    hiddenLabel.setText("Error: Product not found");
                    hiddenLabel.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }




    @FXML
    private Label hiddenLabel;

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the FXML file for the admin login page
            Parent adminLoginRoot = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the root of the current scene to the admin login page
            currentStage.getScene().setRoot(adminLoginRoot);
        } catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }
}
