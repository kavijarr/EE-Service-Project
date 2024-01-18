package controller;

import bo.BoFactory;
import bo.custom.OrderBo;
import bo.custom.RepairBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.OrderDto;
import dto.RepairDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.codehaus.stax2.ri.typed.ValueDecoderFactory;
import tm.OrderTm;
import tm.OrderTypeTm;
import tm.RepairTm;
import util.BoType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewOrdersFormController {
    public Circle logo;
    public BorderPane pane;
    private final ObservableList<String> options = FXCollections.observableArrayList("Orders","Services");
    public ComboBox cmbOption;
    public JFXTreeTableView tblOrders;
    public TreeTableColumn colId;
    public TreeTableColumn colDate;
    public TreeTableColumn colState;
    public TreeTableColumn colOption;

    private OrderBo orderBo = BoFactory.getInstance().getBo(BoType.ORDER);
    private RepairBo repairBo = BoFactory.getInstance().getBo(BoType.REPAIR);

    public void initialize() throws IOException {
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));

        cmbOption.setItems(options);
        cmbOption.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, option) -> {
            switch (option.toString()){
                case "Orders" :
                    try {
                        loadOrders();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Services" : loadServiceOrders();break;
            }
        }));

        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colState.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
        stage.setTitle("User Dashboard");
        stage.centerOnScreen();
        stage.show();
    }

    private void loadOrders() throws IOException {
        List<OrderDto> orders = orderBo.getAll();
        ObservableList<OrderTypeTm> tmList = FXCollections.observableArrayList();
        for (OrderDto dto: orders) {
            JFXButton btn = new JFXButton("Details");
            btn.setOnAction(event -> {
                try {
                    btnActionOrders(dto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            tmList.add(new OrderTypeTm(
                    dto.getId(),
                    dto.getDate(),
                    "pending",
                    btn
            ));
        }
        TreeItem<OrderTypeTm> treeItem = new RecursiveTreeItem<>(tmList,RecursiveTreeObject::getChildren);
        tblOrders.setRoot(treeItem);
        tblOrders.setShowRoot(false);
    }

    private void btnActionOrders(OrderDto dto) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OrderDetailForm.fxml"));
        Parent root = loader.load();
        OrderDetailFormController controller=loader.getController();
        Stage stage = (Stage) pane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.setTitle("Order Details");
        newStage.centerOnScreen();
        newStage.show();
        stage.close();
        controller.setData(dto);
        System.out.println(dto);
    }

    private void loadServiceOrders(){
        List<RepairDto> list = repairBo.getAll();
        ObservableList<RepairTm> tmList = FXCollections.observableArrayList();
        for (RepairDto dto:list) {
            JFXButton btn = new JFXButton("Details");
            btn.setOnAction(actionEvent -> {
                try {
                    btnActionService(dto);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            tmList.add(new RepairTm(
                    dto.getRepairId(),
                    dto.getDate(),
                    "Pending",
                    btn
            ));
        }
        TreeItem<RepairTm> treeItem = new RecursiveTreeItem<>(tmList,RecursiveTreeObject::getChildren);
        tblOrders.setRoot(treeItem);
        tblOrders.setShowRoot(false);
    }

    private void btnActionService(RepairDto dto) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ServiceDetailsForm.fxml"));
        Parent root = loader.load();
        ServiceDetailsFormController controller = loader.getController();
        Stage stage = (Stage) pane.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.setTitle("Service Details");
        newStage.centerOnScreen();
        newStage.show();
        controller.setData(dto);
        stage.close();
    }
}
