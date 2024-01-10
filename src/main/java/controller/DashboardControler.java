package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardControler {
    public Label lblDate;
    public Label lblTime;
    public BorderPane pane;

    public void StaffLoginBtn(ActionEvent actionEvent) {
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
