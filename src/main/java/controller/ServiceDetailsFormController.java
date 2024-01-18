package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.RepairBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.RepairDetailsDto;
import dto.RepairDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import tm.PartsTm;
import util.BoType;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsFormController {

    public Label lblCustomerName;
    public Label lblNumber;
    public Label lblEmail;
    public Label lblItemName;
    public TextField txtDesc;
    public JFXTextField txtPartName;
    public JFXTextField txtPartCost;
    public ComboBox cmbStatus;
    public TreeTableColumn colPartName;
    public TreeTableColumn colPartCost;
    public TreeTableColumn colOption;
    public Label lblTotal;
    public Circle logo;
    private RepairDto dto;
    private ObservableList<PartsTm> tmList = FXCollections.observableArrayList();
    private List<RepairDetailsDto> list = new ArrayList<>();
    CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    RepairBo repairBo = BoFactory.getInstance().getBo(BoType.REPAIR);

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) {
    }

    public void CompleteOrderBtnOnAction(ActionEvent actionEvent) {
        repairBo.saveDetails(list);
    }

    public void setData(RepairDto data){
        this.dto=data;
        loadCustomer();
        lblItemName.setText(dto.getItemName());
        txtDesc.setText(data.getDesc());
    }

    public void AddPartBtnOnAction(ActionEvent actionEvent) {
        JFXButton btn = new JFXButton("Delete");
        tmList.add(new PartsTm(
                txtPartName.getText(),
                Double.parseDouble(txtPartCost.getText()),
                btn
        ));
        list.add(new RepairDetailsDto(
                txtPartName.getText(),
                Double.parseDouble(txtPartCost.getText()),
                dto.getRepairId()
        ));
    }

    private void loadCustomer(){
        CustomerDto customer = customerBo.getCustomer(dto.getCustomerId());
        lblCustomerName.setText(customer.getCustomerName());
        lblNumber.setText(customer.getContactNumber());
        lblEmail.setText(customer.getCustomerEmail());
    }
}
