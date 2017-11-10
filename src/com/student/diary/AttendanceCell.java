/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ABHISHEK D
 */
public class AttendanceCell extends ListCell<String> {

    private Button actionBtn;
    public boolean attended;

    public AttendanceCell() {
        super();

        attended = false;

        setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                //do something                  
            }
        });

        actionBtn = new Button("Subject");
        actionBtn.setMaxWidth(Double.MAX_VALUE);
        actionBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!attended) {
                    LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).findSubject(getItem()).attendance.Attend();
                    actionBtn.setStyle("-fx-background-color: green; -fx-text-fill: white");
                    System.out.println("Attended: " + getItem());
                    attended = true;
                } else {
                    LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).findSubject(getItem()).attendance.Cancel();
                    actionBtn.setStyle("-fx-background-color: red; -fx-text-fill: white");
                    System.out.println("Cancelled: " + getItem());
                    attended = false;
                }
                
                actionBtn.setText(getItem() + " : " + (LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).findSubject(getItem()).attendance.attendance * 100));
            }
        });
        actionBtn.setStyle("-fx-background-color: red; -fx-text-fill: white");
        setText(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setEditable(false);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black");
        if (item != null) {
            actionBtn.setText(item + " : " + (LogInUser.student.semesters.get(LogInUser.student.currentSemester - 1).findSubject(item).attendance.attendance * 100));
            setGraphic(actionBtn);
        } else {
            setGraphic(null);
        }
    }
}
