package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.RepairBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.RepairDetailsDto;
import dto.RepairDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tm.PartsTm;
import util.BoType;
import util.StatusInfo;
import util.StatusType;

import java.io.IOException;
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
    public JFXTreeTableView tblParts;
    public BorderPane pane;
    private RepairDto dto;
    private ObservableList<PartsTm> tmList = FXCollections.observableArrayList();
    private List<RepairDetailsDto> list = new ArrayList<>();
    CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    RepairBo repairBo = BoFactory.getInstance().getBo(BoType.REPAIR);
    private double total;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        colPartName.setCellValueFactory(new TreeItemPropertyValueFactory<>("partName"));
        colPartCost.setCellValueFactory(new TreeItemPropertyValueFactory<>("partCost"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        Platform.runLater(()->{
            populateCmbStatus();
        });

    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ViewOrdersForm.fxml"))));
        stage.show();
        stage.setTitle("Orders");
        stage.centerOnScreen();
    }

    public void CompleteOrderBtnOnAction(ActionEvent actionEvent) {
        Boolean isComplete = repairBo.saveDetails(list);
        if (isComplete){
            new Alert(Alert.AlertType.CONFIRMATION,"Order Completed!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    public void setData(RepairDto data){
        this.dto=data;
        loadCustomer();
        lblItemName.setText(dto.getItemName());
        txtDesc.setText(data.getDesc());
    }

    public void AddPartBtnOnAction(ActionEvent actionEvent) {
        JFXButton btn = new JFXButton("Delete");
        total+=Double.parseDouble(txtPartCost.getText());
        PartsTm tm = new PartsTm(
                txtPartName.getText(),
                Double.parseDouble(txtPartCost.getText()),
                btn);
        RepairDetailsDto detailsDto = new RepairDetailsDto(
                txtPartName.getText(),
                Double.parseDouble(txtPartCost.getText()),
                dto.getRepairId()
        );
        btn.setOnAction(actionEvent1 -> {
            tmList.remove(tm);
            list.remove(detailsDto);
        });
        tmList.add(tm);
        list.add(detailsDto);

        TreeItem<PartsTm> treeObject = new RecursiveTreeItem<PartsTm>(tmList, RecursiveTreeObject::getChildren);
        tblParts.setRoot(treeObject);
        tblParts.setShowRoot(false);
        lblTotal.setText(String.valueOf(total));
    }

    private void loadCustomer(){
        CustomerDto customer = customerBo.getCustomer(dto.getCustomerId());
        lblCustomerName.setText(customer.getCustomerName());
        lblNumber.setText(customer.getContactNumber());
        lblEmail.setText(customer.getCustomerEmail());
    }

    private void populateCmbStatus(){
        if(dto.getStatus()== StatusInfo.statusType(StatusType.PENDING)){
            cmbStatus.setItems(
                    FXCollections.observableArrayList("Processing")
            );
        } else if (dto.getStatus() == StatusInfo.statusType(StatusType.PROCESSING)) {
            cmbStatus.setValue(
                    FXCollections.observableArrayList("Processing")
            );
            cmbStatus.setDisable(true);
        }
    }

    private void updateStatus(String status){
        switch (status){
            case "Processing" : repairBo.updateStatus(StatusType.PROCESSING,dto.getRepairId());
        }
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        updateStatus(cmbStatus.getValue().toString());
    }
}
