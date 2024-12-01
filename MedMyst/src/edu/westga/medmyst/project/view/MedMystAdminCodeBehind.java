package edu.westga.medmyst.project.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.westga.medmyst.project.viewmodel.MedMystViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Class MedMystLoginCodeBehind.
 * 
 * MedmystLoginCodeBehind provides access to the Login GUI
 * 
 * @author CS 3152
 * @version Fall 2024
 */
public class MedMystAdminCodeBehind {

	@FXML
	private Button clearQueryButton;

	@FXML
	private Button executeQueryButton;

	@FXML
	private Button logoutButton;

	@FXML
	private Button nurseViewButton;

	@FXML
	private TableView<Map<String, Object>> queryResultsTable;

	@FXML
	private TextArea queryTextArea;

	@FXML
	private Label usernameLabel;

	@FXML
	private Button exportResultsButton;

	private MedMystViewModel viewmodel;

	/**
	 * Sets the ViewModel for this controller and updates the username label.
	 *
	 * @param viewmodel The ViewModel to use in this controller.
	 */
	public void setViewModel(MedMystViewModel viewmodel) {
		this.viewmodel = viewmodel;

		if (this.viewmodel.getCurrentUser() != null) {
			String firstName = this.viewmodel.getCurrentUser().getFirstName();
			String userId = this.viewmodel.getCurrentUser().getUserID();
			String lastName = this.viewmodel.getCurrentUser().getLastName();

			this.usernameLabel.setText(String.format("Welcome, %s, %s %s!", userId, firstName, lastName));
		}
	}

	@FXML
	void clearQuery(ActionEvent event) {
		this.queryTextArea.clear();
		this.queryResultsTable.getColumns().clear();
		this.queryResultsTable.getItems().clear();
	}

	@FXML
	void executeQuery(ActionEvent event) {
		String query = this.queryTextArea.getText();
		if (query == null || query.trim().isEmpty()) {
			this.showAlert("Query Error", "Query text cannot be empty.", Alert.AlertType.ERROR);
			return;
		}

		try {
			List<Map<String, Object>> results = this.viewmodel.executeAdminQuery(query);
			this.populateQueryResultsTable(results);
		} catch (SQLException e) {
			e.printStackTrace();
			this.showAlert("SQL Error", "Error executing query: " + e.getMessage(), Alert.AlertType.ERROR);
		}
	}

	@FXML
	private void logout() {
		this.viewmodel.logout();

		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/edu/westga/medmyst/project/view/MedMystLogin.fxml"));
			Pane loginPane = loader.load();
			Stage stage = (Stage) this.logoutButton.getScene().getWindow();
			Scene scene = new Scene(loginPane);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void nurseView(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/edu/westga/medmyst/project/view/MedMystNursePage.fxml"));
			Pane dashboardPane = loader.load();

			MedMystNurseCodeBehind nurseController = loader.getController();

			nurseController.setViewModel(this.viewmodel);

			Stage stage = (Stage) this.nurseViewButton.getScene().getWindow();
			Scene scene = new Scene(dashboardPane);
			stage.setScene(scene);
			javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
			stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
			stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateQueryResultsTable(List<Map<String, Object>> results) {
		if (results == null || results.isEmpty()) {
			this.queryResultsTable.getColumns().clear();
			this.queryResultsTable.setItems(FXCollections.observableArrayList());
			return;
		}

		this.queryResultsTable.getColumns().clear();

		results.get(0).keySet().forEach(columnName -> {
			TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
			column.setCellValueFactory(data -> {
				ObservableValue<Object> value = new SimpleObjectProperty<>(data.getValue().get(columnName));
				return value;
			});
			this.queryResultsTable.getColumns().add(column);
		});

		ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(results);
		this.queryResultsTable.setItems(data);
	}

	@FXML
	void exportResults() {
		if (this.queryResultsTable.getItems() == null || this.queryResultsTable.getItems().isEmpty()) {
			this.showAlert("Export Error", "No results to export.", Alert.AlertType.ERROR);
			return;
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Query Results");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

		File file = fileChooser.showSaveDialog(this.queryResultsTable.getScene().getWindow());
		if (file == null) {
			return;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			List<TableColumn<Map<String, Object>, ?>> columns = this.queryResultsTable.getColumns();
			for (int i = 0; i < columns.size(); i++) {
				writer.write(columns.get(i).getText());
				if (i < columns.size() - 1) {
					writer.write(",");
				}
			}
			writer.newLine();

			for (Map<String, Object> row : this.queryResultsTable.getItems()) {
				for (int i = 0; i < columns.size(); i++) {
					TableColumn<Map<String, Object>, ?> column = columns.get(i);
					Object cellData = column.getCellData(row);
					writer.write(cellData != null ? cellData.toString() : "");
					if (i < columns.size() - 1) {
						writer.write(",");
					}
				}
				writer.newLine();
			}

			this.showAlert("Export Success", "Results exported successfully to " + file.getAbsolutePath(),
					Alert.AlertType.CONFIRMATION);
		} catch (IOException e) {
			e.printStackTrace();
			this.showAlert("Export Error", "Error exporting results: " + e.getMessage(), Alert.AlertType.ERROR);
		}
	}

	private void showAlert(String title, String message, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
