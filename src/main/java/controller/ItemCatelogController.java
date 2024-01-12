package controller;

import bo.BoFactory;
import bo.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tm.OrderTm;
import util.BoType;

import java.io.IOException;
import java.util.List;

public class ItemCatelogController {


    public Circle logo;
    public BorderPane pane;
    public GridPane itemGrid;
    public ScrollPane gridPane;
    public TreeTableColumn colItemName;
    public TreeTableColumn colQty;
    public TreeTableColumn colOption;
    public Label lblTotal;
    public JFXTreeTableView orderTable;
    public TreeTableColumn colAmount;
    public JFXButton reloadBtn;

    private List<ItemDto> itemList;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private static double total=0;
    private static ObservableList<OrderTm> tmList = FXCollections.observableArrayList();

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        itemList = itemBo.getAll();
        int collumn =0;
        int row =1;

        itemList = itemBo.getAll();
        for(int i=0;i<itemList.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/Item.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(itemList.get(i));
                if (collumn==4){
                    collumn=0;
                    row++;
                }
                itemGrid.add(anchorPane,collumn++,row);
                GridPane.setMargin(anchorPane,new Insets(10));
                itemGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                itemGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                itemGrid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                itemGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                itemGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                itemGrid.setMaxHeight(Region.USE_COMPUTED_SIZE);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }
        colItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleteBtn"));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
        stage.show();
        stage.setTitle("User Dashboard");
        stage.centerOnScreen();
    }
    private void setTotal(){
        lblTotal.setText(String.valueOf(total));
        System.out.println(total);
    }

    public void addToCart(ItemDto dto,int qty){
        JFXButton btn = new JFXButton("Delete");
        OrderTm orderTm = new OrderTm(
                dto.getId(),
                dto.getName(),
                qty,
                dto.getUnitPrice()*qty,
                btn
        );

        boolean isExist = false;
        for (OrderTm tm: tmList) {
            if (tm.getId().equals(dto.getId())){
                isExist=true;

            }
        }
        tmList.add(orderTm);
        System.out.println(tmList);
        System.out.println(tmList.size());

    }

    public void ReloadBtnOnAction(ActionEvent actionEvent) {
//        lblTotal.setText(String.valueOf(total));
//        TreeItem<OrderTm> treeObject = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
//        orderTable.setRoot(treeObject);
//        orderTable.setShowRoot(false);
    }
}
