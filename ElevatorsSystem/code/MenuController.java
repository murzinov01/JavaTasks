package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {
    private Stage stage;
    private ElevatorsDisplayController displayController;
    @FXML
    private Button fxConfigButton;
    @FXML
    private TextField fxElevatorsText, fxFloorsText, fxPeopleText;
    private int elevators_num, floors_num, people_num;

    public MenuController(Stage stage) {
        this.stage = stage;
    }

    private void changeScene(ActionEvent event, String file_name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(file_name));
        displayController = new ElevatorsDisplayController(elevators_num, floors_num, people_num);
        fxmlLoader.setController(displayController);
        Parent page = fxmlLoader.load();

        Scene page_scene = new Scene(page);
        System.out.println(page_scene);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(page_scene);
        stage.setX(480);
        stage.setY(200);
        displayController.setStage(stage);
        stage.show();
    }

    @FXML
    public void click(ActionEvent actionEvent) {
        String[] string_params = new String[] {
                fxElevatorsText.getText(),
                fxFloorsText.getText(),
                fxPeopleText.getText()};

        int[] params = convert_params(string_params, new TextField[]{fxElevatorsText, fxFloorsText, fxPeopleText});

        // incorrect input
        if (params == null) {
            fxConfigButton.setStyle("-fx-faint-focus-color: red");
            return;
        }

        fxConfigButton.setStyle("-fx-faint-focus-color: blue");

        elevators_num = params[0];
        floors_num = params[1];
        people_num = params[2];

        try {
            changeScene(actionEvent, "ElevatorsDisplay.fxml");
        }
        catch (IOException e) {
            System.out.println("Error occurred while reading the file");
        }
    }

    private int check_number_in_string(String value, int min_value, int max_value) {
        int number = 0;
        try {
            number = Integer.parseInt(value);
            if (number < min_value || number > max_value) {
                number = -1;
            }
        }
        catch (Exception e) {
            number = -1;
        }
        return number;
    }

    private int[] convert_params(String[] string_params, TextField[] textFields) {
        int params_num = string_params.length;
        boolean isCorrect = true;
        int[] params = new int[params_num];
        int[] min_params = new int[]{1, 2, 1}; // min_elevators=1, min_floors=2, min_people=1
        int[] max_params = new int[]{5, 25, 10}; // max_elevators=5, max_floors=25, max_people=10

        for (int i = 0; i < params_num; i++) {
            params[i] = check_number_in_string(string_params[i], min_params[i], max_params[i]);
            if (params[i] == -1) {
                textFields[i].setStyle("-fx-faint-focus-color: red");
                isCorrect = false;
            } else {
                textFields[i].setStyle("-fx-faint-focus-color: blue");
            }
        }

        if (isCorrect)
            return params;
        else
            return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setViewLabelFxText("transfer of control in View \"labelFx\" variable");

        // Добавляем слушателя, по клику мышки на кнопку, выводит текст на консоль
        fxConfigButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> System.out.println("Configure clicked"));
    }
}
