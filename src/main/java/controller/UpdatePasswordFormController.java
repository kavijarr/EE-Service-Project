package controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXTextField;
import dto.UserDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.BoType;
import util.EmailSender;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class UpdatePasswordFormController {
    public Circle logo;
    public JFXTextField txtOtp;
    public JFXTextField txtNewPw;
    private static UserDto user;
    public BorderPane pane;
    public Label lblDate;
    public Label lblTime;
    private EmailSender emailSender = new EmailSender();
    private String otp;
    UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void initialize(){
        Image logoImg = new Image("/img/E&E Logo.png");
        logo.setFill(new ImagePattern(logoImg));
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

    public void BackBtnOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/StaffLoginForm.fxml"))));
        stage.setTitle("Staff Login");
        stage.centerOnScreen();
        stage.show();
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        if (txtOtp.getText().equals(otp)){
            Boolean isUpdated = userBo.updatePassword(txtNewPw.getText(), user);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Password Updated successfully").show();
            } else{
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Incorrect OTP").show();
        }
    }

    public void setData(UserDto data){
        this.user=data;
    }

    public void RequestOtpBtnOnAction(ActionEvent actionEvent) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<6;i++){
            sb.append(random.nextInt(10));
        }
        otp=sb.toString();
        Boolean isSent = emailSender.sendOtp(user.getEmail(), otp);
        if (isSent){
            new Alert(Alert.AlertType.INFORMATION,"An Email containing the OTP has been sent to the email").show();
        }
    }
}
