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
    static boolean changed;

    public static void saveAll() {
        DatabaseManager dbms = new DatabaseManager(databasePath);
        if (LogInUser.student != null) {
            dbms.save(LogInUser.student);
        }
    }

    public static void saveAll(String nRoll) {
        DatabaseManager dbms = new DatabaseManager(databasePath);
        if (LogInUser.student != null) {
            dbms.save(LogInUser.student, nRoll);
        }
    }

    public static void delete() {
        DatabaseManager dbms = new DatabaseManager(databasePath);
        if (LogInUser.student != null) {
            dbms.delete(LogInUser.student);
            LogInUser.student = null;
        }
    }
}
