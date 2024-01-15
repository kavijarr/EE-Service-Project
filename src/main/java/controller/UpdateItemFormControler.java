package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
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
import util.BoType;

import java.io.IOException;

public class UpdateItemFormControler {
    public Circle logo;
    public Label lblItemId;
    public JFXTextField txtItemName;
    public JFXTextField txtQty;
    public JFXTextField txtPrice;
    public BorderPane pane;
    private ItemDto item;
    ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

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
