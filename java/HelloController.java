package com.example.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HelloController {

    @FXML
    private StackPane contentStackPane; // The container to hold different views

    // ... existing code ...

    @FXML
    private void handleAdminLogin() {
        loadLoginScene("AdminLogin.fxml", "Admin Login");
    }

    @FXML
    private void handleCustomerLogin() {
        loadLoginScene("CustomerLogin.fxml", "Customer Login");
    }

    private void loadLoginScene(String fxmlFile, String title) {
        try {
            // Load the corresponding FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent loginRoot = loader.load();

            // Set the content of the contentStackPane to the loaded FXML
            contentStackPane.getChildren().clear();
            contentStackPane.getChildren().add(loginRoot);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
}
