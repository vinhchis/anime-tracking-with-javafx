package Controllers;

import Models.data;
import Util.DBConnect;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


public class FormController implements Initializable {


    @FXML
    private Button btnSignUp;

    @FXML
    private ImageView imageSide;

    @FXML
    private AnchorPane forgotForm;

    @FXML
    private Button fp_backBtn;

    @FXML
    private TextField fp_emailTxt;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private AnchorPane loginForm;


    @FXML
    private GridPane list_gridPane;

    @FXML
    private GridPane list_gridPaneTopAnime;

    @FXML
    private ScrollPane list_scrollPaneAnime;

    @FXML
    private ScrollPane list_scrollPaneTopAnime;


    @FXML
    private AnchorPane newpassForm;

    @FXML
    private Button np_backBtn;

    @FXML
    private Button np_changePasswordBtn;

    @FXML
    private PasswordField np_confirmPasswordTxt;

    @FXML
    private PasswordField np_passwordTxt;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private Button si_btnLogin;

    @FXML
    private Hyperlink si_forgotPasswordLink;

    @FXML
    private PasswordField si_passwordTxt;

    @FXML
    private TextField si_usernameTxt;

    @FXML
    private AnchorPane sideForm;

    @FXML
    private Button side_alreadyBtn;

    @FXML
    private Button side_btnCreate;

    @FXML
    private PasswordField su_confirmpasswordTxt;

    @FXML
    private TextField su_emailTxt;

    @FXML
    private PasswordField su_passwordTxt;

    @FXML
    private TextField su_usernameTxt;

    Connection cnn;
    PreparedStatement st;
    ResultSet rs;

    StringBuilder ID = new StringBuilder();
    StringBuilder UserID = new StringBuilder();

    String getID;
    private Alert alert;

    // Class
    public class regexPassword{
        private static final Pattern PASSWORD_PATTERN
                = Pattern.compile("^(?=.*[0-9]).{6,}$");

        public static boolean isValidPassword(String password) {
            return PASSWORD_PATTERN.matcher(password).matches();
        }
    }

    public class regexEmail{
        private static final Pattern EMAIL_PATTERN
                = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        public static boolean isValidEmail(String email) {
            return EMAIL_PATTERN.matcher(email).matches();
        }

    }

    // End Class

    // Method
    public void clearFields(){
        su_usernameTxt.setText("");
        su_passwordTxt.setText("");
        su_confirmpasswordTxt.setText("");
        su_emailTxt.setText("");
    }
    // End Method

    // Constructor
    public FormController(){
        cnn = DBConnect.makeConnection();
    }
    // End Constructor

    // Action Event
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if(event.getSource() == side_btnCreate){
           slider.setNode(sideForm);
           slider.setToX(300);
           slider.setDuration(Duration.seconds(0.5));
           slider.setOnFinished((ActionEvent e) -> {
               side_alreadyBtn.setVisible(true);
               side_btnCreate.setVisible(false);
               forgotForm.setVisible(false);
               loginForm.setVisible(true);
               newpassForm.setVisible(false);
               si_passwordTxt.setText("");
           });
        slider.play();
        }

        else if(event.getSource() == side_alreadyBtn){
            slider.setNode(sideForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(0.5));
            slider.setOnFinished((ActionEvent e)-> {
                side_alreadyBtn.setVisible(false);
                side_btnCreate.setVisible(true);
                si_passwordTxt.setText("");
            });
            slider.play();
        }
    }

    public void linkForgotPassword(){
        forgotForm.setVisible(true);
        loginForm.setVisible(false);
    }
    public void backLoginFormBtn(){
        forgotForm.setVisible(false);
        loginForm.setVisible(true);
    }

    public void backForgotPasswordFormBtn(){
        forgotForm.setVisible(true);
        newpassForm.setVisible(false);
    }

    public void loginBtn(){
        if(si_usernameTxt.getText().isEmpty() || si_passwordTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }
        else{
            String selectData = "select username,password,account_id,nickname,isadmin from Account where username = ? and password = ?";
            try {
                st = cnn.prepareStatement(selectData);
                st.setString(1, si_usernameTxt.getText());
                st.setString(2, si_passwordTxt.getText());

                rs = st.executeQuery();
                // If successfully login, then proceed to another form which is our main form ( dashboard )
                if (rs.next()) {
                    // To get the username that user used
                    data.accountid = rs.getInt("account_id");
                    data.nickname = rs.getString("nickname");
                    boolean isAdmin = rs.getBoolean("isadmin");
                    if(isAdmin){
                        showSuccessAlertAndSwitchForm("Admin Menu","/Views/Form/Administrator.fxml");
                    }
                    else{
                        showSuccessAlertAndSwitchForm("User Menu", "/Views/Form/UserInterface.fxml");
                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    // Switch and Alert
    private void showSuccessAlertAndSwitchForm(String title, String fxmlFileName) {
        // Hiển thị thông báo đăng nhập thành công
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Login!");
            alert.showAndWait();

            // Chuyển đến form mới
            switchForm(title, fxmlFileName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void switchForm(String title, String fxmlFileName){
        try{
            si_btnLogin.getScene().getWindow().hide();
            URL fxmlLocation = getClass().getResource(fxmlFileName);
            Parent root = FXMLLoader.load(fxmlLocation);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    //
    public void registerBtn(){
        TranslateTransition slider = new TranslateTransition();
        if(su_usernameTxt.getText().isEmpty() || su_passwordTxt.getText().isEmpty() ||
            su_confirmpasswordTxt.getText().isEmpty() || su_emailTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }
        else{
            String query = "insert into Account(username,password,email,nickname) values(?,?,?,?)";
            try {
                String checkUsername = "select Username from Account where Username = '" + su_usernameTxt.getText() + "'";
                st = cnn.prepareStatement(checkUsername);
                rs = st.executeQuery();
                if(rs.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Username already exists");
                    alert.showAndWait();
                }
                else if(!regexPassword.isValidPassword(su_passwordTxt.getText())){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid password, atleast 6 characters are needed");
                    alert.showAndWait();
                }
                else if(!regexEmail.isValidEmail(su_emailTxt.getText())){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid email address");
                    alert.showAndWait();
                }


                else if(!(su_confirmpasswordTxt.getText().equals(su_passwordTxt.getText()))){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password does not match");
                    alert.showAndWait();
                }
                else{

                    // Check Email exist
                    String checkEmail = "select email from Account where Email= '" + su_emailTxt.getText() + "'";
                    st = cnn.prepareStatement(checkEmail);
                    rs = st.executeQuery();
                    if(rs.next()){
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Email already exists");
                        alert.showAndWait();
                    }
                    // End
                    else{

                        st = cnn.prepareStatement(query);
                        st.setString(1, su_usernameTxt.getText());
                        st.setString(2, su_passwordTxt.getText());
                        st.setString(3, su_emailTxt.getText());
                        st.setString(4, su_usernameTxt.getText());
                        st.executeUpdate();


                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully registered Account");
                        alert.showAndWait();

                        clearFields();
                        slider.setNode(sideForm);
                        slider.setToX(0);
                        slider.setDuration(Duration.seconds(0.5));
                        slider.setOnFinished((ActionEvent e) -> {
                            side_alreadyBtn.setVisible(false);
                            side_btnCreate.setVisible(true);
                        });
                        slider.play();
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void proceedBtn(){
        if(fp_emailTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }
        else if(!regexEmail.isValidEmail(fp_emailTxt.getText())){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid email address");
            alert.showAndWait();
        }

        else{
            String checkEmail = "select email from Account where email = '" + fp_emailTxt.getText() + "'";
            try{
                st = cnn.prepareStatement(checkEmail);
                rs = st.executeQuery();
                if(rs.next()){
                    forgotForm.setVisible(false);
                    newpassForm.setVisible(true);
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Email does not exist");
                    alert.showAndWait();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void changePasswordBtn(){
        if(np_passwordTxt.getText().isEmpty() || np_confirmPasswordTxt.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        }else{
            if(np_confirmPasswordTxt.getText().equals(np_passwordTxt.getText())){
                    String updatePassword = "update Account set password= '" + np_passwordTxt.getText() + "' where email= '" + fp_emailTxt.getText() + "'";
                try{
                    st = cnn.prepareStatement(updatePassword);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed Password!");
                    alert.showAndWait();

                    loginForm.setVisible(true);
                    newpassForm.setVisible(false);
                    // Clear Fields

                    fp_emailTxt.setText("");
                    np_passwordTxt.setText("");
                    np_confirmPasswordTxt.setText("");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }
    // End Action Event

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //TO DO
    }
}

