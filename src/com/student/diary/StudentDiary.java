/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author ABHISHEK D
 */
public class StudentDiary extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("UI_Login.fxml"));

        Scene scene = new Scene(root, Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {  
        LogInUser.databasePath = "DBase_";
        
        /*
        ArrayList<Subject> a = new ArrayList<>();
        a.add(new Subject("OOP"));
        a.add(new Subject("DSA"));
        a.add(new Subject("DC"));
        a.add(new Subject("MATHS"));
        a.add(new Subject("PSYCHOLOGY"));
        
        ArrayList<Semester> s = new ArrayList<>();
        
        s.add(new Semester(new DateF().set(1, 7, 2016), new DateF().set(1, 6, 2017), a, 1));
        s.get(0).subjects = a;
        
        new DatabaseManager(LogInUser.databasePath).save(new Student("Abhishek.D", new DateF().set(31, 01, 1998), new Credentials().setRollno("a").setPassword("a"), 1, s));
        new DatabaseManager(LogInUser.databasePath).save(new Student("Abhishek.D", new DateF().set(31, 01, 1998), new Credentials().setRollno("s").setPassword("s"), 1, s));
        
        System.out.println("H1: " + new DatabaseManager(LogInUser.databasePath).find(new Credentials().setRollno("a").setPassword("a")).name);
        */
        
        launch(args);
    }

}
