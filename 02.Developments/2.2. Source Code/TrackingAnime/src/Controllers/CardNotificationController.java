package Controllers;

import Models.Notification;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CardNotificationController implements Initializable {

    @FXML
    private ImageView nf_imvPoster;

    @FXML
    private Label nf_lblDate;

    @FXML
    private Label nf_lblStatus;

    @FXML
    private Label nf_lblTime;

    @FXML
    private Label nf_lblTitle;

    private Notification notice;
    public void setData(Notification notice){
        this.notice = notice;

        nf_lblTitle.setText(notice.getTitle());
        nf_imvPoster.setImage(notice.getPoster());
        nf_imvPoster.setFitWidth(150);
        nf_imvPoster.setFitHeight(100);
        nf_imvPoster.setPreserveRatio(false);
        nf_imvPoster.setSmooth(true);
        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(1, "Plan to Watch");
        statusMap.put(2, "Watching");
        statusMap.put(3, "Completed");
        statusMap.put(3, "On Hold");
        statusMap.put(3, "Dropped");
        nf_lblStatus.setText(notice.getStatusString(statusMap));
        nf_lblDate.setText(notice.getDate());
        nf_lblTime.setText(notice.getTimes());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}

