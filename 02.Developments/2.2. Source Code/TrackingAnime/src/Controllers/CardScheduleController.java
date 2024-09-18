package Controllers;

import Models.data;
import Util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class CardScheduleController implements Initializable {

    @FXML
    private AnchorPane cs_apSchedule;

    @FXML
    private Button cs_btnAdd;

    @FXML
    private Button cs_btnCancel;

    @FXML
    private ComboBox<Integer> cs_cbDay;

    @FXML
    private ImageView cs_imgviewPoster;

    @FXML
    private TextField cs_txtTime;
    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    public Alert alert;
    public Integer[] day = {2,3,4,5,6,7,8};

    public CardScheduleController(){
        cnn = DBConnect.makeConnection();
    }

    public void displayschedule(){
        String checkID = "SELECT * FROM Anime where Anime.anime_id = ?";
        try {
            st = cnn.prepareStatement(checkID);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                Image image = new Image(rs.getString("poster"),150,150,false,true);
                cs_imgviewPoster.setImage(image);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addschedulebtn(){
        if(cs_txtTime.getText().isEmpty() || cs_cbDay.getSelectionModel().getSelectedItem() == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else{
            String selected = "SELECT * FROM Schedule WHERE day = '" + cs_cbDay.getSelectionModel().getSelectedItem() + "'";
            try {
                st = cnn.prepareStatement(selected);
                rs = st.executeQuery();
                if(rs.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("");
                    alert.setContentText("This day of anime already exists in the database");
                    alert.showAndWait();
                }else {

                    String insertdate = "INSERT INTO Schedule(anime_id,day,time) VALUES(?,?,?)";

                    st = cnn.prepareStatement(insertdate);
                    st.setInt(1, data.id);
                    st.setString(2, cs_cbDay.getSelectionModel().getSelectedItem().toString());
                    st.setString(3, cs_txtTime.getText());
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText("");
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    Stage stage = (Stage) cs_apSchedule.getScene().getWindow();
                    stage.close();
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        }
    }

    public void cancelschedulebtn(){
       Stage stage = (Stage) cs_apSchedule.getScene().getWindow();
       stage.close();
    }

    public void optDay(){
        List<Integer> dayList = new ArrayList<>();
        for(Integer data: day){
            dayList.add(data);
        }
        ObservableList listDay = FXCollections.observableArrayList(dayList);
        cs_cbDay.setItems(listDay);
        Map<Integer, String> itemDay = new HashMap<>();
        itemDay.put(2, "Monday");
        itemDay.put(3, "Tuesday");
        itemDay.put(4, "Wednesday");
        itemDay.put(5, "Thursday");
        itemDay.put(6, "Friday");
        itemDay.put(7, "Saturday");
        itemDay.put(8, "Sunday");
        cs_cbDay.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ?  itemDay.get(object) : "";
            }

            @Override
            public Integer fromString(String string) {
                for(Map.Entry<Integer, String> entry: itemDay.entrySet()){
                    if(entry.getValue().equals(string)){
                        return entry.getKey();
                    }
                }
                return null;
            }
        });

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayschedule();
        optDay();
    }
}