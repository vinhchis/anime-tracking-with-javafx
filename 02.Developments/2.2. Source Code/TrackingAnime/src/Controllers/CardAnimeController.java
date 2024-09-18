package Controllers;

import Models.*;
import Models.data;
import Util.DBConnect;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CardAnimeController {

    private UserController userController;
    Information info;
    @FXML
    private ImageView anime_imageView;

    @FXML
    private Hyperlink anime_hyperlinkTitle;

    @FXML
    private void initialize() {
        anime_hyperlinkTitle.setOnAction(event -> {hyperlinkTitleClicked();});
    }
    Connection cnn;
    PreparedStatement st;
    ResultSet rs;
    int animeid;
    int tlid;
    public Alert alert;

    private ManageAnime manageAnime;
    public void setData(ManageAnime manageAnime) {
        this.manageAnime = manageAnime;
        anime_imageView.setImage(manageAnime.getPoster());
        anime_imageView.setFitWidth(200);
        anime_imageView.setFitHeight(200);
        anime_imageView.setPreserveRatio(false);
        anime_imageView.setSmooth(true);
        anime_hyperlinkTitle.setText(manageAnime.getTitle());
    }

    public CardAnimeController(){
        cnn = DBConnect.makeConnection();
    }

    public void hyperlinkTitleClicked(){
        String checkID = "Select * from Anime where title= '" + anime_hyperlinkTitle.getText() + "'";
        try{
            st = cnn.prepareStatement(checkID);
            rs = st.executeQuery();
            Stage stage = new Stage();
            while (rs.next()){
                data.studioid = rs.getInt("studio_id");
                animeid = rs.getInt("anime_id");
                SharedData.getInstance().setAnimeId(animeid);
                addAnimeToHistory(animeid);
            }
            data.id = animeid;
            String checkTLID = "Select tl.* From TrackingList tl INNER JOIN TrackingAnime ta ON ta.tl_id = tl.tl_id" +
                    " where ta.anime_id = '" + animeid + "'";
            st = cnn.prepareStatement(checkTLID);
            rs = st.executeQuery();
            while (rs.next()){
                tlid = rs.getInt("tl_id");
            }
            data.tlid = tlid;
            String query = "Select * from anime where anime_id=?";
            st = cnn.prepareStatement(query);
            st.setInt(1, animeid);
            rs = st.executeQuery();
            while(rs.next()){
                Parent root = FXMLLoader.load(getClass().getResource("/Views/Dialog/PopupAnimeUser.fxml"));
                stage.setTitle("Detail Anime");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addAnimeToHistory(int animeId) {
        String query = "SELECT * FROM Anime WHERE anime_id = ?";
        try {
            st = cnn.prepareStatement(query);
            st.setInt(1, animeId);
            rs = st.executeQuery();

            ObservableList<Information> lastUpdatedInfo = SharedData.getInstance().getLastUpdatedInfo();

            while (lastUpdatedInfo.size() >= 3) {
                lastUpdatedInfo.remove(lastUpdatedInfo.size() - 1);
            }
            while (rs.next()) {
                String imagePath = rs.getString("poster");
                Image image = new Image(imagePath, 110, 100, false, true);
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formatted = currentDateTime.format(formatter);

                Information info = new Information(
                        rs.getInt("anime_id"),
                        rs.getString("title"),
                        image,
                        rs.getInt("episodes"),
                        rs.getInt("new_episode"),
                        rs.getInt("status"),
                        rs.getFloat("score"),
                        formatted
                );
                lastUpdatedInfo.add(0, info);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
