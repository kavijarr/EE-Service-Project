package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class UserDashboardController {
    public Circle logo;
    public BorderPane pane;

    @FXML
    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) {

    }

    public void ItemCatelogBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemCatelog.fxml"))));
        stage.show();
        stage.setTitle("Item Catelog");
        stage.centerOnScreen();
    }

    public void ServiceBtnOnAction(ActionEvent actionEvent) {
    }

    public void OrdersBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ViewOrdersForm.fxml"))));
        stage.show();
        stage.setTitle("Orders");
        stage.centerOnScreen();
    }

    public void ReportsBtnOnAction(ActionEvent actionEvent) {
    }

    public void AddItemBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddItemForm.fxml"))));
        stage.show();
        stage.setTitle("Add New Item");
        stage.centerOnScreen();
    }

    public void CustomersBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ViewCustomersForm.fxml"))));
        stage.setTitle("Customers");
        stage.centerOnScreen();
        stage.show();
    }
}
