package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class View {
    @FXML
    private Label labelFx;

    public void setViewLabelFxText(String text){
        labelFx.setText(text);
    }
}
