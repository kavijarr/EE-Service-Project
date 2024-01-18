package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
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

public class CreateCustomerFormController {
    public Circle logo;
    public BorderPane pane;
    public Label lblCustomerId;
    public JFXTextField txtMobileNumber;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerEmail;
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private static String type;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        lblCustomerId.setText(customerBo.generateId());
    }

    public void SaveCustomerBtnAction(ActionEvent actionEvent) {
        boolean issaved = customerBo.saveCustomer(new CustomerDto(
                lblCustomerId.getText(),
                txtCustomerName.getText(),
                txtCustomerEmail.getText(),
                txtMobileNumber.getText()
        ));
        if (issaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Customer Succesfully Saved !").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        if(type.equals("Service")){
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceServiceOrderForm.fxml"))));
        } else if (type.equals("Order")) {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderForm.fxml"))));
        }
        stage.setTitle("Place Order");
        stage.centerOnScreen();
        stage.show();
    }
    public void setStage(String data){
        this.type=data;
    }
}
