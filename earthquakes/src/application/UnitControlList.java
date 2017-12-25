package application;

import controller.*;
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
import spider.ArrayListToDB;
import spider.MyThreads;
import spider.SpiderOnePage;

/**
 * @Author:  Alan
 * @since : Java_8_151
 * @version:: 1.0
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
    private Label counter;
    @FXML
    private TableView<Earthquake> table;
    @FXML
    private StackPane stpMercator;
    @FXML
    private StackPane stpEckert;
    @FXML
    private ToggleGroup source;
    @FXML
    private Button initSpider;
    /**
     * for judging whether the spider has run in history
     */
    private Boolean hasSpidered = false;

    /**
     * required quakes list for refresh Views
     */
    static ObservableList<Earthquake> quakeList = FXCollections.observableArrayList();

    /**
     * filter get required quakes list
     */
    static WholeFilterController wholeFilter = new WholeFilterController();

    /**
     * required quakes list
     */
    ArrayList<Earthquake> earthquakes = null;

    /**
     * controller for getting all the info that selected by user
     */
    UnitInfo unitInfo = null;

    /**
     * get a counter label Controller
     */
    CounterController counterController = null;

    /**
     * get a tableview Controller
     */
    TableController<Earthquake> tableController = null;

    /**
     * get a MERCATOR Map Controller
     */
    MapStackPaneController mapControllerMERCATOR = null;

    /**
     * get a ECKERT_IV Map Controller
     */
    MapStackPaneController mapControllerECKERT_IV = null;

    /**
     * get a toggleGroupController
     */
    ToggleGroupController toggleGroupController = null;

    /**
     * get a init spider Button Controller
     */
    SpiderController spiderController = new SpiderController();

    public UnitControlList() {
    }

    /**
     * on mouseEnter the stage, init all the required units
     */
    public void initUnits(){
        if (unitInfo == null){
            unitInfo = new UnitInfo(dateFromDatePicker,dateToDatePicker,latitudeMinText,latitudeMaxText,
                    longitudeMinText,longitudeMaxText,depthMinText,depthMaxText,magnitudeMinSd,magnitudeMaxSd);
            this.counterController = new CounterController(counter);
            this.tableController = new TableController<Earthquake>(this.table);
            this.mapControllerMERCATOR = new MapStackPaneController(stpMercator,"MERCATOR");
            this.mapControllerECKERT_IV = new MapStackPaneController(stpEckert,"ECKERT_IV");
            this.toggleGroupController = new ToggleGroupController(source);
            this.initSpider.setOnMouseClicked(E -> {
                if (!hasSpidered){
                    spiderController.fillSpDB();
                    hasSpidered = true;
                }
            });
        }
    }

    /**
     * get search result to refresh table and Maps
     * @param e MouseEvent click
     */
    public void search(MouseEvent e) {
        HashMap info = unitInfo.getAllinfo();
        wholeFilter.setValues((Timestamp) info.get("dateFrom"), (Timestamp)info.get("dateTo"), (float)info.get("latitudeMin"), (float)info.get("latitudeMax"),
                (float)info.get("longitudeMin"),(float)info.get("longitudeMax"), (float)info.get("depthMin"), (float)info.get("depthMax"),
                (float)info.get("magnitudeMin"), (float)info.get("magnitudeMax"));
        if (toggleGroupController.getSelected().equals("File")){
            earthquakes = wholeFilter.getEarthquakeListFromFile();
        }else if (toggleGroupController.getSelected().equals("Database")){
            earthquakes = wholeFilter.getEarthquakeListFromDB();
        }else {
            earthquakes = wholeFilter.getEarthquakeListFromSpDB();
        }
        quakeList.clear();
        for (Earthquake q : earthquakes) {
            quakeList.add(q);
        }
        refreshCounter();
        refreshTable();
        refreshMaps();
    }

    /**
     * private, refresh the table
     */
    private void refreshCounter(){
        counterController.refresh(quakeList.size());
    }

    /**
     * private, refresh the table
     */
    private void refreshTable(){
        tableController.refresh(quakeList);
    }

    /**
     * private, refresh the two Maps
     */
    private void refreshMaps() {
        mapControllerMERCATOR.refresh(quakeList);
        mapControllerECKERT_IV.refresh(quakeList);
    }
}
