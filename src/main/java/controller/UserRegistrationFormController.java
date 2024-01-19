package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import util.BoType;

import java.io.IOException;

public class UserRegistrationFormController {
    public Circle logo;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public Label lblId;
    public JFXTextField txtName;
    public BorderPane pane;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        lblId.setText(userBo.generateId());
    }

    public void RegisterBtnOnAction(ActionEvent actionEvent) {
        Boolean isSaved = userBo.saveUser(new UserDto(
                lblId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        ));
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"User Succesfully Saved !").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
        stage.setTitle("Admin Dashboard");
        stage.centerOnScreen();
        stage.show();
    }
}
