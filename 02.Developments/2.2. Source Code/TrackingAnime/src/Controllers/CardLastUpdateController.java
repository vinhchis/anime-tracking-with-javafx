package Controllers;

import Models.Information;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CardLastUpdateController implements Initializable {

    @FXML
    private Label cardH_Date;

    @FXML
    private Hyperlink cardH_Name;

    @FXML
    private ProgressBar cardH_ProgressBar;

    @FXML
    private Label cardH_Score;

    @FXML
    private Label cardH_Watching;

    @FXML
    private ImageView cardH_imageView;

    @FXML
    private Label cardH_Status;

    private Information info;
    private Image image;

    // Method
    public void setData(Information info){
        this.info = info;
        double progress = (double) info.getEpisodes() / info.getNewepisode();
        cardH_imageView.setImage(info.getPoster());
        cardH_imageView.setFitWidth(110);
        cardH_imageView.setFitHeight(100);
        cardH_imageView.setPreserveRatio(false);
        cardH_imageView.setSmooth(true);

        cardH_Name.setText(info.getTitle());
        cardH_Watching.setText(info.getEpisodes() + "/" + info.getNewepisode());
        cardH_Score.setText(String.valueOf(info.getScore()));
        cardH_Date.setText(info.getDate());
        if(info.getEpisodes() == 0){
            cardH_ProgressBar.setProgress(0);
        }else{
            cardH_ProgressBar.setProgress(progress);
        }

    }
    // End Method


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
    }
}
