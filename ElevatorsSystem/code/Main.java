package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu.fxml"));
        MenuController menuController = new MenuController( primaryStage );
        fxmlLoader.setController(menuController);
        Parent menu = fxmlLoader.load();

        Rectangle r = new Rectangle(50, 50 , 200, 100);

        primaryStage.setTitle("Elevator System Controller");
        // Group group = new Group(menu, r);
        Scene scene = new Scene(menu, 300, 300);

        scene.getStylesheets().add(0, "styles/menu.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
