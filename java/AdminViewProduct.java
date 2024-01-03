package com.example.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminViewProduct {

    @FXML
    private VBox productContainer;
    @FXML
    private Label hiddenLabel;

    @FXML
    private AnchorPane contentAnchorPane;


    public void initialize() {
    handleViewProductsButton();
    }
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product, String> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    public void handleViewProductsButton() {

        try {
            // Fetch product data from the database
            List<Product> products = DatabaseConnector.getProductsFromDatabase();

            // Convert the List to an ObservableList for the TableView
            ObservableList<Product> observableProducts = FXCollections.observableArrayList(products);

            // Set up the TableView columns
            productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

            // Set the items in the TableView
            productTableView.setItems(observableProducts);

        } catch (SQLException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }



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


    public void handleClearScreenButton(ActionEvent actionEvent) {
            try {
                // Load the FXML file for the admin view page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminViewProduct.fxml"));
                Parent adminViewRoot = loader.load();

                // Set the loaded FXML as the content of contentAnchorPane
                contentAnchorPane.getChildren().setAll(adminViewRoot);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception (e.g., log it or show an error message)
            }
        }




}
