package Controllers;

import Models.Database;
import Models.Schedule;
import Models.data;
import Util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

public class PopupAnimeUserController implements Initializable {

    @FXML
    private Button pu_btnAddMyList;

    @FXML
    private ComboBox<Integer> pu_cbSelectScore;

    @FXML
    private ComboBox<Integer> pu_cbStatus;

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
    private Button pu_btnAddFavorites;

    @FXML
    private Button pu_btnRemoveFavorites;

    @FXML
    private Label pu_lblIntroduction;

    @FXML
    private Label pu_lblRanked;

    @FXML
    private Label pu_lblScore;

    @FXML
    private Label pu_lblStatus;

    @FXML
    private ComboBox<String> pu_cbTrackingList;

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
    int animeid = -1;
    int tlid,numberofanime;
    public Integer[] listscore = {10,9,8,7,6,5,4,3,2,1};
    public Integer[] liststatus = {1,2,3,4,5};


    // Popup Details

    public void displaydetails(){
        String query = "select * from Anime where anime_id= ?";
        try{
            st = cnn.prepareStatement(query);
            st.setInt(1, data.id);
            rs = st.executeQuery();
            while (rs.next()){
                data.poster =rs.getString("poster");
                Image image = new Image(data.poster,150,180,false,true);
                pu_imgViewPoster.setImage(image);

                if(rs.getInt("score") == 0){
                    pu_lblScore.setText("N/A");
                }else{
                    pu_lblScore.setText(String.valueOf(rs.getFloat("score")));
                }
                String rank = "WITH RankedAnime AS (\n" +
                        "    SELECT anime_id, RANK() OVER (ORDER BY score DESC) AS rank\n" +
                        "    FROM Anime\n" +
                        ")\n" +
                        "UPDATE A\n" +
                        "SET rankded = R.rank\n" +
                        "FROM Anime A\n" +
                        "JOIN RankedAnime R\n" +
                        "ON A.anime_id = R.anime_id;";
                st = cnn.prepareStatement(rank);
                st.executeUpdate();

                String selectrank = "Select * from Anime where anime_id = '" + data.id + "'";
                st = cnn.prepareStatement(selectrank);
                rs = st.executeQuery();
                if (rs.next()){
                    if(rs.getInt("rankded") == -1){
                        pu_lblRanked.setText("N/A");
                    }
                    else{
                        pu_lblRanked.setText(String.valueOf(rs.getInt("rankded")));
                    }
                }
                else{
                    pu_lblRanked.setText("N/A");
                }


                pu_lblEpisodes.setText(String.valueOf(rs.getInt("episodes")));
                pu_hyperlinkStudio.setText(String.valueOf(rs.getInt("studio_id")));
                pu_hyperlinkStudios.setText(String.valueOf(rs.getInt("studio_id")));
                pu_lblIntroduction.setText(rs.getString("introduction"));
                if(rs.getDate("aried") == null){
                    pu_lblAired.setText("N/A");
                }
                else{
                    LocalDate selectedDate = rs.getDate("aried").toLocalDate();
                    String formattedDate = selectedDate.toString();
                    pu_lblAired.setText(formattedDate);
                }


                String findanimeid = "Select * from Anime where title= ?";
                st = cnn.prepareStatement(findanimeid);
                st.setString(1, rs.getString("title"));
                rs = st.executeQuery();
                while (rs.next()) {
                    animeid = rs.getInt("anime_id");
                    if (animeid != -1) {
                        String selected = "SELECT TL.tl_id, TL.account_id, TA.anime_id, TA.status\n" +
                                "FROM TrackingList TL\n" +
                                "INNER JOIN TrackingAnime TA ON TL.tl_id = TA.tl_id where TL.account_id= ? AND TA.anime_id= ?";
                        st = cnn.prepareStatement(selected);
                        st.setInt(1, data.accountid);
                        st.setInt(2, animeid);
                        rs = st.executeQuery();
                        if (rs.next()) {
                            pu_btnAddMyList.setVisible(false);
                            pu_cbStatus.setVisible(true);
                            pu_cbTrackingList.setVisible(true);
                            pu_btnAddFavorites.setVisible(true);
                        } else {
                            pu_btnAddMyList.setVisible(true);
                            pu_cbStatus.setVisible(false);
                            pu_cbTrackingList.setVisible(false);
                            pu_btnAddFavorites.setVisible(false);
                        }
                    }

                    String statusdisplay = "SELECT status FROM TrackingAnime where anime_id= ?";
                        st = cnn.prepareStatement(statusdisplay);
                        st.setInt(1, data.id);
                        rs = st.executeQuery();
                        while (rs.next()) {
                            pu_cbStatus.setValue(rs.getInt("status"));
                        }

                    String scoredisplay = "SELECT score FROM Anime where anime_id= ?";
                    st = cnn.prepareStatement(scoredisplay);
                    st.setInt(1, data.id);
                    rs = st.executeQuery();
                    while (rs.next()) {
                        pu_cbSelectScore.setValue(rs.getInt("score"));
                    }
                }

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
                if(rs.getDate("aried") != null){
                    Date dbDate = rs.getDate("aried");
                    if(dbDate.compareTo(currentDate) < 0){
                        pu_lblStatus.setText("Currently Airing");
                    }
                    else if(dbDate.compareTo(currentDate) > 0){
                        pu_lblStatus.setText("Not yet aired");
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
                if(rs.getDate("aried") != null){
                    LocalDate selectedDate = rs.getDate("aried").toLocalDate();
                    int year = selectedDate.getYear();
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
                }else{
                    pu_hyperlinkSeason.setText("N/A");
                    pu_hyperlinkPrimiered.setText("N/A");
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

    // Optional
    public void optscore(){
        List<Integer> listScore = new ArrayList<>();
        for(Integer data: listscore){
            listScore.add(data);
        }
        ObservableList score = FXCollections.observableArrayList(listScore);
        pu_cbSelectScore.setItems(score);

        Map<Integer, String> itemScore = new HashMap<>();
        itemScore.put(10, "(10) Masterpiece");
        itemScore.put(9, "(9) Great");
        itemScore.put(8, "(8) Very Good");
        itemScore.put(7, "(7) Good");
        itemScore.put(6, "(6) Fine");
        itemScore.put(5, "(5) Average");
        itemScore.put(4, "(4) Bad");
        itemScore.put(3, "(3) Very Bad");
        itemScore.put(2, "(2) Horrible");
        itemScore.put(1, "(1) Appalling");

        pu_cbSelectScore.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ?  itemScore.get(object) : "";
            }

            @Override
            public Integer fromString(String string) {
                for(Map.Entry<Integer, String> entry: itemScore.entrySet()){
                    if(entry.getValue().equals(string)){
                        return entry.getKey();
                    }
                }
                return null;
            }
        });
        pu_cbSelectScore.setOnAction(event -> {
            try {
                int selectedIndex = pu_cbSelectScore.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0 && selectedIndex < listscore.length){
                    int selectedScore = listscore[selectedIndex];
                    String updateScore = "UPDATE Anime SET score = '" + selectedScore +
                            "' WHERE anime_id= '" + data.id + "'";
                    switch (selectedScore) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                            st = cnn.prepareStatement(updateScore);
                            st.executeUpdate();
                            break;
                    }


                } else {
                    System.out.println("Nothing to display");
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public void optstatus(){
        List<Integer> listStatus = new ArrayList<>();
        for(Integer data: liststatus){
            listStatus.add(data);
        }
        ObservableList status = FXCollections.observableArrayList(listStatus);
        pu_cbStatus.setItems(status);
        Map<Integer, String> itemStatus = new HashMap<>();
        itemStatus.put(1, "Watching");
        itemStatus.put(2, "Completed");
        itemStatus.put(3, "On-Hold");
        itemStatus.put(4, "Dropped");
        itemStatus.put(5, "Plan to Watch");
        pu_cbStatus.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ?  itemStatus.get(object) : "";
            }

            @Override
            public Integer fromString(String string) {
                for(Map.Entry<Integer, String> entry: itemStatus.entrySet()){
                    if(entry.getValue().equals(string)){
                        return entry.getKey();
                    }
                }
                return null;
            }
        });
        pu_cbStatus.setOnAction(event -> {

            try {
                    int selectedIndex = pu_cbStatus.getSelectionModel().getSelectedIndex();
                    if(selectedIndex >= 0 && selectedIndex < liststatus.length){
                        int selectedStatus = liststatus[selectedIndex];
                        String updateStatus = "UPDATE TA set TA.status= '" + selectedStatus +
                                "' From TrackingAnime TA INNER JOIN TrackingList TL ON TL.tl_id = TA.tl_id " +
                                "WHERE TA.anime_id= '" + data.id + "'";
                        switch (selectedStatus) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                                st = cnn.prepareStatement(updateStatus);
                                st.executeUpdate();
                                break;
                        }


                    } else {
                        System.out.println("Nothing to display");
                    }


            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }
    ObservableList<String> trackingListNames = FXCollections.observableArrayList();
    public void optTrackingList(){
        String query = "SELECT * FROM TrackingList ";

        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            trackingListNames.clear();
            String first = null;
            boolean isFirst = true;
            while (rs.next()) {
                String tlName = rs.getString("tl_name");
                trackingListNames.add(tlName);
            }
            pu_cbTrackingList.setItems(trackingListNames);
            String checkID = "SELECT * FROM TrackingList " +" where  tl_id= '" + data.tlid + "'";
            st = cnn.prepareStatement(checkID);
            rs = st.executeQuery();
            while (rs.next()) {
                String tlName = rs.getString("tl_name");
                if(isFirst){
                    first = tlName;
                    isFirst = false;
                }

            }
            if(first != null){
                pu_cbTrackingList.setValue(first);
            }

            pu_cbTrackingList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    updateTrackingListName(oldValue,newValue);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateTrackingListName(String oldTlName,String newTlName) {
        String removeAnimeQuery = "UPDATE TrackingList SET number_of_anime = number_of_anime - 1 WHERE tl_name = ?";
        String addAnimeQuery = "UPDATE TrackingList SET number_of_anime = number_of_anime + 1 WHERE tl_name = ?";
        String updateLastUpdatedQuery = "UPDATE TrackingList SET last_updated = GETDATE() WHERE tl_name = ?";
        String insertAnimeQuery = "INSERT INTO TrackingAnime (tl_id, anime_id, status, last_watched_episode)\n" +
                "SELECT tl.tl_id, ?, ?, ?\n" +
                "FROM TrackingList tl\n" +
                "WHERE tl.tl_name = ?";
        String deleteAnimeQuery = "DELETE FROM TrackingAnime WHERE tl_id = ? AND anime_id = ?";
        try {
            st = cnn.prepareStatement(insertAnimeQuery);
            st.setInt(1, data.id);
            st.setInt(2,1);
            st.setInt(3,0);
            st.setString(4, pu_cbTrackingList.getSelectionModel().getSelectedItem());
            st.executeUpdate();

            st = cnn.prepareStatement(removeAnimeQuery);
            st.setString(1, oldTlName);
            st.executeUpdate();

            st = cnn.prepareStatement(addAnimeQuery);
            st.setString(1, newTlName);
            st.executeUpdate();

            st = cnn.prepareStatement(updateLastUpdatedQuery);
            st.setString(1, newTlName);
            st.executeUpdate();

            st = cnn.prepareStatement(deleteAnimeQuery);
            st.setInt(1, data.tlid);
            st.setInt(2, data.id);
            st.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    // End Optional

    // Event
    public void addmylistBtn(){
        String selected = "Select * from TrackingList";
        try {
            st = cnn.prepareStatement(selected);
            rs = st.executeQuery();
            Stage stage = new Stage();
            while (rs.next()){
                tlid = rs.getInt("tl_id");
                numberofanime = rs.getInt("number_of_anime");
                data.tlid = tlid;
                data.numberofanime = numberofanime;
                URL fxmlLocation = getClass().getResource("/Views/Dialog/cardListTrackingAnime.fxml");
                Parent root = FXMLLoader.load(fxmlLocation);
                stage.setTitle("Schedule");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
                displaydetails();
                lastupdated();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void addtoFavoritesBtn(){
        String update = "Update TrackingAnime set isFavorite= '" + 1 + "' where anime_id= '" + data.id + "'";
        try {
            st = cnn.prepareStatement(update);
            st.executeUpdate();
            displayFavoriteBtn();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removetoFavoritesBtn(){
        String update = "Update TrackingAnime set isFavorite= '" + 0 + "' where anime_id= '" + data.id + "'";
        try {
            st = cnn.prepareStatement(update);
            st.executeUpdate();
            displayFavoriteBtn();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // End Event

    public void lastupdated(){
        String query = "update TrackingList set last_updated= ? from TrackingList TL INNER JOIN TrackingAnime TA " +
                "ON TL.tl_id = TA.tl_id  where TA.anime_id= ?";
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);
        try {
            st = cnn.prepareStatement(query);
            st.setTimestamp(1, currentTimestamp);
            st.setInt(2, data.id);
            st.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void displayFavoriteBtn(){
        String query = "Select * from TrackingAnime where anime_id= '" + data.id + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                if(rs.getInt("isFavorite") == 1){
                    pu_btnAddFavorites.setVisible(false);
                    pu_btnRemoveFavorites.setVisible(true);
                }else{
                    pu_btnAddFavorites.setVisible(true);
                    pu_btnRemoveFavorites.setVisible(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public PopupAnimeUserController(){
        cnn = DBConnect.makeConnection();
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
        // Optional
        optscore();
        optstatus();
        optTrackingList();
        // End Optional
        displayFavoriteBtn();
    }

}
