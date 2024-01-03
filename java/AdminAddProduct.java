package com.example.java;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminAddProduct {


    @FXML
    private StackPane stackPane; // Reference to the StackPane

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
        String productName = productNameField.getText();
        String quantityText = quantityField.getText();
        String category = categoryField.getText();
        String priceText = priceField.getText();
        String expiryDate = dateField.getText(); // Assuming dateField is a TextField

        // Check if any required field is empty
        if (productId.isEmpty() || productName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            hiddenLabel.setText("Error: Fields cannot be empty");
            hiddenLabel.setVisible(true);
            return; // Do not proceed with insertion
        }


        // Validate numeric fields
        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            // Call the method to insert data into the Product table
            DatabaseConnector.insertProduct(productId, productName, quantity, category, price, expiryDate);

            // Reset error label
            hiddenLabel.setText("Product Added");
            hiddenLabel.setVisible(true);
        } catch (NumberFormatException e) {
            hiddenLabel.setText("Error: Invalid quantity or price");
            hiddenLabel.setVisible(true);
        }
    }

    @FXML
    private Label hiddenLabel;

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the FXML file for the admin home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
            Parent adminHomeRoot = loader.load();

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the root of the current scene to the admin home page
            currentStage.getScene().setRoot(adminHomeRoot);
        } catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }


}
