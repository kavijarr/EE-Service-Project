package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.ItemBo;
import bo.custom.OrderDetailsBo;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import tm.OrderDetailsTm;
import util.BoType;

import java.util.List;

public class OrderDetailFormController {
    public Label lblName;
    public Label lblContactNumber;
    public ComboBox cmbStatus;
    public JFXTreeTableView tblItems;
    public TreeTableColumn colId;
    public TreeTableColumn colName;
    public TreeTableColumn colQty;
    public TreeTableColumn colAmount;
    private OrderDto orderDto;
    public Circle logo;
    private OrderDetailsBo orderDetailsBo = BoFactory.getInstance().getBo(BoType.ORDERDETAILS);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);

    public void setData(OrderDto data){
        this.orderDto=data;
        Platform.runLater(() -> {
            loadCustomer();
            loadItemTable();
        });
    }

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) {

    }

    private void loadCustomer(){
        CustomerDto customer = customerBo.getCustomer(orderDto.getCustomerId());
        lblName.setText(customer.getCustomerName());
        lblContactNumber.setText(customer.getContactNumber());
    }

    private void loadItemTable(){
        List<OrderDetailsDto> list = orderDto.getList();
        ObservableList<OrderDetailsTm> tmList = FXCollections.observableArrayList();
        for (OrderDetailsDto dto:list) {
            tmList.add(new OrderDetailsTm(
                    dto.getItemId(),
                    itemBo.getItem(dto.getItemId()).getName(),
                    dto.getQty(),
                    dto.getQty()*dto.getUnitPrice()
            ));
        }
        TreeItem<OrderDetailsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblItems.setRoot(treeItem);
        tblItems.setShowRoot(false);
    }
}
