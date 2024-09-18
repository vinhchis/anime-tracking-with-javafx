package Controllers;


import Models.data;
import Util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class CardListTrackingAnimeController implements Initializable {

    @FXML
    private AnchorPane cta_apTrackingAnime;


    @FXML
    private Button cta_btnAdd;

    @FXML
    private Button cta_btnCancel;

    @FXML
    private ComboBox<String> cta_cbTrackingList;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    public Alert alert;
    public PopupAnimeUserController popupanime;
    public CardListTrackingAnimeController() {
        cnn = DBConnect.makeConnection();
        popupanime = new PopupAnimeUserController();
    }
    ObservableList<String> trackingListNames = FXCollections.observableArrayList();
    public void optTrackingList(){
        String query = "SELECT tl_name FROM TrackingList where account_id= '" + data.accountid + "'";
        String first = null;
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            trackingListNames.clear();
            boolean isFirst = true;
            while (rs.next()) {
                String tlName = rs.getString("tl_name");
                trackingListNames.add(tlName);
                if(isFirst){
                    first = tlName;
                    isFirst = false;
                }
            }
            cta_cbTrackingList.setItems(trackingListNames);
            if(first != null){
                cta_cbTrackingList.setValue(first);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addtrackingbtn() {
        try {
        String insertdata = "INSERT INTO TrackingAnime (tl_id, anime_id, status, last_watched_episode)\n" +
                "SELECT tl.tl_id, ?,?,?\n" +
                "FROM TrackingList tl\n" +
                "WHERE tl.account_id = ? AND tl.tl_name = ?";
            st = cnn.prepareStatement(insertdata);
            st.setInt(1, data.id);
            st.setInt(2,1);
            st.setInt(3,0);
            st.setInt(4, data.accountid);
            st.setString(5, cta_cbTrackingList.getSelectionModel().getSelectedItem());
            st.executeUpdate();


            String update = "update TrackingList set number_of_anime= ? where tl_id= ? ";
            st = cnn.prepareStatement(update);
            st.setInt(1,data.numberofanime + 1);
            st.setInt(2,data.tlid);
            st.executeUpdate();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText("");
            alert.setContentText("Successfully Added!");
            alert.showAndWait();

            popupanime.lastupdated();
            Stage stage = (Stage) cta_apTrackingAnime.getScene().getWindow();
            stage.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void canceltrackingbtn() {
        Stage stage = (Stage) cta_apTrackingAnime.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        optTrackingList();
    }
}