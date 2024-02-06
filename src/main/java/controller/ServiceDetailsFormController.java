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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;
import tm.PartsTm;
import util.BoType;
import util.EmailSender;
import util.StatusInfo;
import util.StatusType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public JFXButton completeOrderBtn;
    public JFXButton updateBtn;
    public JFXButton addPartBtn;
    public Label lblDate;
    public Label lblTime;
    private RepairDto dto;
    private ObservableList<PartsTm> tmList = FXCollections.observableArrayList();
    private List<RepairDetailsDto> list = new ArrayList<>();
    CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    RepairBo repairBo = BoFactory.getInstance().getBo(BoType.REPAIR);
    EmailSender emailSender = new EmailSender();
    private double total;

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
        txtDesc.setDisable(true);
        showTime();

        Platform.runLater(()->{
            if (dto.getStatus()==StatusInfo.statusType(StatusType.PENDING)){
                completeOrderBtn.setDisable(true);
                addPartBtn.setDisable(true);
                completeOrderBtn.setDisable(true);
            }
        });

        colPartName.setCellValueFactory(new TreeItemPropertyValueFactory<>("partName"));
        colPartCost.setCellValueFactory(new TreeItemPropertyValueFactory<>("partCost"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        Platform.runLater(()->{
            populateCmbStatus();
        });

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
            updateStatus("Completed");
            try {
                JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream("/reports/serviceSummery.jrxml"));
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("total", repairBo.getTotal(this.dto.getRepairId()));
                JasperReport jasperReport = JasperCompileManager.compileReport(design);
                JRBeanCollectionDataSource customerReport = repairBo.getRepairSummery(this.dto.getRepairId());
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, customerReport);
                JasperViewer.viewReport(jasperPrint, false);


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
                byte[] reportBytes = byteArrayOutputStream.toByteArray();
                emailSender.sendReciept(customerBo.getCustomer(this.dto.getCustomerId()).getCustomerEmail(), reportBytes);

            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    public void setData(RepairDto data){
        this.dto=data;
        loadCustomer();
        lblItemName.setText(dto.getItemName());
        txtDesc.setText(data.getDesc());
        if (dto.getStatus()==StatusInfo.statusType(StatusType.COMPLETED)){
            completeOrderBtn.setDisable(true);
            addPartBtn.setDisable(true);
            txtPartName.setDisable(true);
            txtPartCost.setDisable(true);
            RepairDto repair = repairBo.getRepair(dto.getRepairId());
            loadParts(repair);
        } else if (dto.getStatus() == StatusInfo.statusType(StatusType.CLOSED)) {
            completeOrderBtn.setDisable(true);
            updateBtn.setDisable(true);
            addPartBtn.setDisable(true);
            txtPartName.setDisable(true);
            txtPartCost.setDisable(true);
            RepairDto repair = repairBo.getRepair(dto.getRepairId());
            loadParts(repair);
        }
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
        } else if (dto.getStatus() == StatusInfo.statusType(StatusType.COMPLETED)) {
            cmbStatus.setItems(
                    FXCollections.observableArrayList("Closed")
            );
        } else if (dto.getStatus() == StatusInfo.statusType(StatusType.CLOSED)) {
            cmbStatus.setValue(
                    FXCollections.observableArrayList("Processing")
            );
            cmbStatus.setDisable(true);

        }
    }

    private void updateStatus(String status){
        boolean isUpdated=false;
        switch (status){
            case "Processing" : isUpdated = repairBo.updateStatus(StatusType.PROCESSING,dto.getRepairId());break;
            case "Completed" : isUpdated = repairBo.updateStatus(StatusType.COMPLETED,dto.getRepairId());break;
            case "Closed" : isUpdated = repairBo.updateStatus(StatusType.CLOSED,dto.getRepairId());break;
        }
        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Status Updated Succsesfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Error").show();
        }
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        updateStatus(cmbStatus.getValue().toString());
    }

    private void loadParts(RepairDto data){
        List<RepairDetailsDto> list1 = data.getList();
        ObservableList<PartsTm> partList = FXCollections.observableArrayList();
        for (RepairDetailsDto detailsDto:list1) {
            total+=detailsDto.getPrice();
            JFXButton btn = new JFXButton("Delete");
            btn.setDisable(true);
            partList.add(new PartsTm(
                    detailsDto.getPartName(),
                    detailsDto.getPrice(),
                    btn
                    )
            );
        }
        TreeItem<PartsTm> treeObject = new RecursiveTreeItem<PartsTm>(partList, RecursiveTreeObject::getChildren);
        tblParts.setRoot(treeObject);
        tblParts.setShowRoot(false);
        lblTotal.setText(String.valueOf(total));
    }
}
