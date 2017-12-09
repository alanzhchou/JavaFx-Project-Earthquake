package application;

import controller.MapStackPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import bean.Earthquake;
import controller.WholeFilterController;
import controller.TableController;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/7
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
    private Slider magnitudeMinSd;
    @FXML
    private Slider magnitudeMaxSd;
    @FXML
    private TableView<Earthquake> table;
    @FXML
    private StackPane stpMercator;
    @FXML
    private StackPane stpEckert;

    static ObservableList<Earthquake> quakeList = FXCollections.observableArrayList();
    ArrayList<Earthquake> earthquakes = null;
    static WholeFilterController wholeFilter = new WholeFilterController();
    TableController<Earthquake> tableController = null;
    MapStackPaneController mapControllerMERCATOR = null;
    MapStackPaneController mapControllerECKERT_IV = null;
    UnitInfo unitInfo = null;

    public void initUnits(){
        if (unitInfo == null){
            unitInfo = new UnitInfo(dateFromDatePicker,dateToDatePicker,latitudeMinText,latitudeMaxText,
                    longitudeMinText,longitudeMaxText,depthMinText,depthMaxText,magnitudeMinSd,magnitudeMaxSd);
            this.tableController = new TableController<Earthquake>(this.table);
            this.mapControllerMERCATOR = new MapStackPaneController(stpMercator,"MERCATOR");
            this.mapControllerECKERT_IV = new MapStackPaneController(stpEckert,"ECKERT_IV");
        }
    }

    public void search(MouseEvent e) {
        HashMap info = unitInfo.getAllinfo();
        wholeFilter.setValues((Timestamp) info.get("dateFrom"), (Timestamp)info.get("dateTo"), (float)info.get("latitudeMin"), (float)info.get("latitudeMax"),
                (float)info.get("longitudeMin"),(float)info.get("longitudeMax"), (float)info.get("depthMin"), (float)info.get("depthMax"),
                (float)info.get("magnitudeMin"), (float)info.get("magnitudeMax"));
        earthquakes = wholeFilter.getEarthquakeList();
        quakeList.clear();
        for (Earthquake q : earthquakes) {
            quakeList.add(q);
        }
        refreshTable();
        refreshMaps();
    }

    private void refreshTable(){
        tableController.refresh(quakeList);
    }

    private void refreshMaps() {
        mapControllerMERCATOR.refresh(quakeList);
        mapControllerECKERT_IV.refresh(quakeList);
    }
}
