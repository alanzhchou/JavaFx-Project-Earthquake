package application;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import bean.Earthquake;
import bean.EarthquakeSimple;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import service.SearchService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Main extends Application {
	static ObservableList<EarthquakeSimple> quakeList = FXCollections.observableArrayList();
	@FXML
	private TableView<EarthquakeSimple> table;
	@FXML
	private TableColumn<EarthquakeSimple, String> dateColumn;
	@FXML
	private TableColumn<EarthquakeSimple, String> latitudeColumn;
	@FXML
	private TableColumn<EarthquakeSimple, String> longitudeColumn;
	@FXML
	private TableColumn<EarthquakeSimple, String> depthColumn;
	@FXML
	private TableColumn<EarthquakeSimple, String> magnitudeColumn;
	@FXML
	private TableColumn<EarthquakeSimple, String> regionColumn;
	@FXML
	private TextField longitudeMinText;
	@FXML
	private TextField longitudeMaxText;
	@FXML
	private TextField latitudeMinText;
	@FXML
	private TextField latitudeMaxText;
	@FXML
	private TextField magnitudeMinText;
	@FXML
	private TextField magnitudeMaxText;
	@FXML
	private TextField depthMinText;
	@FXML
	private TextField depthMaxText;
	@FXML
	private DatePicker dateFromDatePicker;
	@FXML
	private DatePicker dateToDatePicker;

	public void search(MouseEvent me) {
		SearchService searcher = new SearchService();
		Timestamp dateFrom = (dateFromDatePicker.getValue() != null)
				? Timestamp.valueOf(dateFromDatePicker.getValue().atTime(0, 0, 0)): Timestamp.valueOf("1970-01-01 00:00:00.0");

		Timestamp dateTo = (dateToDatePicker.getValue() != null)
				? Timestamp.valueOf(dateToDatePicker.getValue().atTime(23, 59, 59)): Timestamp.valueOf(LocalDateTime.now());

		float latitudeMax = (latitudeMaxText.getText() != null && !latitudeMaxText.getText().isEmpty())
				? latitudeMax = Float.parseFloat(latitudeMaxText.getText()): 90;

		float latitudeMin = (latitudeMinText.getText() != null && !latitudeMinText.getText().isEmpty())
				? Float.parseFloat(latitudeMinText.getText()): -90;

		float longitudeMax = (longitudeMaxText.getText() != null && !longitudeMaxText.getText().isEmpty())
				? Float.parseFloat(longitudeMaxText.getText()): 180;

		float longitudeMin = (longitudeMinText.getText() != null && !longitudeMinText.getText().isEmpty())
				? Float.parseFloat(longitudeMinText.getText()): -180;

		float depthMax = (depthMaxText.getText() != null && !depthMaxText.getText().isEmpty())
				? Float.parseFloat(depthMaxText.getText()): Float.MAX_VALUE;

		float depthMin = (depthMinText.getText() != null && !depthMinText.getText().isEmpty())
				? Float.parseFloat(depthMinText.getText()): 0;

		float magnitudeMax = (magnitudeMaxText.getText() != null && !magnitudeMaxText.getText().isEmpty())
				? Float.parseFloat(magnitudeMaxText.getText()): Float.MAX_VALUE;

		float magnitudeMin = (magnitudeMinText.getText() != null && !magnitudeMinText.getText().isEmpty())
				? Float.parseFloat(magnitudeMinText.getText()): 0;

		ArrayList<Earthquake> earthquakes = searcher.search(dateFrom, dateTo, latitudeMin, latitudeMax, longitudeMin,
				longitudeMax, depthMin, depthMax, magnitudeMin, magnitudeMax);
		// System.out.println(earthquakes.get(0).getUTC_date().toString());
		quakeList.clear();
		for (Earthquake q : earthquakes) {
			quakeList.add(new EarthquakeSimple(q));
		}
		dateColumn.setCellValueFactory(new PropertyValueFactory<EarthquakeSimple, String>("UTC_date"));
		latitudeColumn.setCellValueFactory(new PropertyValueFactory<EarthquakeSimple, String>("latitude"));
		longitudeColumn.setCellValueFactory(new PropertyValueFactory<EarthquakeSimple, String>("longitude"));
		depthColumn.setCellValueFactory(new PropertyValueFactory<EarthquakeSimple, String>("depth"));
		magnitudeColumn.setCellValueFactory(new PropertyValueFactory<EarthquakeSimple, String>("magnitude"));
		regionColumn.setCellValueFactory(new PropertyValueFactory<EarthquakeSimple, String>("region"));
		table.setItems(quakeList);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
			primaryStage.setTitle("My Application");

			primaryStage.setScene(new Scene(root));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
