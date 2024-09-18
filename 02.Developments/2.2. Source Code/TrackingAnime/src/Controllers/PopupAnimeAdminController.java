package Controllers;

import Models.Database;
import Models.Schedule;
import Models.data;
import Util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;



public class PopupAnimeAdminController implements Initializable {
    @FXML
    private Label pu_lblAired;

    @FXML
    private Hyperlink pu_hyperlinkPrimiered;

    @FXML
    private Hyperlink pu_hyperlinkSeason;

    @FXML
    private Hyperlink pu_hyperlinkStudios;

    @FXML
    private Hyperlink pu_hyperlinkType;

    @FXML
    private Hyperlink pu_hyperlinkTypeAll;

    @FXML
    private Hyperlink pu_hyperlinkStudio;

    @FXML
    private ImageView pu_imgViewPoster;

    @FXML
    private Label pu_lblEpisodes;

    @FXML
    private Label pu_lblIntroduction;

    @FXML
    private Label pu_lblRanked;

    @FXML
    private Label pu_lblScore;

    @FXML
    private Label pu_lblStatus;

    @FXML
    private TableView<Schedule> pu_tbvSchedule;

    @FXML
    private TableColumn<Schedule, Integer> pu_tbvcolDay;

    @FXML
    private TableColumn<Schedule, Time> pu_tbvcolTime;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    public Alert alert;
    Database database;
    int year;
    // Popup Details
    public void displaydetails(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                Image image = new Image(rs.getString("poster"),150,180,false,true);
                pu_imgViewPoster.setImage(image);
                if(rs.getInt("score") == 0){
                    pu_lblScore.setText("N/A");
                }
                if(rs.getInt("rankded") == -1){
                    pu_lblRanked.setText("N/A");
                }

                pu_lblEpisodes.setText(String.valueOf(rs.getInt("episodes")));

                if(rs.getDate("aried") == null){
                    pu_lblAired.setText("N/A");
                }else{
                    LocalDate selectedDate = rs.getDate("aried").toLocalDate();
                    String formattedDate = selectedDate.toString();
                    pu_lblAired.setText(formattedDate);
                }
                pu_hyperlinkStudio.setText(String.valueOf(rs.getInt("studio_id")));
                pu_hyperlinkStudios.setText(String.valueOf(rs.getInt("studio_id")));
                pu_lblIntroduction.setText(rs.getString("introduction"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void displaystatus(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1,data.id);
            rs = st.executeQuery();
            Date currentDate = new Date(System.currentTimeMillis());
            while (rs.next()){

                Date dbDate = rs.getDate("aried");
            if(dbDate != null){
                if(dbDate.compareTo(currentDate) < 0){
                    pu_lblStatus.setText("Currently Airing");
                }
                else if(dbDate.compareTo(currentDate) > 0){
                    pu_lblStatus.setText("Not yet aired");
                }else if(rs.getInt("episodes") == rs.getInt("new_episode")){
                    pu_lblStatus.setText("Finished Airing");
                }
            }
                else{
                    pu_lblStatus.setText("N/A");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void displayseason(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                if(rs.getDate("aried") == null){

                }else {
                    LocalDate selectedDate = rs.getDate("aried").toLocalDate();
                    year = selectedDate.getYear();
                }

                if(rs.getInt("season") == 1){
                    pu_hyperlinkSeason.setText("Spring " + year);
                    pu_hyperlinkPrimiered.setText("Spring " + year);
                }
                else if(rs.getInt("season") == 2){
                    pu_hyperlinkSeason.setText("Summer " + year);
                    pu_hyperlinkPrimiered.setText("Summer " + year);
                }
                else if(rs.getInt("season") == 3){
                    pu_hyperlinkSeason.setText("Autumn " + year);
                    pu_hyperlinkPrimiered.setText("Autumn " + year);
                }
                else if(rs.getInt("season") == 4){
                    pu_hyperlinkSeason.setText("Winter " + year);
                    pu_hyperlinkPrimiered.setText("Winter " + year);
                }
                else{
                    pu_hyperlinkSeason.setText(String.valueOf(year));
                    pu_hyperlinkPrimiered.setText(String.valueOf(year));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // End Popup Details
    public void displaytype(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                if(rs.getInt("type") == 1){
                    pu_hyperlinkType.setText("Series");
                    pu_hyperlinkTypeAll.setText("Series");
                }
                else if(rs.getInt("type") == 2){
                    pu_hyperlinkType.setText("Movies");
                    pu_hyperlinkTypeAll.setText("Movies");
                }
                else if(rs.getInt("type") == 3){
                    pu_hyperlinkType.setText("OVA");
                    pu_hyperlinkTypeAll.setText("OVA");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void displaystuio(){
        String query = "select * from Studio where studio_id = '" + data.studioid + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                pu_hyperlinkStudio.setText(rs.getString("studio_name"));
                pu_hyperlinkStudios.setText(rs.getString("studio_name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PopupAnimeAdminController(){
        cnn = DBConnect.makeConnection();
    }

    public ObservableList<Schedule> getScheduledata() {
        ObservableList<Schedule> listData = FXCollections.observableArrayList();
        Schedule scheduleItems;
        String query = "SELECT Anime.anime_id,Anime.poster,Anime.status, Anime.title,Schedule.schedule_id ,Schedule.day, Schedule.time FROM Anime " +
                    "INNER JOIN Schedule ON Anime.anime_id = Schedule.anime_id where Schedule.anime_id= '" + data.id + "' ORDER BY Schedule.day ASC";

        try{
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                String imagePath = rs.getString("poster");
                Image image = new Image(imagePath, 60, 60, false, true);
                scheduleItems = new Schedule(rs.getInt("schedule_id"),rs.getString("title"),image,rs.getInt("status"),
                        rs.getInt("day"),rs.getTime("time"));
                listData.add(scheduleItems);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    ObservableList<Schedule> scheduleTableView;
    public void scheduleShowcase(){
        scheduleTableView = getScheduledata();
        pu_tbvcolDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        pu_tbvcolDay.setCellFactory(column -> {
            return new TableCell<Schedule, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 2){
                        setText("Monday");
                    }
                    else if(item == 3){
                        setText("Tuesday");
                    }
                    else if(item == 4){
                        setText("Wednesday");
                    }
                    else if(item == 5){
                        setText("Thursday");
                    }
                    else if(item == 6){
                        setText("Friday");
                    }
                    else if(item == 7){
                        setText("Saturday");
                    }
                    else if(item == 8){
                        setText("Sunday");
                    }
                }
            };
        });
        pu_tbvcolTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        pu_tbvSchedule.setItems(scheduleTableView);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TO DO
        displaydetails();
        displayseason();
        displaytype();
        displaystatus();
        displaystuio();
        scheduleShowcase();
    }
}
