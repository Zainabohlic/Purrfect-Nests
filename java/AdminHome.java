package com.example.java;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import javafx.fxml.FXML;

import javafx.scene.Parent;

import javafx.scene.control.Label;


public class AdminHome {

   // @FXML
    AdminViewProduct adminViewCart = new AdminViewProduct();


    @FXML
    private AnchorPane contentAnchorPane; // Reference to the AnchorPane

    @FXML
    private void handleAddProduct() {
        loadContent("AdminAddProduct.fxml", "Add Product");
    }

    @FXML
    private void handleDeleteProduct() {
        loadContent("AdminDeleteProduct.fxml", "Delete Product");
    }

//    @FXML
//    private void handleViewDatabase() {
//        loadContent("AdminViewProduct.fxml", "View Product");
//        AdminViewProduct obj = new AdminViewProduct();
//        obj.handleViewProductsButton();

//    }



    @FXML
public void handleViewDatabase() {


       loadContent("AdminViewProduct.fxml", "View Product");


}


    @FXML
    private Label hiddenLabel;

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the FXML file for the admin login page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
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


}
