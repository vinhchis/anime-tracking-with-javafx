package Controllers;

import Models.data;
import Util.DBConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CardAccountInformationController implements Initializable {

    @FXML
    private AnchorPane ca_apAccountInformation;

    @FXML
    private Button ca_btnProceed;

    @FXML
    private PasswordField ca_txtConfirmPassword;

    @FXML
    private PasswordField ca_txtOldPassword;

    @FXML
    private PasswordField ca_txtPassword;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;

    private Alert alert;
    private String originalPassword;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public CardAccountInformationController() {
        cnn = DBConnect.makeConnection();
    }

    public void setAccountInformation(String password) {
        ca_txtPassword.setText(password);
        ca_txtConfirmPassword.setText(password);
    }

    public void proceedBtn(){
        String oldPassword = ca_txtOldPassword.getText();
        String newPassword = ca_txtPassword.getText();
        String newConfirmPassword = ca_txtPassword.getText();

        String query = "Select * from Account where account_id = '" + data.accountid + "'";
        try {
            st = cnn.prepareStatement(query);
            rs = st.executeQuery();
            if (rs.next()) {
                originalPassword = rs.getString("password");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (!newPassword.equals(newConfirmPassword)) {
            // Thông báo lỗi nếu mật khẩu và mật khẩu xác nhận không khớp
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password does not match");
            alert.showAndWait();
            return; // Dừng quá trình nếu mật khẩu không khớp
        }

        else if(oldPassword.equals(originalPassword) && newPassword.equals(originalPassword)) {
            // Thông báo thông tin không thay đổi
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Information does not change");
            alert.showAndWait();
        }
        else if(!FormController.regexPassword.isValidPassword(ca_txtPassword.getText())){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid password, atleast 6 characters are needed");
            alert.showAndWait();
        }
        else {
            // Cập nhật thông tin trong cơ sở dữ liệu
            String updateQuery = "UPDATE Account SET password = ? WHERE account_id = ?";
            try {
                PreparedStatement st = cnn.prepareStatement(updateQuery);
                st.setString(1, newPassword);
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

                Stage currentStage = (Stage) ca_apAccountInformation.getScene().getWindow();
                currentStage.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}