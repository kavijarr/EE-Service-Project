package controller;

import bo.BoFactory;
import bo.custom.CustomerBo;
import com.mysql.cj.xdevapi.SessionImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.BoType;
import util.HibernateUtil;
import util.Login;
import util.UserType;

import java.io.IOException;
import java.sql.Connection;

public class ReportsFormController {
    public BorderPane pane;
    CustomerBo customerBo = BoFactory.getInstance().getBo(BoType.CUSTOMER);


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

    public void CustomerReportBtnOnAction(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/reports/customerReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JRBeanCollectionDataSource customerReport = customerBo.getCustomerReport();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,customerReport);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void OrderReportBtnOnAction(ActionEvent actionEvent) {

    }
}
