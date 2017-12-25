package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class Main extends Application {
	/**
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
			primaryStage.setTitle("EarthQuake View");

			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * main entrance
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
