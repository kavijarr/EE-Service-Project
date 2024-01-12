package controller;

import bo.BoFactory;
import bo.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dao.custom.ItemDao;
import dto.ItemDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.BoType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class AddItemFormController {
    public Circle logo;
    public BorderPane pane;
    public JFXTextField txtItemName;
    public JFXTextField txtPrice;
    public JFXTextField txtQty;
    public ComboBox cmbCategory;
    public ImageView viewImg;
    private String imgUrl;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private List<ItemDto> itemList;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
        stage.show();
        stage.setTitle("User Dashboard");
        stage.centerOnScreen();
    }

    public void AddImgBtnOnAction(ActionEvent actionEvent) throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add((new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg")));
        File file = fileChooser.showOpenDialog(null);
        if (file!=null){
             imgUrl = file.toURL().toString();
             viewImg.setImage(new Image(imgUrl));
        }
    }

    public void newCategoryBtnOnAction(ActionEvent actionEvent) {
    }

    public void SaveBtnOnAction(ActionEvent actionEvent) {
        ItemDto dto = new ItemDto(
         itemBo.generateID(),
         txtItemName.getText(),
         Integer.parseInt(txtQty.getText()),
         Double.parseDouble(txtPrice.getText()),
         imgUrl
        );
        System.out.println(dto);
        itemBo.saveItem(dto);
    }
}
