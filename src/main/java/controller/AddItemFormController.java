package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.ItemDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import util.BoType;
import util.Login;
import util.UserType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public Label lblDate;
    public Label lblTime;
    public JFXButton addItem;
    private String imgUrl;
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private List<ItemDto> itemList;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        lblItemId.setText(itemBo.generateID());
        showTime();
        cmbCategory.setDisable(true);
        addItem.setDisable(true);

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
