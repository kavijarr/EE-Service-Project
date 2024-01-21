package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.RepairBo;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.RepairDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.*;

import java.io.IOException;
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
    public BorderPane pane;
    public Label lblDate;
    public Label lblTime;

    private RepairBo repairBo = BoFactory.getInstance().getBo(BoType.REPAIR);
    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);
    private List<CustomerDto> customers = new ArrayList<>();

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
        showTime();

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
        if (Login.getUser().equals(UserType.STAFF)){
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
            stage.show();
            stage.setTitle("User Dashboard");
            stage.centerOnScreen();
        } else if (Login.getUser().equals(UserType.ADMIN)) {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
            stage.show();
            stage.setTitle("Admin Dashboard");
            stage.centerOnScreen();
        }
    }

    public void NewCustomerBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CreateCustomerForm.fxml"))));
        stage.setTitle("Create Customer");
        stage.centerOnScreen();
        CreateCustomerFormController controller = new CreateCustomerFormController();
        controller.setStage("Service");

        stage.show();
    }

    public void ConformOrderBtnOnAction(ActionEvent actionEvent) {
        RepairDto dto = new RepairDto(
                lblServiceId.getText(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")),
                lblCustomerId.getText(),
                txtItemName.getText(),
                txtDesc.getText(),
                StatusInfo.statusType(StatusType.PENDING)
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
