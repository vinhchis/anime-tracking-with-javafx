package Controllers;

import Models.*;
import Util.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;




public class AdministratorController implements Initializable {

    @FXML
    private Button ad_btnDashboard;

    @FXML
    private Button ad_btnListAccount;

    @FXML
    private Button ad_btnSchedule;

    @FXML
    private Button ad_btnSignout;

    @FXML
    private Label ad_lblName;


    // Dashboard
    @FXML
    private AnchorPane dashboardForm;

    @FXML
    private ComboBox<Integer> db_cbNation;

    @FXML
    private ComboBox<Integer> db_cbSeason;

    @FXML
    private ComboBox<Integer> db_cbStatus;


    @FXML
    private ComboBox<Integer> db_cbType;

    @FXML
    private DatePicker db_dpAried;

    @FXML
    private TableColumn<Database, Image> db_tbvcolPoster;

    @FXML
    private TableColumn<Database, Integer> db_tbvcolSeason;

    @FXML
    private TableColumn<Database, Integer> db_tbvcolStatus;

    @FXML
    private TableColumn<Database, Void> db_tbvcolTags;

    @FXML
    private TableView<Database> db_tbvDatabase;

    @FXML
    private TableColumn<Database, String> db_tbvcolTitle;

    @FXML
    private TableColumn<Database, Integer> db_tbvcolType;

    @FXML
    private TableColumn<Database, Void> db_tbvcolSchedule;

    @FXML
    private TextField db_txtEpisode;

    @FXML
    private TextArea db_txtIntroduction;

    @FXML
    private TextField db_txtNewepisode;

    @FXML
    private TextField db_txtPoster;

    @FXML
    private ComboBox<Integer> db_cbStudio;

    @FXML
    private Label db_lblTypeShowcase;

    @FXML
    private Label db_lblSeasonShowcase;

    @FXML
    private Label db_lblStatusShowcase;

    @FXML
    private Label db_lblNationShowcase;

    @FXML
    private TextField db_txtTitle;

    @FXML
    private Button db_btnAdd;

    @FXML
    private Button db_btnClear;

    @FXML
    private Button db_btnDelete;

    @FXML
    private Button db_btnUpdate;

    // End Dashboard

    // Schedule
    @FXML
    private TableView<Schedule> sc_tbvSchedule;

    @FXML
    private TableColumn<Schedule, Integer> sc_tbvcolDay;


    @FXML
    private TableColumn<Schedule, Image> sc_tbvcolPoster;

    @FXML
    private TableColumn<Schedule, Integer> sc_tbvcolStatus;

    @FXML
    private TableColumn<Schedule, Time> sc_tbvcolTime;

    @FXML
    private TableColumn<Schedule, String> sc_tbvcolTitle;

    @FXML
    private AnchorPane scheduleForm;


    // Entry Data

    @FXML
    private Button sc_btnClear;

    @FXML
    private Button sc_btnDelete;

    @FXML
    private Button sc_btnUpdate;

    @FXML
    private ComboBox<Integer> sc_cbDay;


    @FXML
    private ComboBox<Integer> sc_cbFilterStatus;

    @FXML
    private ComboBox<Integer> sc_cbFilterDay;

    @FXML
    private TextField sc_txtAnimeID;

    @FXML
    private TextField sc_txtTime;



    // End Entry Daata

    // End Schedule

    // List Account
    @FXML
    private Button la_btnSearch;

    @FXML
    private TableView<ManageAccount> la_tbvAccount;

    @FXML
    private TableColumn<ManageAccount, Integer> la_tbvcolAcountID;

    @FXML
    private TableColumn<ManageAccount, Integer> la_tbvcolAnimeID;

    @FXML
    private TableColumn<ManageAccount, Date> la_tbvcolCreated;

    @FXML
    private TableColumn<ManageAccount, Date> la_tbvcolLastUpdate;

    @FXML
    private TableColumn<ManageAccount, Integer> la_tbvcolLastWatched;

    @FXML
    private TableColumn<ManageAccount, Integer> la_tbvcolStatus;

    @FXML
    private TextField la_txtSearch;



    @FXML
    private AnchorPane listaccountForm;
    // End List Account



    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    public Alert alert;
    public Integer[] status = {1,2,3,4};
    public Integer[] type ={1,2,3};
    public Integer[] season = {1,2,3,4};
    public Integer[] nation = {1,2,3};
    public Integer[] day = {2,3,4,5,6,7,8};
    String query;
    int studioid;

    // Connect Database
    public AdministratorController(){
        cnn = DBConnect.makeConnection();
    }
    // End Connect Database

    // Enter data

    public ObservableList<Database> getDashboardEntry() {
        ObservableList<Database> listData = FXCollections.observableArrayList();
        Database dbItems;
        String query = "SELECT * FROM ANIME";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                String imagePath = rs.getString("poster");
                Image image = new Image(imagePath, 60, 60, false, true);
                dbItems = new Database(rs.getInt("anime_id"),image,rs.getString("title"),
                        rs.getInt("season"),rs.getInt("type"),rs.getInt("status"),
                        rs.getInt("nation"),rs.getDate("aried"),rs.getInt("episodes"),
                        rs.getInt("new_episode"),rs.getInt("studio_id"),rs.getString("introduction"));
                listData.add(dbItems);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    ObservableList<Database> dashboardTableView;
    public void dashboardShowcase(){
        dashboardTableView = getDashboardEntry();
        db_tbvcolPoster.setCellValueFactory(new PropertyValueFactory<>("poster"));
        db_tbvcolPoster.setCellFactory(column -> {
            return new TableCell<Database,Image>(){
                private final ImageView imageView = new ImageView();
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else{
                        imageView.setImage(item);
                        setGraphic(imageView);
                    }
                }
            };
        });
        db_tbvcolTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        db_tbvcolSeason.setCellValueFactory(new PropertyValueFactory<>("season"));
        db_tbvcolSeason.setCellFactory(column -> {
            return new TableCell<Database, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Spring");
                    }
                    else if(item == 2){
                        setText("Summer");
                    }
                    else if(item == 3){
                        setText("Autumn");
                    }
                    else if(item == 4){
                        setText("Winter");
                    }
                }
            };
        });
        db_tbvcolType.setCellValueFactory(new PropertyValueFactory<>("type"));
        db_tbvcolType.setCellFactory(column -> {
            return new TableCell<Database, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Series");
                    }
                    else if(item == 2){
                        setText("Movies");
                    }
                }
            };
        });
        db_tbvcolStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        db_tbvcolStatus.setCellFactory(column ->{
            return new TableCell<Database, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Airing");
                    }
                    else if(item == 2){
                        setText("Finished");
                    }
                    else if(item == 3){
                        setText("Cancelled");
                    }
                    else if(item == 4){
                        setText("Upcoming");
                    }
                }
            };
        });
        db_tbvcolTags.setCellFactory(column -> {
            return new TableCell<Database, Void>(){
                private final Button button = new Button();{
                    button.setOnAction(event -> {
                        Database selectedAnime = db_tbvDatabase.getItems().get(getIndex());
                        int offset = selectedAnime.getAnimeid();
                        data.id = offset;
                        String query = "select * from Anime where anime_id = ?" ;
                        Stage stage = new Stage();
                        try {
                            st = cnn.prepareStatement(query);
                            st.setInt(1, offset);
                            rs = st.executeQuery();
                            while (rs.next()) {
                                data.studioid = rs.getInt("studio_id");
                                URL fxmlLocation = getClass().getResource("/Views/Dialog/PopupAnimeAdmin.fxml");
                                Parent root = FXMLLoader.load(fxmlLocation);
                                stage.setTitle("Details");
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        button.setText("Details");
                        button.setCursor(Cursor.HAND);
                        setGraphic(button);
                    }
                }
            };
        });
        db_tbvcolSchedule.setCellFactory(column -> {
            return new TableCell<Database, Void>(){
                private final Button button = new Button();{
                    button.setOnAction(event -> {
                        Database selectedAnime = db_tbvDatabase.getItems().get(getIndex());
                        int offset = selectedAnime.getAnimeid();
                        data.id = offset;
                        String query = "SELECT * FROM Anime where anime_id= ?";
                        Stage stage = new Stage();
                        try {
                            st = cnn.prepareStatement(query);
                            st.setInt(1, offset);
                            rs = st.executeQuery();
                            while (rs.next()) {
                                URL fxmlLocation = getClass().getResource("/Views/Dialog/cardSchedule.fxml");
                                Parent root = FXMLLoader.load(fxmlLocation);
                                stage.setTitle("Schedule");
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty){
                        setText(null);
                        setGraphic(null);
                    }
                    else{
                        button.setText("Add");
                        button.setCursor(Cursor.HAND);
                        setGraphic(button);
                    }
                }
            };
        });

        db_tbvDatabase.setItems(dashboardTableView);
    }

    // Optional Data
    public void optStatus(){
        List<Integer> staList = new ArrayList<>();
        for(Integer data: status){
            staList.add(data);
        }
        ObservableList listStatus = FXCollections.observableArrayList(staList);
        db_cbStatus.setItems(listStatus);
        Map<Integer, String> itemStatus = new HashMap<>();
        itemStatus.put(1, "Airing");
        itemStatus.put(2, "Finished");
        itemStatus.put(3, "Cancelled");
        itemStatus.put(4, "Upcoming");
        db_cbStatus.setConverter(new StringConverter<Integer>() {
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
        db_cbStatus.setOnAction(event -> {
            int selectedIndex = db_cbStatus.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0 && selectedIndex < status.length){
                int selectedStatus = status[selectedIndex];
                switch (selectedStatus) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        db_txtNewepisode.setText("NULL");
                        break;
                    case 4:
                        break;
                }


            } else {
                System.out.println("Nothing to display");
            }
        });

    }
    public void optType(){
        List<Integer> typeList = new ArrayList<>();
        for(Integer data: type){
            typeList.add(data);
        }
        ObservableList listType = FXCollections.observableArrayList(typeList);
        db_cbType.setItems(listType);
        Map<Integer, String> itemType = new HashMap<>();
        itemType.put(1, "Series");
        itemType.put(2, "Movies");
        itemType.put(3, "OVA");

        db_cbType.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ?  itemType.get(object) : "";
            }

            @Override
            public Integer fromString(String string) {
                for(Map.Entry<Integer, String> entry: itemType.entrySet()){
                    if(entry.getValue().equals(string)){
                        return entry.getKey();
                    }
                }
                return null;
            }
        });

    }
    public void optSeason(){
        List<Integer> seasonList = new ArrayList<>();
        for(Integer data: season){
            seasonList.add(data);
        }
        ObservableList listSeason = FXCollections.observableArrayList(seasonList);
        db_cbSeason.setItems(listSeason);
        Map<Integer, String> itemSeason = new HashMap<>();
        itemSeason.put(1, "Spring");
        itemSeason.put(2, "Summer");
        itemSeason.put(3, "Autumn");
        itemSeason.put(4, "Winter");
        db_cbSeason.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ?  itemSeason.get(object) : "";
            }

            @Override
            public Integer fromString(String string) {
                for(Map.Entry<Integer, String> entry: itemSeason.entrySet()){
                    if(entry.getValue().equals(string)){
                        return entry.getKey();
                    }
                }
                return null;
            }
        });
    }
    public void optNation(){
        List<Integer> nationList = new ArrayList<>();
        for(Integer data: nation){
            nationList.add(data);
        }
        ObservableList listNation = FXCollections.observableArrayList(nationList);
        db_cbNation.setItems(listNation);
        Map<Integer, String> itemNation = new HashMap<>();
        itemNation.put(1, "Japan");
        itemNation.put(2, "China");
        itemNation.put(3, "Others");
        db_cbNation.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object != null ?  itemNation.get(object) : "";
            }

            @Override
            public Integer fromString(String string) {
                for(Map.Entry<Integer, String> entry: itemNation.entrySet()){
                    if(entry.getValue().equals(string)){
                        return entry.getKey();
                    }
                }
                return null;
            }
        });
    }
    public void optDay(){
        List<Integer> dayList = new ArrayList<>();
        for(Integer data: day){
            dayList.add(data);
        }
        ObservableList listDay = FXCollections.observableArrayList(dayList);
        sc_cbDay.setItems(listDay);
        Map<Integer, String> itemDay = new HashMap<>();
        itemDay.put(2, "Monday");
        itemDay.put(3, "Tuesday");
        itemDay.put(4, "Wednesday");
        itemDay.put(5, "Thursday");
        itemDay.put(6, "Friday");
        itemDay.put(7, "Saturday");
        itemDay.put(8, "Sunday");
        sc_cbDay.setConverter(new StringConverter<Integer>() {
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
    public void optFilterDay(){
        List<Integer> dayList = new ArrayList<>();
        for(Integer data: day){
            dayList.add(data);
        }
        ObservableList listDay = FXCollections.observableArrayList(dayList);
        sc_cbFilterDay.setItems(listDay);
        Map<Integer, String> itemDay = new HashMap<>();
        itemDay.put(2, "Monday");
        itemDay.put(3, "Tuesday");
        itemDay.put(4, "Wednesday");
        itemDay.put(5, "Thursday");
        itemDay.put(6, "Friday");
        itemDay.put(7, "Saturday");
        itemDay.put(8, "Sunday");
        sc_cbFilterDay.setConverter(new StringConverter<Integer>() {
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
        sc_cbFilterDay.setOnAction(event -> {
            int selectedIndex = sc_cbFilterDay.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0 && selectedIndex < day.length){
                int selectedDay = day[selectedIndex];
                String query = "Select S.*, A.* from Schedule S INNER JOIN Anime A ON A.anime_id = S.anime_id " +
                        "  where S.day='" + selectedDay + "'";
                try {
                    if(rs !=null)
                        rs.close();

                    st = cnn.prepareStatement(query);
                    rs = st.executeQuery();
                    sc_tbvSchedule.getItems().clear();
                    while (rs.next()){
                        scheduleShowcase(query);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
    }
    public void optFilterStatus(){
        List<Integer> staList = new ArrayList<>();
        for(Integer data: status){
            staList.add(data);
        }
        ObservableList listStatus = FXCollections.observableArrayList(staList);
        sc_cbFilterStatus.setItems(listStatus);
        Map<Integer, String> itemStatus = new HashMap<>();
        itemStatus.put(1, "Airing");
        itemStatus.put(2, "Finished");
        itemStatus.put(3, "Cancelled");
        itemStatus.put(4, "Upcoming");
        sc_cbFilterStatus.setConverter(new StringConverter<Integer>() {
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
        sc_cbFilterStatus.setOnAction(event -> {
            int selectedIndex = sc_cbFilterStatus.getSelectionModel().getSelectedIndex();
            if(selectedIndex >= 0 && selectedIndex < status.length){
                int selectedStatus = status[selectedIndex];
                String query = "Select S.*, A.* from Schedule S INNER JOIN Anime A ON A.anime_id = S.anime_id" +
                        " where A.status= '" + selectedStatus + "'";
                try {
                    if(rs !=null)
                        rs.close();

                    st = cnn.prepareStatement(query);
                    rs = st.executeQuery();
                    sc_tbvSchedule.getItems().clear();
                    while (rs.next()){
                        scheduleShowcase(query);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            } else {
                System.out.println("Nothing to display");
            }
        });
    }

    ObservableList<Integer> studios = FXCollections.observableArrayList();
    public void optStudio(){
        String query = "SELECT * FROM Studio ";
        Map<Integer, String> studioMap = new HashMap<>();
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            studios.clear();
            while (rs.next()) {
                studioid = rs.getInt("studio_id");
                String studioName = rs.getString("studio_name");
                studioMap.put(studioid,studioName);
                studios.add(studioid);
            }
            db_cbStudio.setItems(studios);
            db_cbStudio.setConverter(new StringConverter<Integer>() {
                @Override
                public String toString(Integer object) {
                    return object != null ?  studioMap.get(object) : "";
                }

                @Override
                public Integer fromString(String string) {
                    for(Map.Entry<Integer, String> entry: studioMap.entrySet()){
                        if(entry.getValue().equals(string)){
                            return entry.getKey();
                        }
                    }
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // End Optional Data



    /* Anime Data*/
    public void addbtn(){
        if(db_txtPoster.getText().isEmpty() || db_txtTitle.getText().isEmpty() ||
                db_cbSeason.getSelectionModel().getSelectedItem() == null || db_cbType.getSelectionModel().getSelectedItem() == null
                || db_cbStatus.getSelectionModel().getSelectedItem() == null || db_dpAried.getValue() == null
                || db_txtEpisode.getText().isEmpty() || db_txtNewepisode.getText().isEmpty() ||
                db_cbStudio.getSelectionModel().getSelectedItem() == null|| db_txtIntroduction.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else{
            if(db_txtIntroduction.getText().length() < 20){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText("");
                alert.setContentText("Introduction cannot under 20 characters");
                alert.showAndWait();
            }
            else if(Integer.valueOf(db_txtNewepisode.getText()) > Integer.valueOf(db_txtEpisode.getText())){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText("");
                alert.setContentText("Newepisode cannot above Episodes");
                alert.showAndWait();
            }
            else {
                String checkTitleQuery = "SELECT title FROM Anime WHERE title = '" + db_txtTitle.getText() + "'";
                String insertData = "INSERT INTO Anime (account_id, title, poster, status, aried, episodes, new_episode, studio, type, introduction, season, nation)" +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    st = cnn.prepareStatement(checkTitleQuery);
                    rs = st.executeQuery();
                    if (rs.next()) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText("");
                        alert.setContentText("This topic already exists in the database");
                        alert.showAndWait();
                    } else {

                        st = cnn.prepareStatement(insertData);
                        st.setInt(1, data.accountid);
                        st.setString(2, db_txtTitle.getText());
                        st.setString(3, db_txtPoster.getText());
                        st.setString(4, db_cbSeason.getSelectionModel().getSelectedItem().toString());
                        LocalDate selectedDate = db_dpAried.getValue();
                        String formattedDate = selectedDate.toString();
                        st.setString(5, formattedDate);
                        st.setString(6, db_txtEpisode.getText());
                        st.setString(7, db_txtNewepisode.getText());
                        st.setString(8, db_cbStudio.getSelectionModel().getSelectedItem().toString());
                        st.setString(9, db_cbType.getSelectionModel().getSelectedItem().toString());
                        st.setString(10, db_txtIntroduction.getText());
                        st.setString(11, db_cbSeason.getSelectionModel().getSelectedItem().toString());
                        Object selectedItemObject = db_cbNation.getSelectionModel().getSelectedItem();
                        if (selectedItemObject != null) {
                            String selectedItem = selectedItemObject.toString();
                            st.setString(12, selectedItem);
                        } else {
                            st.setString(12, "1");
                        }
                        st.executeUpdate();
                        // Alert
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText("");
                        alert.setContentText("Successfully Added!");
                        alert.showAndWait();

                        dashboardShowcase();
                        clearbtn();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void updatebtn(){
        if(db_txtPoster.getText().isEmpty() || db_txtTitle.getText().isEmpty() ||
                db_cbSeason.getSelectionModel().getSelectedItem() == null || db_cbType.getSelectionModel().getSelectedItem() == null
                || db_cbStatus.getSelectionModel().getSelectedItem() == null || db_dpAried.getValue() == null
                || db_txtEpisode.getText().isEmpty() || db_txtNewepisode.getText().isEmpty() ||
                db_cbStudio.getSelectionModel().getSelectedItem() == null || db_txtIntroduction.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            LocalDate selectedDate = db_dpAried.getValue();
            String formattedDate = selectedDate.toString();
            String updateData= "update Anime set poster= '" + db_txtPoster.getText() + "' ,title= '" + db_txtTitle.getText() + "' ,type= '"+
                    db_cbType.getSelectionModel().getSelectedItem().toString() + "' ,status= '"
                    +db_cbStatus.getSelectionModel().getSelectedItem().toString() + "' ,aried= '"
                    +  formattedDate + "' ,season ='" + db_cbSeason.getSelectionModel().getSelectedItem().toString() +
                    "' ,episodes= '" + db_txtEpisode.getText() + "' ,introduction= '" + db_txtIntroduction.getText() + "' ,Nation= '"
                    + db_cbNation.getSelectionModel().getSelectedItem().toString() + "' , new_episode= '" + db_txtNewepisode.getText() + "', studio_id = '" +
                    db_cbStudio.getSelectionModel().getSelectedItem() + "' where anime_id= '" + data.id + "'";
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to update ?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    st = cnn.prepareStatement(updateData);
                    st.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    dashboardShowcase();
                    clearbtn();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void deletebtn(){
        int animeid = db_tbvDatabase.getSelectionModel().getSelectedIndex();
        int offset = animeid + 1;
        if(animeid != -1){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                String deleteAnime = "DELETE FROM Anime WHERE anime_id = '" + offset + "'";
                String deleteNotifications = "DELETE FROM Notification WHERE ref_anime_id = '" + offset + "'";
                String deleteTrackingAnime = "DELETE FROM TrackingAnime WHERE anime_id = '" + offset + "'";
                String deleteSchedule = "DELETE FROM Schedule WHERE anime_id = '" + offset + "'";
                String deleteGenreWithAnime = "DELETE FROM GenreWithAnime WHERE anime_id = '" + offset + "'";
                try {
                    st = cnn.prepareStatement(deleteNotifications);
                    st.executeUpdate();

                    st = cnn.prepareStatement(deleteTrackingAnime);
                    st.executeUpdate();

                    st = cnn.prepareStatement(deleteSchedule);
                    st.executeUpdate();

                    st = cnn.prepareStatement(deleteGenreWithAnime);
                    st.executeUpdate();

                    st = cnn.prepareStatement(deleteAnime);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    dashboardShowcase();
                    clearbtn();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No row selected");
            alert.setContentText("Please select a row to delete.");
            alert.showAndWait();
        }
    }
    public void clearbtn(){
        db_txtPoster.clear();
        db_txtTitle.clear();
        db_cbSeason.getSelectionModel().clearSelection();
        db_cbType.getSelectionModel().clearSelection();
        db_cbNation.getSelectionModel().clearSelection();
        db_cbSeason.getSelectionModel().clearSelection();
        db_cbStatus.getSelectionModel().clearSelection();
        db_dpAried.setValue(null);
        db_txtEpisode.clear();
        db_txtNewepisode.clear();
        db_cbStudio.getSelectionModel().clearSelection();
        db_txtIntroduction.clear();
        db_lblSeasonShowcase.setText("");
        db_lblTypeShowcase.setText("");
        db_lblNationShowcase.setText("");
        db_lblStatusShowcase.setText("");
    }
    /* End Anime Data*/

    /* Selected */
    public void selecteditems(){
        String getData = "SELECT * FROM Anime where anime_id= ?";
        try{
            Database selectedAnime = db_tbvDatabase.getSelectionModel().getSelectedItem();
            if(selectedAnime == null){
                return;
            }
            int animeid = selectedAnime.getAnimeid();
            data.id = animeid;
            st = cnn.prepareStatement(getData);
            st.setInt(1, animeid);
            rs = st.executeQuery();
            while(rs.next()){
                db_txtPoster.setText(rs.getString("poster"));
                db_txtTitle.setText(rs.getString("title"));
                if(rs.getDate("aried") == null){
                    db_dpAried.setValue(null);
                }else{
                    db_dpAried.setValue(rs.getDate("aried").toLocalDate());
                }
                db_txtEpisode.setText(String.valueOf(rs.getInt("episodes")));
                db_txtNewepisode.setText(String.valueOf(rs.getInt("new_episode")));
                db_cbNation.setValue(rs.getInt("nation"));
                db_cbSeason.setValue(rs.getInt("season"));
                db_cbStatus.setValue(rs.getInt("status"));
                db_cbType.setValue(rs.getInt("type"));
                db_cbStudio.setValue(rs.getInt("studio_id"));
                db_txtIntroduction.setText(rs.getString("introduction"));
            }
            db_btnUpdate.setDisable(false);
            db_btnDelete.setDisable(false);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void selectedschedules(){
        String getData = "SELECT Anime.anime_id,Anime.poster,Anime.status, Anime.title, Schedule.day, Schedule.time FROM Anime \n" +
                "                INNER JOIN Schedule ON Anime.anime_id = Schedule.anime_id where schedule_id= ?";
        try{
            Schedule selectedSchedule = sc_tbvSchedule.getSelectionModel().getSelectedItem();
            if(selectedSchedule == null){
                return;
            }
            int scheduleid = selectedSchedule.getSchedule_id();
            data.scheduleid = scheduleid;
            st = cnn.prepareStatement(getData);
            st.setInt(1, scheduleid);
            rs = st.executeQuery();
            while(rs.next()){
                sc_txtAnimeID.setText(rs.getString("anime_id"));
                String timeString = rs.getString("time");
                LocalTime time = LocalTime.parse(timeString);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedTime = time.format(formatter);
                sc_txtTime.setText(formattedTime);
            }
            sc_btnUpdate.setDisable(false);
            sc_btnDelete.setDisable(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /* End Selected*/

    /* Schedule Data*/
    public void updateschedulebtn(){
        if(sc_txtAnimeID.getText().isEmpty() || sc_txtTime.getText().isEmpty() ||
                sc_cbDay.getSelectionModel().getSelectedItem() == null){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }else{
            try {

            String updateData= "update Schedule set anime_id= '" + sc_txtAnimeID.getText() + "' , day= '" +
                    sc_cbDay.getSelectionModel().getSelectedItem().toString() + "', time= '" + sc_txtTime.getText() + "'" +
                    " where schedule_id= '" + data.scheduleid + "'";
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to update ?");
                Optional<ButtonType> option = alert.showAndWait();
                if(option.get().equals(ButtonType.OK)){
                    st = cnn.prepareStatement(updateData);
                    st.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    scheduleShowcase(query);
                    clearschedulebtn();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");
                    alert.showAndWait();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void deleteschedulebtn(){
        int scheduleid = sc_tbvSchedule.getSelectionModel().getSelectedIndex();
        int offset = scheduleid + 1;
        if(scheduleid != -1){
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete ?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "delete from Schedule where schedule_id = '" + offset + "'";
                try {
                    st = cnn.prepareStatement(deleteData);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    scheduleShowcase(query);
                    clearschedulebtn();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No row selected");
            alert.setContentText("Please select a row to delete.");
            alert.showAndWait();
        }
    }
    public void clearschedulebtn(){
        sc_txtAnimeID.clear();
        sc_txtTime.clear();
        sc_cbDay.getSelectionModel().clearSelection();
        sc_cbFilterDay.getSelectionModel().clearSelection();
        sc_cbFilterStatus.getSelectionModel().clearSelection();
    }

    /* End Schedule Data*/

    // End Enter data


    // Schedule Data
    public ObservableList<Schedule> getScheduledata(String query) {
        ObservableList<Schedule> listData = FXCollections.observableArrayList();
        Schedule scheduleItems;
        if(query == null || query.trim().isEmpty()){
            query = "SELECT Anime.anime_id,Anime.poster,Anime.status, Anime.title,Schedule.schedule_id ,Schedule.day, Schedule.time FROM Anime " +
                    "INNER JOIN Schedule ON Anime.anime_id = Schedule.anime_id ORDER BY Anime.anime_id ASC";
        }

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


    public void scheduleShowcase(String query){
        scheduleTableView = getScheduledata(query);
        sc_tbvcolTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        sc_tbvcolPoster.setCellValueFactory(new PropertyValueFactory<>("poster"));
        sc_tbvcolPoster.setCellFactory(column -> {
            return new TableCell<Schedule,Image>(){
                private final ImageView imageView = new ImageView();
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else{
                        imageView.setImage(item);
                        setGraphic(imageView);
                    }
                }
            };
        });
        sc_tbvcolStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        sc_tbvcolStatus.setCellFactory(column ->{
            return new TableCell<Schedule, Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Airing");
                    }
                    else if(item == 2){
                        setText("Finished");
                    }
                    else if(item == 3){
                        setText("Cancelled");
                    }
                    else if(item == 4){
                        setText("Upcoming");
                    }
                }
            };
        });
        sc_tbvcolDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        sc_tbvcolDay.setCellFactory(column -> {
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
        sc_tbvcolTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        sc_tbvSchedule.setItems(scheduleTableView);
    }

    // End Schedule Data

    // Manage Account
    public ObservableList<ManageAccount> getManageAccountdata(){
        ObservableList<ManageAccount> listData = FXCollections.observableArrayList();
        ManageAccount manageAccount;
        String query = "SELECT TL.account_id, TA.anime_id, TA.status, TL.created_day, TA.last_watched_episode, TL.last_updated " +
                "FROM TrackingList TL " +
                "INNER JOIN TrackingAnime TA ON TL.tl_id = TA.tl_id";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            while(rs.next()){
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Timestamp timestamp = rs.getTimestamp("last_updated");
                String formattedDate = dateFormat.format(timestamp);
                manageAccount = new ManageAccount(rs.getInt("account_id"), rs.getInt("anime_id"),
                        rs.getInt("status"),rs.getInt("last_watched_episode"),rs.getDate("created_day"),
                        formattedDate);
                listData.add(manageAccount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    ObservableList<ManageAccount> manageAccountTableView;
    public void manageaccountShowcase(){
        manageAccountTableView = getManageAccountdata();
        la_tbvcolAcountID.setCellValueFactory(new PropertyValueFactory<>("accountid"));
        la_tbvcolAnimeID.setCellValueFactory(new PropertyValueFactory<>("animeid"));
        la_tbvcolStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        la_tbvcolStatus.setCellFactory(column -> {
            return new TableCell<ManageAccount, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText(null);
                    }
                    else if(item == 1){
                        setText("Plan to Watch");
                    }
                    else if(item == 2){
                        setText("Watching");
                    }
                    else if(item == 3){
                        setText("Completed");
                    }
                    else if(item == 4){
                        setText("On Hold");
                    }
                    else if(item == 5){
                        setText("Dropped");
                    }
                }
            };
        });
        la_tbvcolCreated.setCellValueFactory(new PropertyValueFactory<>("createdday"));
        la_tbvcolLastWatched.setCellValueFactory(new PropertyValueFactory<>("lastwatched"));
        la_tbvcolLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastupdated"));

        la_tbvAccount.setItems(manageAccountTableView);
    }

    public void searchAccountID(){
        String selectedID = "Select account_id from Account where account_id= '" + la_txtSearch.getText() + "'";
        try {
            st = cnn.prepareStatement(selectedID);
            rs = st.executeQuery();
            while (rs.next()){
                manageaccountShowcase();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // End Manage Account

    // Switch Form
   public void dashboardBtn(){
       dashboardForm.setVisible(true);
       scheduleForm.setVisible(false);
       listaccountForm.setVisible(false);
       clearbtn();
       clearschedulebtn();
       db_btnUpdate.setDisable(true);
       db_btnDelete.setDisable(true);
   }

   public void scheduleBtn(){
       dashboardForm.setVisible(false);
       scheduleForm.setVisible(true);
       listaccountForm.setVisible(false);
       clearbtn();
       clearschedulebtn();
       scheduleShowcase(query);
       sc_btnUpdate.setDisable(true);
       sc_btnDelete.setDisable(true);
   }

   public void listaccountBtn(){
       dashboardForm.setVisible(false);
       scheduleForm.setVisible(false);
       listaccountForm.setVisible(true);
       manageaccountShowcase();
       clearbtn();
       clearschedulebtn();
   }

    // End Switch Form

    // Display
    public void displayName() {
        String user = data.nickname;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        ad_lblName.setText(user);
    }

    // End Display

    public void signoutBtn() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText("You want to logout account ?");
        alert.setContentText("Select 'OK' to log out, or 'Cancel' to continue.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    ad_btnSignout.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/Views/Form/Form.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Form Login");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayName();

        // Dashboard Method
        dashboardShowcase();

        optStatus();
        optType();
        optSeason();
        optNation();
        optDay();
        optFilterDay();
        optFilterStatus();
        optStudio();
        // End Dashboard Method

        // Schedule Method
        scheduleShowcase(query);

        // End Schedule Method

        // Manage Account
        manageaccountShowcase();
        // End Account Method
    }
}
