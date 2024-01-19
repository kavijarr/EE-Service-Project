package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import util.Login;
import util.UserType;

import java.io.IOException;

public class AdminLoginController {
    public BorderPane pane;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public Circle logo;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
    }
    public void BackBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
            stage.show();
            stage.setTitle("Dashboard");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void AdminLoginBtnOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("admin")&&txtPassword.getText().equals("1234")){
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
            stage.setTitle("Admin Dashboard");
            stage.centerOnScreen();
            stage.show();
            Login.setUser(UserType.ADMIN);
        }else{
            new Alert(Alert.AlertType.ERROR,"Incorrect UserName or Password").show();
        }

    }
}
