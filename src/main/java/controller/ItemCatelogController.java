package controller;

import bo.BoFactory;
import bo.ItemBo;
import com.jfoenix.controls.JFXButton;
import dao.custom.ItemDao;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tm.ItemTm;
import util.BoType;

import java.io.IOException;
import java.util.List;

public class ItemCatelogController {


    public Circle logo;
    public BorderPane pane;
    public GridPane itemGrid;
    public ScrollPane gridPane;

    private List<ItemDto> itemList;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

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
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
        stage.show();
        stage.setTitle("User Dashboard");
        stage.centerOnScreen();
    }

    public void addToCart(ItemDto dto,int qty){
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        JFXButton deleteBtn = new JFXButton("Delete");
        tmList.add(new ItemTm(
                dto.getId(),
                dto.getName(),
                qty,
                dto.getUnitPrice(),
                deleteBtn
        ));
    }
}
