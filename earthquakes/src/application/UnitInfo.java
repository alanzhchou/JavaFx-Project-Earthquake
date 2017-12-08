package application;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/8.
 * Version 1.0
 */
public class UnitInfo {
    private DatePicker dateFromDatePicker;
    private DatePicker dateToDatePicker;
    private TextField latitudeMinText;
    private TextField latitudeMaxText;
    private TextField longitudeMinText;
    private TextField longitudeMaxText;
    private TextField depthMinText;
    private TextField depthMaxText;
    private Slider magnitudeMinSd;
    private Slider magnitudeMaxSd;

    public UnitInfo(DatePicker dateFromDatePicker, DatePicker dateToDatePicker,
                    TextField latitudeMinText, TextField latitudeMaxText, TextField longitudeMinText, TextField longitudeMaxText,
                    TextField depthMinText, TextField depthMaxText, Slider magnitudeMinSd, Slider magnitudeMaxSd){
        this.dateFromDatePicker = dateFromDatePicker;
        this.dateToDatePicker = dateToDatePicker;
        this.latitudeMinText = latitudeMinText;
        this.latitudeMaxText = latitudeMaxText;
        this.longitudeMinText = longitudeMinText;
        this.longitudeMaxText = longitudeMaxText;
        this.depthMinText = depthMinText;
        this.depthMaxText = depthMaxText;
        this.magnitudeMinSd = magnitudeMinSd;
        this.magnitudeMaxSd = magnitudeMaxSd;
    }

    public HashMap<String,Object> getAllinfo(){
        Timestamp dateFrom = (dateFromDatePicker.getValue() != null)
                ? Timestamp.valueOf(dateFromDatePicker.getValue().atTime(0, 0, 0)): Timestamp.valueOf("2017-10-01 00:00:00.0");

        Timestamp dateTo = (dateToDatePicker.getValue() != null)
                ? Timestamp.valueOf(dateToDatePicker.getValue().atTime(23, 59, 59)): Timestamp.valueOf(LocalDateTime.now());

        float latitudeMax = (latitudeMaxText.getText() != null && !latitudeMaxText.getText().isEmpty())
                ? Float.parseFloat(latitudeMaxText.getText()): 90f;

        float latitudeMin = (latitudeMinText.getText() != null && !latitudeMinText.getText().isEmpty())
                ? Float.parseFloat(latitudeMinText.getText()): -90f;

        float longitudeMax = (longitudeMaxText.getText() != null && !longitudeMaxText.getText().isEmpty())
                ? Float.parseFloat(longitudeMaxText.getText()): 180f;

        float longitudeMin = (longitudeMinText.getText() != null && !longitudeMinText.getText().isEmpty())
                ? Float.parseFloat(longitudeMinText.getText()): -180f;

        float depthMax = (depthMaxText.getText() != null && !depthMaxText.getText().isEmpty())
                ? Float.parseFloat(depthMaxText.getText()): 1000f;

        float depthMin = (depthMinText.getText() != null && !depthMinText.getText().isEmpty())
                ? Float.parseFloat(depthMinText.getText()): 0f;

        float magnitudeMax = (float) magnitudeMaxSd.getValue();

        float magnitudeMin = (float) magnitudeMinSd.getValue();

        HashMap<String,Object> info = new HashMap<String,Object>();
        info.put("dateFrom",dateFrom);
        info.put("dateTo",dateTo);
        info.put("latitudeMax",latitudeMax);
        info.put("latitudeMin",latitudeMin);
        info.put("longitudeMax",longitudeMax);
        info.put("longitudeMin",longitudeMin);
        info.put("depthMax",depthMax);
        info.put("depthMin",depthMin);
        info.put("magnitudeMax",magnitudeMax);
        info.put("magnitudeMin",magnitudeMin);
        return info;
    }
}
