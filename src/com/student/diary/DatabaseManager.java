/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author ABHISHEK D
 */
public class DatabaseManager implements Serializable {

    private final String databasePath;

    public DatabaseManager(String databasePath) {
        this.databasePath = databasePath;
    }

    public boolean save(Student student) {
        try {
            File file = new File(this.databasePath + student.credentials.getRollno() + ".db");
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream outData = new ObjectOutputStream(fos);
            outData.writeObject(student);
            outData.close();
            fos.close();
        } catch (IOException ex) {
            System.err.println("Database Input/Output Error: " + ex);
            return false;
        }
        return true;
    }

    public boolean save(Student student, String nRoll) {
        try {
            File file = new File(this.databasePath + student.credentials.getRollno() + ".db");
            student.credentials.setRollno(nRoll);
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            ObjectOutputStream outData = new ObjectOutputStream(fos);
            outData.writeObject(student);
            outData.close();
            file.renameTo(new File(this.databasePath + nRoll + ".db"));
            fos.close();
        } catch (IOException ex) {
            System.err.println("Database Input/Output Error: " + ex);
            return false;
        }
        return true;
    }

    public boolean delete(Student student) {
        File file = new File(this.databasePath + student.credentials.getRollno() + ".db");
        file.delete();
        return true;
    }

    public Student find(Credentials credentials) {
        Student student = null;
        try {
            ObjectInputStream inData = new ObjectInputStream(new FileInputStream(this.databasePath + credentials.getRollno() + ".db"));
            student = null;
            boolean found = false;
            student = ((Student) inData.readObject());
            inData.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Database Empty or Not Found Error: " + ex);
        } catch (IOException ex) {
            System.err.println("Database Input/Output Error: " + ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("Invalid Database Error: " + ex);
        }
        return student;
    }
}
