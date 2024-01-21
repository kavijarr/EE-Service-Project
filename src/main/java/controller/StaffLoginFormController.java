package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.BoType;
import util.Login;
import util.UserType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StaffLoginFormController {
    public Circle logo;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public BorderPane pane;
    public Label lblDate;
    public Label lblTime;

    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        showTime();
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

    public void StaffLoginBtnOnAction(ActionEvent actionEvent) throws IOException {
        try {
           UserDto user = userBo.getUser(txtEmail.getText());
            if (userBo.checkPassword(txtPassword.getText(),user)){
                Stage stage = (Stage) pane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashboard.fxml"))));
                stage.setTitle("Admin Dashboard");
                stage.centerOnScreen();
                stage.show();
                Login.setUser(UserType.STAFF);
            }else {
                new Alert(Alert.AlertType.ERROR,"Incorrect UserName or Password").show();
            }
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR,"User Not Registered").show();
        }
    }

    public void ForgotPasswordBtnOnAction(ActionEvent actionEvent) throws IOException {
        UserDto user = userBo.getUser(txtEmail.getText());
        UpdatePasswordFormController controller = new UpdatePasswordFormController();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdatePasswordForm.fxml"))));
        stage.setTitle("Update Password");
        stage.centerOnScreen();
        stage.show();
        controller.setData(user);
    }

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"))));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
    }
}
