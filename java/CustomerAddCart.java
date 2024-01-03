package com.example.java;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerAddCart {
    @FXML
    private Label hiddenLabel;

    @FXML
    private ListView<String> productCartView; // Assuming the list view displays strings, you can adjust the type based on your data model

    @FXML
    private Label messageLabel;
    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField quantityField;
    @FXML
    private AnchorPane contentAnchorPane;

    @FXML
    private Label loggedInUsernameLabel;

    private String loggedInUsername;  // Store the logged-in username

    @FXML
    private Label totalLabel;

    private double totalCartPrice; // Add a field to store the total cart price

    // Method to set the total cart price
    public void setTotalCartPrice(double totalCartPrice) {
        this.totalCartPrice = totalCartPrice;
    }
    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, String> productIdColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TableColumn<Product, Double> totalPriceColumn;

    public void setLoggedInUsername(String username) {
        loggedInUsername = username;  // Assign the parameter to the instance variable
        loggedInUsernameLabel.setText("Logged in as: " + loggedInUsername);  // Use the instance variable here
    }

    public void initialize() {
        // Call the method to load products when the class is initialized
        handleViewProductsButton();

    }
    @FXML
    private void handleViewProductsButton() {
        try {
            // Fetch product data from the database
            List<Product> products = DatabaseConnector.getProductsFromDatabase();

            // Create a GridPane to display products
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            // Add column headers
            gridPane.add(new Label("Product ID"), 0, 0);
            gridPane.add(new Label("Product Name"), 1, 0);
            gridPane.add(new Label("Quantity"), 2, 0);
            gridPane.add(new Label("Category"), 3, 0);
            gridPane.add(new Label("Price"), 4, 0);
            gridPane.add(new Label("Date"), 5, 0);

            // Populate data
            int rowIndex = 1;
            for (Product product : products) {
                gridPane.add(new Label(product.getProductId()), 0, rowIndex);
                gridPane.add(new Label(product.getProductName()), 1, rowIndex);
                gridPane.add(new Label(Integer.toString(product.getQuantity())), 2, rowIndex);
                gridPane.add(new Label(product.getCategory()), 3, rowIndex);
                gridPane.add(new Label(Double.toString(product.getPrice())), 4, rowIndex);
                gridPane.add(new Label(product.getExpiryDate()), 5, rowIndex);

                rowIndex++;
            }

            // Clear existing content and add the GridPane to the contentAnchorPane
            // contentAnchorPane.getChildren().clear();
            contentAnchorPane.getChildren().add(gridPane);

        } catch (SQLException e) {
            // Handle the exception (e.g., log it or show an error message)
            hiddenLabel.setText("Error fetching product data from the database");
            hiddenLabel.setVisible(true);
        }
    }

    @FXML
    private void handleAddToCartButton(ActionEvent event) throws SQLException {
        try {
            // Retrieve values from text fields
            String productId = productIdField.getText();
            String productName = productNameField.getText();
            int quantity = Integer.parseInt(quantityField.getText());


            if (!DatabaseConnector.doesProductExist(productId)) {
                showMessage("Product not found with ID: " + productId, "red");
                return;  // Stop processing further
            }


            DatabaseConnector.insertIntoCart(loggedInUsername,productId, productName, quantity);


            DatabaseConnector.updateProductQuantity(productId, quantity);


            showMessage("Product added to cart successfully!", "green");
        } catch (NumberFormatException e) {

            showMessage("Please enter a valid quantity.", "red");
        }
    }


    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            // Load the FXML file for the customer home page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerHome.fxml"));
            Parent customerHomeRoot = loader.load();

            // Access the CustomerHome controller
            CustomerHome customerHomeController = loader.getController();

            // Set the logged-in username in the CustomerHome controller
            customerHomeController.setLoggedInUsername(UserContext.getInstance().getLoggedInUsername());

            // Get the stage from any node in the current scene
            Stage currentStage = (Stage) contentAnchorPane.getScene().getWindow();

            // Set the scene for the stage
            currentStage.getScene().setRoot(customerHomeRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String text, String color) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-text-fill: " + color + "; -fx-opacity: 1;");
    }

    @FXML
    private void handleViewCartButton(ActionEvent actionEvent) {
        // Get the logged-in username
        String loggedInUsername = loggedInUsernameLabel.getText().replace("Logged in as: ", "");

        // Fetch cart content for the logged-in user from the database
        List<Product> cartContent = DatabaseConnector.getCartContent(loggedInUsername);

        // Clear existing items in the productCartView
        productCartView.getItems().clear();

        // Add column headers
        String columnHeader = String.format("%-20s%-15s%-10s%-10s", "Product Name", "Product ID", "Quantity", "Total Price");
        productCartView.getItems().add(columnHeader);

        // Add cart items to the productCartView and calculate total sum
        double totalCartPrice = 0.0;

        for (Product product : cartContent) {
            // Customize the display format based on your needs
            String productInfo = String.format("%-20s%-15s%-10d%-10.2f",
                    product.getProductName(), product.getProductId(), product.getQuantity(),
                    product.getPrice() * product.getQuantity());

            productCartView.getItems().add(productInfo);

            // Calculate the total sum
            totalCartPrice += product.getPrice() * product.getQuantity();
        }

        // Display the total sum
        String totalLabel = String.format("%-20s%-15s%-10s%-10.2f", "", "", "Total:", totalCartPrice);
        productCartView.getItems().add(totalLabel);

        // Set the totalCartPrice in the CustomerAddCart controller
        setTotalCartPrice(totalCartPrice);

        // Add additional logic or UI updates as needed
        // For example, you might want to show other information related to the cart.
    }
//    @FXML
//    private void handleViewCartButton(ActionEvent actionEvent) {
//        // Get the logged-in username
//        String loggedInUsername = loggedInUsernameLabel.getText().replace("Welcome: ", "");
//
//        // Fetch cart content for the logged-in user from the database
//        List<Product> cartContent = DatabaseConnector.getCartContent(loggedInUsername);
//
//        // Clear existing items in the productCartView
//        productCartView.getItems().clear();
//
//        // Add column headers
//        String columnHeader = String.format("%-20s%-15s%-10s%-10s", "Product Name", "Product ID", "Quantity", "Total Price");
//        productCartView.getItems().add(columnHeader);
//
//        // Add cart items to the productCartView and calculate total sum
//        double totalCartPrice = 0.0;
//
//        for (Product product : cartContent) {
//            // Customize the display format based on your needs
//            String productInfo = String.format("%-20s%-15s%-10d%-10.2f",
//                    product.getProductName(), product.getProductId(), product.getQuantity(),
//                    product.getPrice() * product.getQuantity());
//
//            productCartView.getItems().add(productInfo);
//
//            // Calculate the total sum
//            totalCartPrice += product.getPrice() * product.getQuantity();
//        }
//
//        // Display the total sum
//        String totalLabel = String.format("%-20s%-15s%-10s%-10.2f", "", "", "Total:", totalCartPrice);
//        productCartView.getItems().add(totalLabel);
//
//        // Set the totalCartPrice in the CustomerAddCart controller
//        setTotalCartPrice(totalCartPrice);
//
//    }
    @FXML
    private void handleProceedToPaymentButton(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the payment page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerPayment.fxml"));
            Parent paymentPageRoot = loader.load();

            // Access the controller for the payment page
            CustomerPayment customerPaymentController = loader.getController();

            // Set the totalCartPrice in the CustomerPayment controller
            customerPaymentController.setTotalCartPrice(totalCartPrice);

            // Get the current stage
            Stage currentStage = (Stage) productCartView.getScene().getWindow();

            // Set the new scene
            currentStage.setScene(new Scene(paymentPageRoot));
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


}
