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
public class Semester implements Serializable {

    DateF startD, endD;
    ArrayList<Subject> subjects;
    int sem;

    public Semester(DateF startD, DateF endD, ArrayList<Subject> subjects, int sem) {
        this.startD = startD;
        this.endD = endD;
        this.subjects = subjects;
        this.sem = sem;
    }

    public Subject findSubject(String subject) {
        for (int i = 0; i < this.subjects.size(); i++) {
            if (this.subjects.get(i).name.contentEquals(subject)) {
                return this.subjects.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String subs = "";
        for (int i = 0; i < this.subjects.size(); i++) {
            subs += "\n" + this.subjects.get(i);
        }
        return "Semester: " + this.sem + "\nFrom: " + this.startD.toString() + "\nTo: " + this.endD.toString() + "\nSubjects:" + subs;
    }
}
