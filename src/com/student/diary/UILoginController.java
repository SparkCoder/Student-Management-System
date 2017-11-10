/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ABHISHEK D
 */
public class UILoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pane;

    @FXML
    private TextField rollno;
    @FXML
    private PasswordField password;

    private Mouse mouse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mouse = new Mouse();
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                mouse.setX(t.getX());
                mouse.setY(t.getY());
            }
        });
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                pane.getScene().getWindow().setX(t.getScreenX() - mouse.getX() - 14);
                pane.getScene().getWindow().setY(t.getScreenY() - mouse.getY() - 14);
            }
        });
    }

    @FXML
    private void signIn(ActionEvent event) throws IOException {
        DatabaseManager dbms = new DatabaseManager(LogInUser.databasePath);
        Credentials creds = new Credentials().setRollno(rollno.getText()).setPassword(password.getText());

        Student student;
        if ((student = dbms.find(creds)) != null) {
            System.out.println("Logged in: " + student.name);

            LogInUser.student = student;

            Parent main_pane = FXMLLoader.load(getClass().getResource("UI_Main.fxml"));
            Scene scene = new Scene(main_pane);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.setScene(scene);
            appStage.show();
        } else {
            System.out.println("User does not exist..!");

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("User " + creds.getRollno() + " does not exist..!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("login.png").toString()));
            alert.showAndWait();
        }
    }

    @FXML
    private void signUp(ActionEvent event) throws IOException {
        Parent register_pane = FXMLLoader.load(getClass().getResource("UI_Register.fxml"));
        Scene scene = new Scene(register_pane);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

    @FXML
    private void minimizeFn(ActionEvent event) {
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        appStage.setIconified(true);
    }

    @FXML
    private void exitFn() {
        System.exit(1);
    }

}
