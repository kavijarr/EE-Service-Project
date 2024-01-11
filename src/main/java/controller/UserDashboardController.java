package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.awt.*;

public class UserDashboardController {
    public Circle logo;

    @FXML
    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) {
    }

    public void ItemCatelogBtnOnAction(ActionEvent actionEvent) {
    }

    public void ServiceBtnOnAction(ActionEvent actionEvent) {
    }

    public void OrdersBtnOnAction(ActionEvent actionEvent) {
    }

    public void ReportsBtnOnAction(ActionEvent actionEvent) {
    }

    public void AddItemBtnOnAction(ActionEvent actionEvent) {
    }
}
