package controller;

import javafx.scene.control.ToggleGroup;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */

public class ToggleGroupController implements MultipleChiocesController {
    /**
     * the target toggleGroup for controlling
     */
    private ToggleGroup toggleGroup = null;

    public ToggleGroupController(ToggleGroup toggleGroup){
        this.toggleGroup = toggleGroup;
    }

    /**
     * @return the selected toggle value of the toggleGroup
     */
    @Override
    public String getSelected() {
        return (toggleGroup.getSelectedToggle().toString().split("'")[1]);
    }
}
