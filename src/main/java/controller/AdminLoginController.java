package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {
    public BorderPane pane;

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

    public void AdminLoginBtnOnAction(ActionEvent actionEvent) {

    }
}
