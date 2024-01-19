package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.BoType;
import util.Login;
import util.UserType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class  AddItemFormController {
    public Circle logo;
    public BorderPane pane;
    public JFXTextField txtItemName;
    public JFXTextField txtPrice;
    public JFXTextField txtQty;
    public ComboBox cmbCategory;
    public ImageView viewImg;
    public Label lblItemId;
    private String imgUrl;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private List<ItemDto> itemList;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        lblItemId.setText(itemBo.generateID());
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        if (Login.getUser().equals(UserType.STAFF)){
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
            stage.show();
            stage.setTitle("User Dashboard");
            stage.centerOnScreen();
        } else if (Login.getUser().equals(UserType.ADMIN)) {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
            stage.show();
            stage.setTitle("Admin Dashboard");
            stage.centerOnScreen();
        }
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
         imgUrl,
                true
        );
        System.out.println(dto);
        Boolean isSaved = itemBo.saveItem(dto);

        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Succesfully Saved").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }
}
