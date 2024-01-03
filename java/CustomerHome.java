package com.example.java;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.fxml.FXML;

import javafx.scene.Parent;

import javafx.scene.control.Label;



public class CustomerHome {

    @FXML
    private AnchorPane contentAnchorPane; // Reference to the AnchorPane

//    @FXML
//    private void handleAddProductCart() {
//        loadContent("CustomerAddCart.fxml", "Add Product");
//    }
    @FXML
    private Label welcomeLabel; // New label to display welcome message
    @FXML
    private Label loggedInUsernameLabel;
    // Get the logged-in username
    String loggedInUsername = UserContext.getInstance().getLoggedInUsername();


    public void setLoggedInUsername(String username) {
        loggedInUsername = username;
        // Update the welcome label
        welcomeLabel.setText("Welcome, " + loggedInUsername + "!");
    }


    @FXML
    private Label hiddenLabel;

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the FXML file for the admin login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerLogin.fxml"));
            VBox adminLoginRoot = loader.load();

            // Create a new scene with the admin login page as the root
            Scene adminLoginScene = new Scene(adminLoginRoot);

            // Get the stage from the hiddenLabel
            Stage currentStage = (Stage) hiddenLabel.getScene().getWindow();

            // Set the scene for the stage
            currentStage.setScene(adminLoginScene);
        } catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAddProductCart() {
        try {
            // Load the FXML file for the CustomerAddCart page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerAddCart.fxml"));
            Parent addCartRoot = loader.load();

            // Access the controller of the CustomerAddCart page
            CustomerAddCart addCartController = loader.getController();

            // Pass the logged-in username to the CustomerAddCart controller
            addCartController.setLoggedInUsername(loggedInUsername);

            // Create a new scene with the CustomerAddCart page as the root
            Scene addCartScene = new Scene(addCartRoot);

            // Get the stage from the contentAnchorPane
            Stage currentStage = (Stage) contentAnchorPane.getScene().getWindow();

            // Set the scene for the stage
            currentStage.setScene(addCartScene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }


    private void loadContent(String fxmlFile, String title) {
        try {
            // Load the corresponding FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent contentRoot = loader.load();

            // Create a new scene with the loaded FXML content
            Scene newScene = new Scene(contentRoot);

            // Get the stage from the contentAnchorPane
            Stage currentStage = (Stage) contentAnchorPane.getScene().getWindow();

            // Set the new scene to the stage
            currentStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }


    public void updateContent() {
        // Check if the username is already set
        if (loggedInUsername != null) {
            // Load or refresh the content based on the logged-in user
            String loadedContent = loadContentForUser(loggedInUsername);

            // Now you can use the 'loadedContent' variable as needed in your UI
            // For example, you might set it as text in a label or update a ListView.
            System.out.println("Updating content: " + loadedContent);
        } else {
            // Handle the case where the username is not set
            System.out.println("No logged-in user found.");
        }
    }

    // Implement your logic to load or refresh content based on the logged-in user
    private String loadContentForUser(String loggedInUsername) {
        // Placeholder logic - replace it with your actual implementation
        return "Content for user: " + loggedInUsername;
    }
}
