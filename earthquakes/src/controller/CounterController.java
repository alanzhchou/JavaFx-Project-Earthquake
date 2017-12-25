package controller;

import javafx.scene.control.Label;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class CounterController implements ViewLikeController {
    private Label label;

    public CounterController(Label label){
        this.label = label;
    }

    @Override
    public void refresh() {
    }

    public void refresh(int count){
        this.label.setText(String.valueOf(count) + " selected");
    }
}
