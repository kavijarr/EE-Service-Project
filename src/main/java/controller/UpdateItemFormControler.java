package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.BoType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateItemFormControler {
    public Circle logo;
    public Label lblItemId;
    public JFXTextField txtItemName;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public BorderPane pane;
    public Label lblDate;
    public Label lblTime;
    private ItemDto item;
    ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
        showTime();

    }

    private void showTime(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();

            // Format date and time separately
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = now.format(dateFormatter);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = now.format(timeFormatter);

            lblDate.setText(formattedDate);
            lblTime.setText(formattedTime);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemCatelog.fxml"))));
        stage.setTitle("Item Catelog");
        stage.centerOnScreen();
        stage.show();
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        ItemDto dto = new ItemDto(
                lblItemId.getText(),
                txtItemName.getText(),
                Integer.parseInt(txtQty.getText()),
                Double.parseDouble(txtPrice.getText()),
                item.getImgUrl(),
                true
        );
        Boolean isUpdated = itemBo.updateItem(dto);
        if (isUpdated==true){
            setData(itemBo.getItem(lblItemId.getText()));
            new Alert(Alert.AlertType.INFORMATION,"Item Succesfully Updated !").show();
        }
    }

    public void setData(ItemDto dto){
        this.item=dto;
        lblItemId.setText(item.getId());
        txtItemName.setText(item.getName());
        txtQty.setText(String.valueOf(item.getQtyOnHand()));
        txtPrice.setText(String.valueOf(item.getUnitPrice()));
    }
}
