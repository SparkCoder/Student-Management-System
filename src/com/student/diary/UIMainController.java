/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
public class UIMainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane pane;
    @FXML
    private Label title;
    @FXML
    private ListView subs;

    private Mouse mouse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        title.setText("Welcome " + LogInUser.student.name);

        ObservableList<String> items = FXCollections.observableArrayList();
        for (int i = 0; i < LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.size(); i++) {
            items.add(LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).name);
        }
        subs.setItems(items);
        subs.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> list) {
                return new AttendanceCell();
            }
        });

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
                        return new Semester(new DateF().set(sd.getValue().getDayOfMonth(), sd.getValue().getMonthValue(), sd.getValue().getYear()), new DateF().set(ed.getValue().getDayOfMonth(), ed.getValue().getMonthValue(), ed.getValue().getYear()), subjects, semi.getValue());
                    }
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
                LogInUser.student.semesters.add(sem);
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

        for (int i = 0; i < LogInUser.student.semesters.size(); i++) {
            items.add(Integer.toString(LogInUser.student.semesters.get(i).sem));
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
                    return LogInUser.student.semesters.get(list.getSelectionModel().getSelectedIndex());
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
                LogInUser.student.currentSemester = LogInUser.student.semesters.indexOf(sem) + 1;
            } else {
                selectSemester(event);
            }
        }
    }

    private Activity generateActivity(ActionEvent event, TextField nameF, DatePicker sd, Spinner<Integer> sth, Spinner<Integer> stm, DatePicker ed, Spinner<Integer> eth, Spinner<Integer> etm, ComboBox typeA) {
        Activity activity = null;
        GridPane grid = new GridPane();
        ButtonType buttonTypeOk;
        switch (typeA.getSelectionModel().getSelectedIndex()) {
            case 0:
                // Custom dialog
                Dialog<Workshop> dialog = new Dialog<>();
                dialog.setTitle("Create a Workshop");
                dialog.setHeaderText("Please Enter the required fields...");
                dialog.setResizable(true);

                // Widgets
                Label label1 = new Label("Topic: ");
                Label label2 = new Label("Host/Organisation: ");
                TextField topic = new TextField();
                TextField host = new TextField();
                CheckBox hadPrize = new CheckBox("Had Prize?: ");

                // Create layout and add to dialog
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 35, 20, 35));
                grid.add(label1, 1, 1);
                grid.add(topic, 2, 1);
                grid.add(label2, 1, 2);
                grid.add(host, 2, 2);
                grid.add(hadPrize, 1, 3, 2, 1);
                dialog.getDialogPane().setContent(grid);

                // Add button to dialog
                buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

                // Result converter for dialog
                dialog.setResultConverter(new Callback<ButtonType, Workshop>() {
                    @Override
                    public Workshop call(ButtonType b) {
                        if (b == buttonTypeOk) {
                            if (topic.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter Topic");
                                alert.showAndWait();
                                generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                            } else if (host.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter Host");
                                alert.showAndWait();
                                generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                            } else {
                                return new Workshop(nameF.getText(), new DateF().set(sd.getValue().getDayOfMonth(), sd.getValue().getMonthValue(), sd.getValue().getYear()), new DateF().set(ed.getValue().getDayOfMonth(), ed.getValue().getMonthValue(), ed.getValue().getYear()), new TimeF().set(stm.getValue(), sth.getValue()), new TimeF().set(etm.getValue(), eth.getValue()), topic.getText(), host.getText(), hadPrize.isSelected());
                            }
                        }
                        return null;
                    }
                });

                // Show dialog
                Optional<Workshop> result = dialog.showAndWait();

                if (result.isPresent()) {
                    activity = result.get();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Created Workshop");
                    alert.setHeaderText(null);
                    alert.setContentText("Is this correct?\n" + activity.toString());
                    Optional<ButtonType> res = alert.showAndWait();

                    if (res.get() != ButtonType.OK) {
                        generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                    }
                }
                break;
            case 1:
                // Custom dialog
                Dialog<Competition> dialog2 = new Dialog<>();
                dialog2.setTitle("Create a Competition");
                dialog2.setHeaderText("Please Enter the required fields...");
                dialog2.setResizable(true);

                // Widgets
                Label label_topic = new Label("Topic: ");
                Label label_Type = new Label("Type: ");
                TextField topicT = new TextField();
                TextField typeT = new TextField();
                CheckBox prize = new CheckBox("Had Prize?: ");

                // Create layout and add to dialog
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 35, 20, 35));
                grid.add(label_topic, 1, 1);
                grid.add(topicT, 2, 1);
                grid.add(label_Type, 1, 2);
                grid.add(typeT, 2, 2);
                grid.add(prize, 1, 3, 2, 1);
                dialog2.getDialogPane().setContent(grid);

                // Add button to dialog2
                buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                dialog2.getDialogPane().getButtonTypes().add(buttonTypeOk);

                // Result converter for dialog
                dialog2.setResultConverter(new Callback<ButtonType, Competition>() {
                    @Override
                    public Competition call(ButtonType b) {
                        if (b == buttonTypeOk) {
                            if (topicT.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter Topic");
                                alert.showAndWait();
                                generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                            } else if (typeT.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter Type");
                                alert.showAndWait();
                                generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                            } else {
                                return new Competition(nameF.getText(), new DateF().set(sd.getValue().getDayOfMonth(), sd.getValue().getMonthValue(), sd.getValue().getYear()), new DateF().set(ed.getValue().getDayOfMonth(), ed.getValue().getMonthValue(), ed.getValue().getYear()), new TimeF().set(stm.getValue(), sth.getValue()), new TimeF().set(etm.getValue(), eth.getValue()), topicT.getText(), typeT.getText(), prize.isSelected());
                            }
                        }
                        return null;
                    }
                });

                // Show dialog2
                Optional<Competition> resultC = dialog2.showAndWait();

                if (resultC.isPresent()) {
                    activity = resultC.get();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Created Competition");
                    alert.setHeaderText(null);
                    alert.setContentText("Is this correct?\n" + activity.toString());
                    Optional<ButtonType> res = alert.showAndWait();

                    if (res.get() != ButtonType.OK) {
                        generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                    }
                }
                break;
            default:
                // Custom dialog
                Dialog<Other> dialog3 = new Dialog<>();
                dialog3.setTitle("Create some other Activity");
                dialog3.setHeaderText("Please Enter the required fields...");
                dialog3.setResizable(true);

                // Widgets
                Label labeltopic = new Label("Topic: ");
                Label labelType = new Label("Type: ");
                TextField topic_T = new TextField();
                TextField type_T = new TextField();
                CheckBox Prize = new CheckBox("Had Prize?: ");

                // Create layout and add to dialog
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 35, 20, 35));
                grid.add(labeltopic, 1, 1);
                grid.add(topic_T, 2, 1);
                grid.add(labelType, 1, 2);
                grid.add(type_T, 2, 2);
                grid.add(Prize, 1, 3, 2, 1);
                dialog3.getDialogPane().setContent(grid);

                // Add button to dialog2
                buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                dialog3.getDialogPane().getButtonTypes().add(buttonTypeOk);

                // Result converter for dialog
                dialog3.setResultConverter(new Callback<ButtonType, Other>() {
                    @Override
                    public Other call(ButtonType b) {
                        if (b == buttonTypeOk) {
                            if (topic_T.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter Topic");
                                alert.showAndWait();
                                generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                            } else if (type_T.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Please enter Type");
                                alert.showAndWait();
                                generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                            } else {
                                return new Other(nameF.getText(), new DateF().set(sd.getValue().getDayOfMonth(), sd.getValue().getMonthValue(), sd.getValue().getYear()), new DateF().set(ed.getValue().getDayOfMonth(), ed.getValue().getMonthValue(), ed.getValue().getYear()), new TimeF().set(stm.getValue(), sth.getValue()), new TimeF().set(etm.getValue(), eth.getValue()), topic_T.getText(), type_T.getText(), Prize.isSelected());
                            }
                        }
                        return null;
                    }
                });

                // Show dialog2
                Optional<Other> resultO = dialog3.showAndWait();

                if (resultO.isPresent()) {
                    activity = resultO.get();

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Created Activity");
                    alert.setHeaderText(null);
                    alert.setContentText("Is this correct?\n" + activity.toString());
                    Optional<ButtonType> res = alert.showAndWait();

                    if (res.get() != ButtonType.OK) {
                        generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                    }
                }
                break;
        }
        return activity;
    }

    @FXML
    private void createActivity(ActionEvent event) {
        Activity act;

        // Custom dialog
        Dialog<Activity> dialog = new Dialog<>();
        dialog.setTitle("Create an Activity");
        dialog.setHeaderText("Please Enter the required fields...");
        dialog.setResizable(true);

        // Widgets
        Label label1 = new Label("Name: ");
        Label label2 = new Label("Start Date: ");
        Label label3 = new Label("Start Time:- ");
        Label label4 = new Label("Hours: ");
        Label label5 = new Label("Minutes: ");
        Label label6 = new Label("End Date: ");
        Label label7 = new Label("End TIme:- ");
        Label label8 = new Label("Hours: ");
        Label label9 = new Label("Minutes: ");
        Label label10 = new Label("Type: ");
        DatePicker sd = new DatePicker();
        DatePicker ed = new DatePicker();
        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Workshop",
                        "Competition",
                        "Other"
                );
        ComboBox typeA = new ComboBox(options);
        Spinner<Integer> sth = new Spinner<>(0, 23, 0);
        Spinner<Integer> stm = new Spinner<>(0, 59, 0);
        Spinner<Integer> eth = new Spinner<>(0, 23, 0);
        Spinner<Integer> etm = new Spinner<>(0, 59, 0);
        TextField nameF = new TextField();

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(label1, 1, 1); // col=1, row=1
        grid.add(nameF, 2, 1);
        grid.add(label2, 1, 2); // col=1, row=2
        grid.add(sd, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(label4, 1, 4);
        grid.add(label5, 2, 4);
        grid.add(sth, 1, 5);
        grid.add(stm, 2, 5);
        grid.add(label6, 1, 6); // col=1, row=2
        grid.add(ed, 2, 6);
        grid.add(label7, 1, 7);
        grid.add(label8, 1, 8);
        grid.add(label9, 2, 8);
        grid.add(eth, 1, 9);
        grid.add(etm, 2, 9);
        grid.add(label10, 1, 10);
        grid.add(typeA, 2, 10);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, Activity>() {
            @Override
            public Activity call(ButtonType b) {
                if (b == buttonTypeOk) {
                    if (nameF.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter Name");
                        alert.showAndWait();
                        createActivity(event);
                    } else if (sd.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter Starting Date");
                        alert.showAndWait();
                        createActivity(event);
                    } else if (ed.getValue() == null) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter End Date");
                        alert.showAndWait();
                        createActivity(event);
                    } else {
                        return generateActivity(event, nameF, sd, sth, stm, ed, eth, etm, typeA);
                    }
                }
                return null;
            }
        });

        // Show dialog
        Optional<Activity> result = dialog.showAndWait();

        if (result.isPresent()) {
            act = result.get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Created Activity");
            alert.setHeaderText(null);
            alert.setContentText("Is this correct?\n" + act.toString());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                LogInUser.student.activities.add(act);
            } else {
                createActivity(event);
            }
        }
    }

    @FXML
    private void editActivities(ActionEvent event) {
        Activity act;
        // Custom dialog
        Dialog<Activity> dialog = new Dialog<>();
        dialog.setTitle("Choose an Activity");
        dialog.setHeaderText("Please choose an Activity...");
        dialog.setResizable(true);

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        list.setPrefWidth(100);
        list.setPrefHeight(70);

        for (int i = 0; i < LogInUser.student.activities.size(); i++) {
            items.add(LogInUser.student.activities.get(i).toString());
        }

        list.setItems(items);
        dialog.getDialogPane().setContent(list);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, Activity>() {
            @Override
            public Activity call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return LogInUser.student.activities.get(list.getSelectionModel().getSelectedIndex());
                }
                return null;
            }
        });

        // Show dialog
        Optional<Activity> result = dialog.showAndWait();

        if (result.isPresent()) {
            act = result.get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Activity");
            alert.setHeaderText(null);
            alert.setContentText("Edit Activity..?\n" + act.toString());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                // Custom dialog
                Dialog<String> dialogO = new Dialog<>();
                dialogO.setTitle("Choose an Option");
                dialogO.setHeaderText("Please choose an Activity...");
                dialogO.setResizable(true);

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 35, 20, 35));

                Button deleteButton = new Button("Delete");

                deleteButton.setOnAction((ActionEvent event1) -> {
                    Alert alertC = new Alert(Alert.AlertType.CONFIRMATION);
                    alertC.setTitle("Delete Activity");
                    alertC.setHeaderText(null);
                    alertC.setContentText("Delete?\n" + act.toString());
                    Optional<ButtonType> resC = alert.showAndWait();

                    if (resC.get() == ButtonType.OK) {
                        LogInUser.student.activities.remove(act);
                    } else {
                        editActivities(event);
                    }
                });
                grid.add(deleteButton, 1, 1);

                if (act.hadPrize) {
                    Button prizeButton = new Button("Set Prize");

                    prizeButton.setOnAction((ActionEvent event1) -> {
                        TextInputDialog d = new TextInputDialog();
                        d.setTitle("Set Prize");
                        d.setHeaderText("Set Prize...\neg:- 1 for 1st, 2 for 2nd etc...");
                        d.setContentText("Prize: ");

                        // Traditional way to get the response value.
                        Optional<String> r = d.showAndWait();
                        if (r.isPresent()) {
                            if (r.get().isEmpty()) {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                                alert2.setTitle("Error");
                                alert2.setHeaderText(null);
                                alert2.setContentText("Please enter Place");
                                alert2.showAndWait();
                            } else {
                                act.place = Integer.parseInt(r.get());
                            }
                            editActivities(event);
                        }
                    });
                    grid.add(prizeButton, 1, 2);
                }

                dialog.getDialogPane().setContent(grid);

                // Add button to dialog
                ButtonType buttonTypeCancelO = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialogO.getDialogPane().getButtonTypes().add(buttonTypeCancelO);

                // Result converter for dialog
                dialogO.setResultConverter(new Callback<ButtonType, String>() {
                    @Override
                    public String call(ButtonType b) {
                        return null;
                    }
                });

                // Show dialog
                Optional<String> resultO = dialogO.showAndWait();
            } else {
                return;
            }
        }
    }

    @FXML
    private void createExam(ActionEvent event) {
        Exam exam;

        // Custom dialog
        Dialog<Exam> dialog = new Dialog<>();
        dialog.setTitle("Create an Activity");
        dialog.setHeaderText("Please Enter the required fields...");
        dialog.setResizable(true);

        // Widgets
        Label label1 = new Label("Title: ");
        Label label2 = new Label("Subject: ");
        Label label3 = new Label("Minimum Marks:- ");
        Label label4 = new Label("Maximum Marks: ");

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        list.setPrefWidth(100);
        list.setPrefHeight(70);

        for (int i = 0; i < LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.size(); i++) {
            items.add(LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).name);
        }

        list.getSelectionModel().select(0);

        TextField title = new TextField();
        Spinner<Integer> min = new Spinner<>(0, 10000, 0);
        Spinner<Integer> max = new Spinner<>(1, 10000, 0);

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(label1, 1, 1);
        grid.add(title, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(list, 1, 3, 2, 1);
        grid.add(label3, 1, 4);
        grid.add(min, 2, 4);
        grid.add(label4, 1, 5);
        grid.add(max, 2, 5);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, Exam>() {
            @Override
            public Exam call(ButtonType b) {
                if (b == buttonTypeOk) {
                    if (title.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter Name");
                        alert.showAndWait();
                        createExam(event);
                    } else {
                        return new Exam(title.getText(), LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).findSubject(list.getSelectionModel().getSelectedItem()), min.getValue(), max.getValue());
                    }
                }
                return null;
            }
        });

        // Show dialog
        Optional<Exam> result = dialog.showAndWait();

        if (result.isPresent()) {
            exam = result.get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Created Exam");
            alert.setHeaderText(null);
            alert.setContentText("Is this correct?\n" + exam.toString());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                exam.subject.exams.add(exam);
            } else {
                createExam(event);
            }
        }
    }

    @FXML
    private void editExam(ActionEvent event) {
        Exam exam;
        // Custom dialog
        Dialog<Exam> dialog = new Dialog<>();
        dialog.setTitle("Choose an Exam");
        dialog.setHeaderText("Please choose an Exam...");
        dialog.setResizable(true);

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList();
        list.setPrefWidth(100);
        list.setPrefHeight(70);

        int pos[][] = new int[LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.size() * Exam.count][2];

        for (int i = 0; i < LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.size(); i++) {
            for (int j = 0; j < LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).exams.size(); j++) {
                String l = LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).exams.get(j) + " (" + LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i) + ")";
                if (LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).exams.get(j).arrear) {
                    l += " - Arrear";
                }
                pos[i * Exam.count + j][0] = i;
                pos[i * Exam.count + j][1] = j;
                items.add(l);
            }
        }

        list.setItems(items);
        dialog.getDialogPane().setContent(list);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, Exam>() {
            @Override
            public Exam call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(pos[list.getSelectionModel().getSelectedIndex()][0]).exams.get(pos[list.getSelectionModel().getSelectedIndex()][1]);
                }
                return null;
            }
        });

        // Show dialog
        Optional<Exam> result = dialog.showAndWait();

        if (result.isPresent()) {
            exam = result.get();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edit Exam");
            alert.setHeaderText(null);
            alert.setContentText("Edit Exam..?\n" + exam.toString());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                // Custom dialog
                Dialog<String> dialogO = new Dialog<>();
                dialogO.setTitle("Choose an Option");
                dialogO.setHeaderText(null);
                dialogO.setResizable(true);

                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 35, 20, 35));

                Button deleteButton = new Button("Delete");

                deleteButton.setOnAction((ActionEvent event1) -> {
                    Alert alertC = new Alert(Alert.AlertType.CONFIRMATION);
                    alertC.setTitle("Delete Activity");
                    alertC.setHeaderText(null);
                    alertC.setContentText("Delete?\n" + exam.title);
                    Optional<ButtonType> resC = alert.showAndWait();

                    if (resC.get() == ButtonType.OK) {
                        exam.subject.exams.remove(exam);
                        exam.subject = null;
                    } else {
                        editExam(event);
                    }
                });

                Button prizeButton = new Button("Set Mark");

                prizeButton.setOnAction((ActionEvent event1) -> {
                    TextInputDialog d = new TextInputDialog();
                    d.setTitle("Set Mark");
                    d.setHeaderText(null);
                    d.setContentText("Mark: ");

                    // Traditional way to get the response value.
                    Optional<String> r = d.showAndWait();
                    if (r.isPresent()) {
                        if (r.get().isEmpty()) {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Error");
                            alert2.setHeaderText(null);
                            alert2.setContentText("Please enter Prize");
                            alert2.showAndWait();
                            createActivity(event);
                        } else {
                            exam.mark = Integer.parseInt(r.get());
                            editExam(event);
                        }
                    }
                });

                grid.add(deleteButton, 1, 1);
                grid.add(prizeButton, 1, 2);

                dialog.getDialogPane().setContent(grid);

                // Add button to dialog
                ButtonType buttonTypeCancelO = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialogO.getDialogPane().getButtonTypes().add(buttonTypeCancelO);

                // Result converter for dialog
                dialogO.setResultConverter(new Callback<ButtonType, String>() {
                    @Override
                    public String call(ButtonType b) {
                        return null;
                    }
                });

                // Show dialog
                Optional<String> resultO = dialogO.showAndWait();
            } else {
                return;
            }
        }
    }

    @FXML
    private void AccSettings(ActionEvent event) {
        // Custom dialog
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Account Settings");
        dialog.setHeaderText(null);
        dialog.setResizable(true);

        // Widgets
        Label label1 = new Label("Name: ");
        Label label2 = new Label("Date Of Birth: ");
        Label label3 = new Label("Current Semester:- ");
        Label label4 = new Label("Roll no: ");

        TextField name = new TextField();
        DatePicker dob = new DatePicker();
        TextField rollno = new TextField();
        TextField cus = new TextField();
        cus.setEditable(false);

        Button semButton = new Button("View/Set Semesters");

        semButton.setOnAction((ActionEvent event1) -> {
            selectSemester(event);
        });

        Button actButton = new Button("View/Edit Activities");

        actButton.setOnAction((ActionEvent event1) -> {
            editActivities(event);
        });

        Button passButton = new Button("Change Password");

        passButton.setOnAction((ActionEvent event1) -> {
            // Custom dialog
            Dialog<String> dialogp = new Dialog<>();
            dialogp.setTitle("Set new Password");
            dialogp.setHeaderText("Please Enter the required fields...");
            dialogp.setResizable(true);

            // Widgets
            Label labelp1 = new Label("Old Password  : ");
            Label labelp2 = new Label("New Password  : ");
            Label labelp3 = new Label("password again:- ");

            PasswordField oPass = new PasswordField();
            PasswordField pass1 = new PasswordField();
            PasswordField pass2 = new PasswordField();

            // Create layout and add to dialog
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 35, 20, 35));
            grid.add(labelp1, 1, 1);
            grid.add(oPass, 2, 1);
            grid.add(labelp2, 1, 2);
            grid.add(pass1, 2, 2);
            grid.add(labelp3, 1, 3);
            grid.add(pass2, 2, 3);
            dialogp.getDialogPane().setContent(grid);

            // Add button to dialog
            ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            dialogp.getDialogPane().getButtonTypes().add(buttonTypeOk);

            // Result converter for dialog
            dialogp.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    if (b == buttonTypeOk) {
                        if (oPass.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter Old Password");
                            alert.showAndWait();
                        } else if (pass1.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter New Password");
                            alert.showAndWait();
                        } else if (pass2.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter New Password again");
                            alert.showAndWait();
                        } else if (!oPass.getText().equals(LogInUser.student.credentials.getPassword())) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Old Password is incorrect");
                            alert.showAndWait();
                        } else if (!pass2.getText().equals(pass2.getText())) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("New Password don't match");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Change Password");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to change your password?");
                            Optional<ButtonType> res = alert.showAndWait();

                            if (res.get() == ButtonType.OK) {
                                LogInUser.student.credentials.setPassword(pass1.getText());
                                LogInUser.saveAll();
                            }
                        }
                    }
                    return null;
                }
            });

            // Show dialog
            dialogp.showAndWait();
        });

        Button deleteButton = new Button("Delete Account");

        deleteButton.setOnAction((ActionEvent event1) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Your Account");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete your account?\nThis process is irreversible..!");
            Optional<ButtonType> res = alert.showAndWait();

            if (res.get() == ButtonType.OK) {
                try {
                    LogInUser.delete();

                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Deleted ");
                    alert2.setHeaderText(null);
                    alert2.setContentText("User Account " + LogInUser.student.name + " Deleted");
                    alert2.showAndWait();

                    Parent login_pane = FXMLLoader.load(getClass().getResource("UI_Login.fxml"));
                    Scene scene = new Scene(login_pane);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(scene);
                    appStage.show();
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        });

        //Set Initial Values
        name.setText(LogInUser.student.name);
        dob.setValue(LocalDate.of(LogInUser.student.dateOfBirth.getYear(), LogInUser.student.dateOfBirth.getMonth(), LogInUser.student.dateOfBirth.getDate()));
        rollno.setText(LogInUser.student.credentials.getRollno());
        cus.setText(Integer.toString(LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).sem));

        name.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                LogInUser.changed = true;
            }
        });

        dob.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                LogInUser.changed = true;
            }
        });

        rollno.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                LogInUser.changed = true;
            }
        });

        cus.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                LogInUser.changed = true;
            }
        });

        LogInUser.changed = false;

        // Create layout and add to dialog
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 35, 20, 35));
        grid.add(label1, 1, 1);
        grid.add(name, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(dob, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(cus, 2, 3);
        grid.add(label4, 1, 4);
        grid.add(rollno, 2, 4);
        grid.add(semButton, 1, 5);
        grid.add(actButton, 2, 5);
        grid.add(passButton, 1, 6);
        grid.add(deleteButton, 2, 6);
        dialog.getDialogPane().setContent(grid);

        // Add button to dialog
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        // Result converter for dialog
        dialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {
                if (b == buttonTypeOk) {
                    if (LogInUser.changed) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Save Changes");
                        alert.setHeaderText(null);
                        alert.setContentText("Is this correct?" + "\nName: " + name.getText() + "\nDate of Birth: " + dob.getValue().toString() + "\nRollno: " + rollno.getText());
                        Optional<ButtonType> res = alert.showAndWait();

                        if (res.get() == ButtonType.OK) {
                            if (!rollno.getText().equals(LogInUser.student.credentials.getRollno())) {
                                LogInUser.saveAll(rollno.getText());
                            } else {

                                if (!name.getText().equals(LogInUser.student.name)) {
                                    LogInUser.student.name = name.getText();
                                }
                                if ((dob.getValue().getDayOfMonth() != LogInUser.student.dateOfBirth.getDate()) || (dob.getValue().getMonthValue() != LogInUser.student.dateOfBirth.getMonth()) || (dob.getValue().getYear() != LogInUser.student.dateOfBirth.getYear())) {
                                    LogInUser.student.dateOfBirth.set(dob.getValue().getDayOfMonth(), dob.getValue().getMonthValue(), dob.getValue().getYear());
                                }
                                LogInUser.saveAll();
                            }
                        } else {
                            AccSettings(event);
                        }
                        LogInUser.changed = false;
                    }
                }
                return null;
            }
        });

        // Show dialog
        dialog.showAndWait();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        for (int i = 0; i < LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.size(); i++) {
            if (!LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).attendance.attended) {
                LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).attendance.Miss();
            } else {
                LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).subjects.get(i).attendance.attended = false;
            }
        }

        LogInUser.saveAll();
        System.out.println("Logged out: " + LogInUser.student.name);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logged out");
        alert.setHeaderText(null);
        alert.setContentText("User " + LogInUser.student.name + " Logged out");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("login.png").toString()));
        alert.showAndWait();

        LogInUser.student = null;

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
        LogInUser.saveAll();
        System.exit(1);
    }

}
