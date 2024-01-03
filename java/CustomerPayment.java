package com.example.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerPayment {
    @FXML
    private TextField accountNumberField;
    @FXML
    private AnchorPane contentAnchorPane;
    @FXML
    private TextField addressLine1Field;
    @FXML
    private TextField addressLine2Field;
    @FXML
    private TextField cityField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private TextField bankNameField;
    @FXML
    private TextField amountField;
    @FXML
    private Label paymentSuccessLabel; // Inject the label

    @FXML
    private Label totalCartPriceLabel;




    private double totalCartPrice;

    public void setTotalCartPrice(double totalCartPrice) {
        this.totalCartPrice = totalCartPrice;

        // Update the totalCartPriceLabel when the totalCartPrice is set
        updateTotalCartPriceLabel();
    }

    @FXML
    public void initialize() {
        // Other initialization tasks can go here

        // You can also call updateTotalCartPriceLabel() here if needed
    }

    private void updateTotalCartPriceLabel() {
        // Display the total cart price in the label
        totalCartPriceLabel.setText("Total Cart Price: " + String.format("%.2f", totalCartPrice));
    }




    @FXML
    private void handleProceedToPaymentButton(ActionEvent actionEvent) {
        // Get the values from the text fields
        String accountNumber = accountNumberField.getText();
        String addressLine1 = addressLine1Field.getText();
        String addressLine2 = addressLine2Field.getText();
        String city = cityField.getText();
        String zipCode = zipCodeField.getText();
        String contactNumber = contactNumberField.getText();
        String bankName = bankNameField.getText();
        String amount = amountField.getText();

        // Get the logged-in username
        String loggedInUsername = UserContext.getInstance().getLoggedInUsername();

        // Insert data into the payment table
        DatabaseConnector.insertIntoPayment(loggedInUsername, accountNumber, bankName, amount);

        // Insert data into the address table
        DatabaseConnector.insertIntoAddress(loggedInUsername, addressLine1, addressLine2, city, zipCode, contactNumber);

        // Show the payment success label
        paymentSuccessLabel.setVisible(true);

        // Add any additional logic or UI updates as needed
        // For example, you might navigate to another page.
    }

    @FXML
    public void handleBackButton(ActionEvent event) {
        try {
            // Load the FXML file for the customer add cart page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerAddCart.fxml"));
            Parent customerAddCartRoot = loader.load();

            // Access the CustomerAddCart controller
            CustomerAddCart customerAddCartController = loader.getController();

            // Set the logged-in username in the CustomerAddCart controller
            customerAddCartController.setLoggedInUsername(UserContext.getInstance().getLoggedInUsername());

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the root of the scene to the customerAddCartRoot
            currentStage.getScene().setRoot(customerAddCartRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
