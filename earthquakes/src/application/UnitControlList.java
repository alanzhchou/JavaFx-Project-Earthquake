package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import bean.Earthquake;
import bean.EarthquakeSimple;
import controller.WholeFilterController;
import dao.Reader_Csv_Filter;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7.
 * Version 1.0
 */
public class UnitControlList {
    @FXML
    private DatePicker dateFromDatePicker;
    @FXML
    private DatePicker dateToDatePicker;
    @FXML
    private TextField latitudeMinText;
    @FXML
    private TextField latitudeMaxText;
    @FXML
    private TextField longitudeMinText;
    @FXML
    private TextField longitudeMaxText;
    @FXML
    private TextField depthMinText;
    @FXML
    private TextField depthMaxText;
    @FXML
    private TextField magnitudeMinText;
    @FXML
    private TextField magnitudeMaxText;
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
    private StackPane stpMercator;
    @FXML
    private ImageView imageMERCATOR;
    private WorldMap wmm;
    private int mW;
    private int mH;

    @FXML
    private StackPane stpEckert;
    @FXML
    private ImageView imageEckertIV ;
    private WorldMap wme ;
    private int eW;
    private int eH;


    static ObservableList<EarthquakeSimple> quakeList = FXCollections.observableArrayList();
    static WholeFilterController wholeFilter = new WholeFilterController();
    UnitInfo unitInfo;

    public void checkUnitInfo(){
        if (unitInfo == null){
            unitInfo = new UnitInfo(dateFromDatePicker,dateToDatePicker,latitudeMinText,latitudeMaxText,
                    longitudeMinText,longitudeMaxText,depthMinText,depthMaxText,magnitudeMinText,magnitudeMaxText);
            wmm = new WorldMap(imageMERCATOR.getImage(), WorldMap.Projection.MERCATOR, 180);
            wme = new WorldMap(imageEckertIV.getImage(), WorldMap.Projection.ECKERT_IV,180);
            mW = (int) imageMERCATOR.getImage().getWidth();
            mH = (int) imageMERCATOR.getImage().getHeight();
            eW = (int) imageEckertIV.getImage().getWidth();
            eH = (int) imageEckertIV.getImage().getHeight();
        }
    }

    public void search(MouseEvent me) {
        checkUnitInfo();
        refreshTable();
        refreshMaps();
    }

    private void refreshTable(){
        HashMap info = unitInfo.getAllinfo();
        wholeFilter.setValues((Timestamp) info.get("dateFrom"), (Timestamp)info.get("dateTo"), (float)info.get("latitudeMin"), (float)info.get("latitudeMax"),
                (float)info.get("longitudeMin"),(float)info.get("longitudeMax"), (float)info.get("depthMin"), (float)info.get("depthMax"),
                (float)info.get("magnitudeMin"), (float)info.get("magnitudeMax"));

        ArrayList<Earthquake> earthquakes = new Reader_Csv_Filter().getEarthquakeList(wholeFilter);

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

    private void refreshMaps() {
        float magnitude;
        int diameter;
        int[] xy;
        ObservableList<Node> paneChildren;

        // Remove existing canvases
        paneChildren = stpMercator.getChildren();
        if (paneChildren.size() > 1) {
            paneChildren.remove(1, paneChildren.size());
        }
        paneChildren = stpEckert.getChildren();
        if (paneChildren.size() > 1) {
            paneChildren.remove(1, paneChildren.size());
        }

        Canvas cvMercator = new Canvas(mW, mH);
        GraphicsContext gcm = cvMercator.getGraphicsContext2D();
        Canvas cvEckert = new Canvas(eW, eH);
        GraphicsContext gce = cvEckert.getGraphicsContext2D();
        gcm.setStroke(Color.RED);
        gcm.setLineWidth(2);
        gce.setStroke(Color.RED);
        gce.setLineWidth(2);
        for (EarthquakeSimple q : quakeList) {
            diameter = (int) (5 * q.getMagnitude() - 4);
            xy = wmm.imgxy(q.getLatitude(), q.getLongitude());
            gcm.strokeOval(xy[0] - diameter / 2, xy[1] - diameter / 2, diameter, diameter);
            xy = wme.imgxy(q.getLatitude(), q.getLongitude());
            gce.strokeOval(xy[0] - diameter / 2, xy[1] - diameter / 2, diameter, diameter);
        }
        stpMercator.getChildren().add(cvMercator);
        stpEckert.getChildren().add(cvEckert);
    }
}
