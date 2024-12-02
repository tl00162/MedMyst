package edu.westga.medmyst.project.view;

import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Code-behind class for the Account Creation Form.
 * 
 * Handles input validation and communication with the ViewModel to create a new account.
 * 
 * @author 
 * @version Fall 2024
 */
public class AccountCreationFormCodeBehind {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private ComboBox<String> accountTypeComboBox;

    @FXML
    private Button createAccountButton;

    @FXML
    private Button cancelButton;

    private MedMystViewModel viewModel;

    /**
     * Initializes the Account Creation Form.
     */
    @FXML
    private void initialize() {
        this.accountTypeComboBox.getItems().addAll("Nurse", "Admin");

        this.createAccountButton.setOnAction(event -> this.handleCreateAccount());
        this.cancelButton.setOnAction(event -> this.closeWindow());
    }

    /**
     * Sets the ViewModel for this form.
     * 
     * @param viewModel the MedMystViewModel to use
     */
    public void setViewModel(MedMystViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Handles the account creation process.
     */
    private void handleCreateAccount() {
        String username = this.usernameTextField.getText().trim();
        String password = this.passwordTextField.getText();
        String confirmPassword = this.confirmPasswordTextField.getText();
        String firstName = this.firstNameTextField.getText().trim();
        String lastName = this.lastNameTextField.getText().trim();
        String accountType = this.accountTypeComboBox.getValue();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty()
                || lastName.isEmpty() || accountType == null) {
            this.showAlert("Validation Error", "All fields must be filled out.", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            this.showAlert("Validation Error", "Passwords do not match.", Alert.AlertType.ERROR);
            return;
        }

        if (password.length() < 8) {
            this.showAlert("Validation Error", "Password must be at least 8 characters long.", Alert.AlertType.ERROR);
            return;
        }

        try {
            boolean success = this.viewModel.createNewAccount(accountType, password, firstName, lastName, username);

            if (success) {
                this.showAlert("Success", "Account created successfully.", Alert.AlertType.INFORMATION);
                this.closeWindow();
            } else {
                this.showAlert("Error", "Failed to create account. Please try again.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            this.showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    /**
     * Closes the current window.
     */
    private void closeWindow() {
        Stage stage = (Stage) this.cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Shows an alert dialog with the specified message.
     * 
     * @param title       the title of the alert
     * @param message     the message to display
     * @param alertType   the type of alert
     */
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
