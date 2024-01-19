package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tm.UserTm;
import util.BoType;
import util.Login;

import java.io.IOException;
import java.util.List;

public class AdminDashboardController {
    public JFXTreeTableView tblUsers;
    public TreeTableColumn colId;
    public TreeTableColumn colName;
    public TreeTableColumn colEmail;
    public TreeTableColumn colOption;
    public Circle logo;
    public BorderPane pane;

    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
        loadUsers();

        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
    }

    public void BackBtnOnAction(ActionEvent actionEvent) {
    }

    public void ItemCatelogBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemCatelog.fxml"))));
        stage.setTitle("Item Catelog");
        stage.centerOnScreen();
        stage.show();
    }

    public void AddItemBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddItemForm.fxml"))));
        stage.setTitle("Add Item");
        stage.centerOnScreen();
        stage.show();
    }

    public void ServiceBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceServiceOrderForm.fxml"))));
        stage.setTitle("Service");
        stage.centerOnScreen();
        stage.show();
    }

    public void OrdersBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ViewOrdersForm.fxml"))));
        stage.setTitle("Orders");
        stage.centerOnScreen();
        stage.show();
    }

    public void SalesReportBtnOnAction(ActionEvent actionEvent) {
    }

    public void ReportBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ReportsForm.fxml"))));
        stage.setTitle("Reports");
        stage.centerOnScreen();
        stage.show();
    }

    public void NewUserBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserRegistrationForm.fxml"))));
        stage.setTitle("New User");
        stage.centerOnScreen();
        stage.show();
    }

    public void LogOutBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
        Login.clearType();
    }

    private void loadUsers(){
        List<UserDto> all = userBo.getAll();
        ObservableList<UserTm> tmList = FXCollections.observableArrayList();
        for (UserDto dto: all) {

            JFXButton btn = new JFXButton("Remove");
            UserTm tm = new UserTm(
                    dto.getId(),
                    dto.getName(),
                    dto.getEmail(),
                    btn
            );
            btn.setOnAction(actionEvent -> {
                tmList.remove(tm);
                userBo.deleteUser(tm.getId());
            });
            tmList.add(tm);
        }
        TreeItem<UserTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblUsers.setRoot(treeItem);
        tblUsers.setShowRoot(false);
    }
}
