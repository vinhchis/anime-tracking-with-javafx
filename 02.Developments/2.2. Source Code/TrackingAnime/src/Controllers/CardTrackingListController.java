package Controllers;

import Util.*;
import Models.data;
import Util.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class CardTrackingListController {

    @FXML
    private AnchorPane ctl_apTrackingList;

    @FXML
    private Button ctl_btnAdd;

    @FXML
    private Button ctl_btnCancel;

    @FXML
    private TextField ctl_txtTrackingName;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    public Alert alert;
    PopupAnimeUserController popupAnimeUserController;
    public CardTrackingListController() {
        cnn = DBConnect.makeConnection();
    }

    public void addtrackingbtn() {
        if (ctl_txtTrackingName.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            String checkName = "SELECT * FROM TrackingList where tl_name='" + ctl_txtTrackingName.getText() + "'";
            try {
                st = cnn.prepareStatement(checkName);
                rs = st.executeQuery();
                if (rs.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("");
                    alert.setContentText("This name already exists in the database");
                    alert.showAndWait();
                } else {
                    String insertdata = "INSERT INTO TrackingList(account_id,tl_name,created_day,last_updated) VALUES(?,?,?,?)";

                    st = cnn.prepareStatement(insertdata);
                    st.setInt(1, data.accountid);
                    st.setString(2, ctl_txtTrackingName.getText());
                    LocalDate localDate = LocalDate.now();
                    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                    st.setDate(3, sqlDate);
                    LocalDateTime localDateTime = LocalDateTime.now();
                    java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(localDateTime);
                    st.setTimestamp(4, sqlTimestamp);

                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText("");
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    Stage stage = (Stage) ctl_apTrackingList.getScene().getWindow();
                    stage.close();
                }
                } catch(Exception e){
                    e.printStackTrace();
                }

        }

    }
    public void canceltrackingbtn() {
        Stage stage = (Stage) ctl_apTrackingList.getScene().getWindow();
        stage.close();
    }
}
