package Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FXMLLoaderHelper {
    public static Parent loadFXML(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FXMLLoaderHelper.class.getResource(fxmlPath));
        return fxmlLoader.load();
    }
}
