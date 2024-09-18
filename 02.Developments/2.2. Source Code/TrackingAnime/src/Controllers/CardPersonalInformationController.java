package Controllers;

import Models.data;
import Util.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class CardPersonalInformationController implements Initializable {

    @FXML
    private Button ca_btnProceed;

    @FXML
    private TextField cp_txtEmail;

    @FXML
    private TextField cp_txtNickname;

    @FXML
    private AnchorPane cp_apPersonalInformation;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;

    private String originalNickName;
    private Alert alert;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public CardPersonalInformationController() {
        cnn = DBConnect.makeConnection();
    }

    public void proceedBtn(){
        String newNickName = cp_txtNickname.getText();

        String query = "Select * from Account where account_id = '" + data.accountid + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            if (rs.next()) {
                originalNickName = rs.getString("nickname");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(newNickName.equals(originalNickName)) {
            // Thông báo thông tin không thay đổi
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Information does not change");
            alert.showAndWait();
        }
        else if(!FormController.regexEmail.isValidEmail(cp_txtEmail.getText())){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email address");
            alert.showAndWait();
        }
        else {
            // Cập nhật thông tin trong cơ sở dữ liệu
            String updateQuery = "UPDATE Account SET nickname = ? WHERE account_id = ?";
            try {
                PreparedStatement st = cnn.prepareStatement(updateQuery);
                st.setString(1, newNickName);
                st.setInt(2, data.accountid);
                st.executeUpdate();

                // Thông báo cập nhật thành công
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Update successful");
                alert.showAndWait();

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("The information will change when you log out and log back in");
                alert.showAndWait();

                Stage currentStage = (Stage) cp_apPersonalInformation.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setPersonalInformation(String nickname,String email) {
        cp_txtNickname.setText(nickname);
        cp_txtEmail.setText(email);
    }

}
