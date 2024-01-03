package com.example.java;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.layout.StackPane;
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

public class AdminLogin {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private StackPane contentStackPane; // Change the type to StackPane

    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection connection = DatabaseConnector.connect()) {
            String query = "SELECT * FROM user WHERE username = ? AND pw = ? AND type = 'admin'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Successful login
                    errorLabel.setText("Login Successful");

                    // Load the home page
                    loadHomePage();
                } else {
                    // Failed login
                    errorLabel.setText("Invalid username or password");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // Handle database connection errors or IO exceptions
        }
    }

    private void loadHomePage() throws IOException {
        Parent homePage = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
        Scene scene = new Scene(homePage);

        // Get the current stage
        Stage stage = (Stage) usernameField.getScene().getWindow();

        // Set the new scene
        stage.setScene(scene);
    }

    private boolean isValidLogin(String username, String password) {
        // Implement your authentication logic here
        // For simplicity, you can hardcode a username and password for testing
        return username.equals("") && password.equals("");
    }

    @FXML
    private void handleBackButton() {
        try {
            // Load the FXML file for the hello-view page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent helloViewRoot = loader.load();

            // Create a new scene with the hello-view as the root
            Scene helloViewScene = new Scene(helloViewRoot);

            // Get the stage from any node in the current scene (e.g., usernameField)
            Stage currentStage = (Stage) usernameField.getScene().getWindow();

            // Set the scene for the stage
            currentStage.setScene(helloViewScene);
        } catch (IOException e) {
            // Handle the exception (e.g., log it or show an error message)
            e.printStackTrace();
        }
    }


}
