package edu.westga.medmyst.project;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * TextTwistApplication extends the JavaFX Application class to build the GUI
 * and start program execution.
 * 
 * @author CS 3152
 * @version Fall 2024
 */
public class MedMystApplication extends Application {

	private static final String GUI_FXML = "view/MedMystLogin.fxml";
	private static final String TITLE = "MedMyst";

	/**
	 * Constructs a new Application object for the MathPracticeApplication program.
	 */
	public MedMystApplication() {
		super();
	}

	/**
	 * Starts the application and sets the main stage.
	 * 
	 * @param primaryStage The main stage to set for the application.
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane pane = this.loadGui();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle(TITLE);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Pane loadGui() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(GUI_FXML));
		return (Pane) loader.load();
	}

	/**
	 * Launches the application.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
