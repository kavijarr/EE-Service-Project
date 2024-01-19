package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardControler {
    public Label lblDate;
    public Label lblTime;
    public BorderPane pane;
    public Circle logo;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
    }

    public void StaffLoginBtn(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StaffLoginForm.fxml"))));
        stage.setTitle("Staff Login");
        stage.centerOnScreen();
        stage.show();
    }

    public void AdminBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminLogin.fxml"))));
            stage.show();
            stage.setTitle("AdminLogin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
