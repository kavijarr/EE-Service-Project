package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.RepairBo;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.RepairDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import util.BoType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PlaceServiceOrderFormController {
    public Circle logo;
    public Label lblServiceId;
    public ComboBox cmbNumber;
    public Label lblCustomerId;
    public Label lblCustomerName;
    public Label lblEmail;
    public JFXTextArea txtDesc;
    public JFXTextField txtItemName;

    private RepairBo repairBo = BoFactory.getInstance().getBo(BoType.REPAIR);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private List<CustomerDto> customers = new ArrayList<>();

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        loadCustomers();
        cmbNumber.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, number) -> {
            for (CustomerDto dto: customers) {
                if (dto.getContactNumber().equals(number)){
                    lblCustomerName.setText(dto.getCustomerName());
                    lblCustomerId.setText(dto.getId());
                    lblEmail.setText(dto.getCustomerEmail());
                }
            }
        }));
        lblServiceId.setText(repairBo.generateId());
    }

    public void BackBtnOnAction(ActionEvent actionEvent) {

    }

    public void NewCustomerBtnOnAction(ActionEvent actionEvent) {
    }

    public void ConformOrderBtnOnAction(ActionEvent actionEvent) {
        RepairDto dto = new RepairDto(
                lblServiceId.getText(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                lblCustomerId.getText(),
                txtItemName.getText()
        );
        Boolean isSaved = repairBo.saveRepair(dto);
        if (isSaved){
            new Alert(Alert.AlertType.CONFIRMATION,"Order Succesfully Saved!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    private void loadCustomers(){
        customers = customerBo.getAll();
        ObservableList list = FXCollections.observableArrayList();
        for (CustomerDto dto: customers) {
            list.add(dto.getContactNumber());
        }
        cmbNumber.setItems(list);
    }
}
