package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import util.BoType;
import util.Login;
import util.UserType;

import java.io.IOException;

public class StaffLoginFormController {
    public Circle logo;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public BorderPane pane;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    public void StaffLoginBtnOnAction(ActionEvent actionEvent) throws IOException {
        try {
           UserDto user = userBo.getUser(txtEmail.getText());
            if (user.getPassword().equals(txtPassword.getText())){
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
                stage.setTitle("Admin Dashboard");
                stage.centerOnScreen();
                stage.show();
                Login.setUser(UserType.STAFF);
            }else {
                new Alert(Alert.AlertType.ERROR,"Incorrect UserName or Password").show();
            }
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR,"User Not Registered").show();
        }
    }

    public void ForgotPasswordBtnOnAction(ActionEvent actionEvent) {

    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
    }
}
