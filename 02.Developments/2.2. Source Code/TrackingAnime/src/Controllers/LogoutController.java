package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class LogoutController {
    public static void logout(Stage currentStage) {
        try {
            // Đóng stage hiện tại
            currentStage.close();
            URL url = FormController.class.getResource("/Views/Form/Form.fxml");
                Parent root = FXMLLoader.load(url);
                Stage loginStage = new Stage();
                loginStage.setTitle("Form Login");
                loginStage.setScene(new Scene(root));
                loginStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
