package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.OrderBo;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import tm.OrderTm;
import util.BoType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PlaceOrderFormController {
    public Circle logo;
    public BorderPane pane;
    public Label lblOrderId;
    public Label lblCustomerId;
    public Label lblCustomerName;
    public Label lblCustomerEmail;
    public ComboBox cmbContactNumber;
    public JFXTreeTableView tblOrders;
    public TreeTableColumn colItemId;
    public TreeTableColumn colItemName;
    public TreeTableColumn colQty;
    public TreeTableColumn colAmount;
    public Label lblTotal;
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private List<CustomerDto> customers = new ArrayList<>();
    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private ObservableList<OrderTm> tmList;
    private double total=0;

    public  void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        loadCustomerNumbers();
        loadOrderTable();

        cmbContactNumber.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, number) -> {
            for (CustomerDto dto: customers) {
                if (dto.getContactNumber().equals(number)){
                    lblCustomerName.setText(dto.getCustomerName());
                    lblCustomerId.setText(dto.getId());
                    lblCustomerEmail.setText(dto.getCustomerEmail());
                }
            }
        }));

        colItemId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        lblTotal.setText(String.valueOf(total));
        lblOrderId.setText(orderBo.generateId());
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemCatelog.fxml"))));
        stage.setTitle("Item Catelog");
        stage.centerOnScreen();
        stage.show();
    }

    public void NewCustomerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CreateCustomerForm.fxml"))));
        stage.setTitle("Create Customer");
        stage.centerOnScreen();
        CreateCustomerFormController controller = new CreateCustomerFormController();
        controller.setStage("Order");
        stage.show();
    }

    private void loadCustomerNumbers(){
        customers = customerBo.getAll();
        ObservableList list = FXCollections.observableArrayList();
        for (CustomerDto dto: customers) {
            list.add(dto.getContactNumber());
        }
        cmbContactNumber.setItems(list);
    }

    private void loadOrderTable(){
        tmList = orderBo.getTmList();
        TreeItem<OrderTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblOrders.setRoot(treeItem);
        tblOrders.setShowRoot(false);
        for (OrderTm tm:tmList) {
            total+= tm.getAmount();
        }
    }

    public void PlaceOrderBtnOnAction(ActionEvent actionEvent) {
        List<OrderDetailsDto> list = new ArrayList<>();
        for (OrderTm tm: tmList) {
            list.add(new OrderDetailsDto(
                    lblOrderId.getText(),
                    tm.getId(),
                    tm.getQty(),
                    tm.getAmount() / tm.getQty()
            ));
        }
            if (!tmList.isEmpty()){
                boolean isSaved = orderBo.saveOrder(new OrderDto(
                        lblOrderId.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                        lblCustomerId.getText(),
                        list
                ));

                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Order Succesfully Saved!").show();
                    orderBo.setTmList(null);
                    try {
                        JasperDesign design = JRXmlLoader.load("src/main/resources/reports/orderSummery.jrxml");
                        JasperReport jasperReport = JasperCompileManager.compileReport(design);
                        JRBeanCollectionDataSource customerReport = orderBo.getOrderSummery(lblOrderId.getText());
                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,customerReport);
                        JasperViewer.viewReport(jasperPrint,false);
                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"Error").show();
                }
            }
        }
}
