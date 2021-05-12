package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class ElevatorsDisplayController {
    private Stage stage;
    private int elevators_num, floors_num, people_num;
    private int request_num;
    private Thread elevators_thread;

    @FXML
    private TextField fxRequestsNum;

    @FXML
    private Button fxGenerateButton;

    public ElevatorsDisplayController(int elevators_num, int floors_num, int people_num) {
        this.elevators_num = elevators_num;
        this.floors_num = floors_num;
        this.people_num = people_num;
        request_num = -1;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void click(ActionEvent actionEvent) {
        String request = fxRequestsNum.getText();
        try {
            request_num = Integer.parseInt(request);
        }
        catch (Exception e) {
            request_num = -1;
        }
        if (request_num > 1 && (elevators_thread == null || !elevators_thread.isAlive())) {
            System.out.println("START");
            fxRequestsNum.setStyle("-fx-faint-focus-color: blue");
            fxGenerateButton.setStyle("-fx-faint-focus-color: blue");
            run_elevators_system();
        }
        else {
            fxRequestsNum.setStyle("-fx-faint-focus-color: red");
            fxGenerateButton.setStyle("-fx-faint-focus-color: red");
        }
    }

    private void run_elevators_system() {
        ElevatorsSystem el_system = new ElevatorsSystem(elevators_num, floors_num, people_num, request_num, stage);
        elevators_thread = new Thread(el_system);
        elevators_thread.start();
    }
}
