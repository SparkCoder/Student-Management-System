/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

/**
 *
 * @author ABHISHEK D
 */
public class LogInUser {

    static Student student;
    static String databasePath;

    public static void saveAll() {
        DatabaseManager dbms = new DatabaseManager(databasePath);
        if (LogInUser.student != null) {
            dbms.save(LogInUser.student);
        }
    }
}
