/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ABHISHEK D
 */
public class Student implements Serializable {

    String name;
    DateF dateOfBirth;
    Credentials credentials;
    int currentSemester;
    ArrayList<Semester> semesters;
    ArrayList<Activity> activities;

    public Student(String name, DateF dateOfBirth, Credentials credentials, int currentSemester, ArrayList<Semester> semesters) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.credentials = credentials;
        this.currentSemester = currentSemester;
        this.semesters = semesters;
        this.activities = new ArrayList<>();
    }
}
