package controller;

import javafx.scene.control.ToggleGroup;

public class ToggleGroupController implements MultipleChiocesController {
    private ToggleGroup toggleGroup = null;

    public ToggleGroupController(ToggleGroup toggleGroup){
        this.toggleGroup = toggleGroup;
    }

    @Override
    public String getSelected() {
        return (toggleGroup.getSelectedToggle().toString().split("'")[1]);
    }
}
