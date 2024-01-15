package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import bo.custom.OrderDetailsBo;
import dto.ItemDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.BoType;

import java.io.IOException;

public class ItemController {
    public ImageView itemImg;
    public Label lblItemName;
    public Label lblItemPrice;
    public Label lblItemId;
    public AnchorPane pane;
    public Label lblQty;

    private ItemDto item;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private OrderDetailsBo orderDetailsBo = BoFactory.getInstance().getBo(BoType.ORDERDETAILS);
    private int qty = 0;
    private static ItemCatelogController controller = new ItemCatelogController();

    public void AddtoCartBtnOnAction(ActionEvent actionEvent) {
        ItemDto dto = itemBo.getItem(lblItemId.getText());
        if (dto!=null){
            controller.addToCart(itemBo.getItem(lblItemId.getText()),qty);
        }
    }

    public void ItemDetailsBtnOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateItemForm.fxml"));
        Parent root = loader.load();
        UpdateItemFormControler controler = loader.getController();
        controler.setData(itemBo.getItem(lblItemId.getText()));
        Stage stage = (Stage) pane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        stage.close();
        newStage.show();
        newStage.centerOnScreen();
        newStage.setTitle("Update Item");
        System.out.println(itemBo.getItem(lblItemId.getText()));
    }

    public void setData(ItemDto dto){
        this.item=dto;
        itemImg.setImage(new Image(item.getImgUrl()));
        lblItemName.setText(item.getName());
        lblItemId.setText(item.getId());
        lblItemPrice.setText(String.valueOf(item.getUnitPrice()));
    }

    public void MinusBtnOnAction(ActionEvent actionEvent) {
        if (qty>0){
            qty--;
            lblQty.setText(String.valueOf(qty));
        }
    }

    public void PlusBtnOnAction(ActionEvent actionEvent) {
        qty++;
        lblQty.setText(String.valueOf(qty));
    }

    public void DeleteBtnOnAction(ActionEvent actionEvent) {
        Boolean isDeleted = itemBo.deleteItem(lblItemId.getText());

        if (isDeleted){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted Successfuly").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }
}
