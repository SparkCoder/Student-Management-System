/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ABHISHEK D
 */
public class UIRegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pane;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField name;
    @FXML
    private TextField rollno;
    @FXML
    private TextField pass1;
    @FXML
    private TextField pass2;
    @FXML
    private Button selectSemButton;

    private Mouse mouse;
    private Student student;
    private ArrayList<Semester> semesters;
    private int currentSemester = -1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        semesters = new ArrayList<>();
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
    private void createSemester(ActionEvent event) {
        Semester sem;
        ArrayList<Subject> subjects = new ArrayList<>();

        // Custom dialog
        Dialog<Semester> dialog = new Dialog<>();
        dialog.setTitle("Create a Semester");
        dialog.setHeaderText("Please Enter the required fields...");
        dialog.setResizable(true);

        // Widgets
        Label label1 = new Label("Start Date: ");
        Label label2 = new Label("End Date: ");
        Label label3 = new Label("Subjects:-");
        Label label4 = new Label("Semester number:-");
        Spinner<Integer> semi = new Spinner<>(1, 100, 1);
        DatePicker sd = new DatePicker();
        DatePicker ed = new DatePicker();
        Button cB = new Button("Add Subject");
        Button rB = new Button("Remove Subject");
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        list.setPrefWidth(100);
        list.setPrefHeight(70);
        list.setItems(items);

        cB.setOnAction((ActionEvent event1) -> {
            TextInputDialog d = new TextInputDialog();
            d.setTitle("Create a Subject");
            d.setHeaderText(null);
            d.setContentText("Enter subject name:");

            // Traditional way to get the response value.
            Optional<String> r = d.showAndWait();
            if (r.isPresent()) {
                items.add(r.get());
            }
        });

        rB.setOnAction((ActionEvent event1) -> {
            if (!list.getSelectionModel().getSelectedItem().isEmpty()) {
                items.remove(list.getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No Subject Selected");
                alert.showAndWait();
            }
        });

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(label1, 1, 1); // col=1, row=1
        grid.add(sd, 2, 1);
        grid.add(label2, 1, 2); // col=1, row=2
        grid.add(ed, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(list, 1, 4, 2, 1);
        grid.add(cB, 1, 5);
        grid.add(rB, 2, 5);
        grid.add(label4, 1, 6);
        grid.add(semi, 2, 6);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, Semester>() {
            @Override
            public Semester call(ButtonType b) {
                if (b == buttonTypeOk) {
                    if (sd.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter Starting Date");
                        alert.showAndWait();
                        createSemester(event);
                    } else if (ed.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter End Date");
                        alert.showAndWait();
                        createSemester(event);
                    } else if (items.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please create atleast one subject");
                        alert.showAndWait();
                        createSemester(event);
                    } else {
                        for (int i = 0; i < items.size(); i++) {
                            subjects.add(new Subject(items.get(i)));
                        }
                    }
                    return new Semester(new DateF().set(sd.getValue().getDayOfMonth(), sd.getValue().getMonthValue(), sd.getValue().getYear()), new DateF().set(ed.getValue().getDayOfMonth(), ed.getValue().getMonthValue(), ed.getValue().getYear()), subjects, semi.getValue());
                }
                return null;
            }
        });

        // Show dialog
        Optional<Semester> result = dialog.showAndWait();

        if (result.isPresent()) {
            sem = result.get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Created Semester");
            alert.setHeaderText(null);
            alert.setContentText("Is this correct?\n" + sem.toString());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                semesters.add(sem);
            } else {
                createSemester(event);
            }
        }
    }

    @FXML
    private void selectSemester(ActionEvent event) {
        Semester sem;
        // Custom dialog
        Dialog<Semester> dialog = new Dialog<>();
        dialog.setTitle("Choose a Semester");
        dialog.setHeaderText("Please choose a Semester...");
        dialog.setResizable(true);

        // Widgets
        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        list.setPrefWidth(100);
        list.setPrefHeight(70);

        for (int i = 0; i < semesters.size(); i++) {
            items.add(Integer.toString(i + 1));
        }

        list.setItems(items);
        dialog.getDialogPane().setContent(list);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, Semester>() {
            @Override
            public Semester call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return semesters.get(list.getSelectionModel().getSelectedIndex());
                }
                return null;
            }
        });

        // Show dialog
        Optional<Semester> result = dialog.showAndWait();

        if (result.isPresent()) {
            sem = result.get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Set Semester");
            alert.setHeaderText(null);
            alert.setContentText("Set Semester..?\n" + sem.toString());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                this.currentSemester = this.semesters.indexOf(sem) + 1;
                this.selectSemButton.setText("Semester: " + this.currentSemester);
            } else {
                selectSemester(event);
            }
        }
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        if ((dob.getValue() == null) || (rollno.getText().isEmpty()) || (name.getText().isEmpty()) || (pass1.getText().isEmpty()) || (pass2.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
        } else if (this.semesters.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Create atleast one Semester");
            alert.showAndWait();
        } else if (currentSemester == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please Choose a Semester");
            alert.showAndWait();
        } else {
            if (pass1.getText().equals(pass2.getText())) {
                student = new Student(name.getText(), new DateF().set(dob.getValue().getDayOfMonth(), dob.getValue().getMonthValue(), dob.getValue().getYear()), new Credentials().setRollno(rollno.getText()).setPassword(pass1.getText()), currentSemester, semesters);
                DatabaseManager dbms = new DatabaseManager(LogInUser.databasePath);

                if (dbms.find(student.credentials) == null) {
                    dbms.save(student);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Registered");
                    alert.setHeaderText(null);
                    alert.setContentText("User " + student.name + " Registered");
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(this.getClass().getResource("login.png").toString()));
                    alert.showAndWait();

                    Parent login_pane = FXMLLoader.load(getClass().getResource("UI_Login.fxml"));
                    Scene scene = new Scene(login_pane);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(scene);
                    appStage.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("User already exists");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Passwords Don't Match");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Parent login_pane = FXMLLoader.load(getClass().getResource("UI_Login.fxml"));
        Scene scene = new Scene(login_pane);
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
