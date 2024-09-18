import Controllers.CardListTrackingAnimeController;
import Controllers.PopupAnimeUserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Pane root = FXMLLoader.load(getClass().getResource("Views/Form/Form.fxml"));
            primaryStage.setTitle("Form");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}