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
public class Subject implements Serializable {

    String name;
    float CGPA;
    int arrear;
    Attendance attendance;
    ArrayList<Exam> exams;
    ArrayList<Exam> arrearExams;

    public Subject(String name) {
        this.name = name;
        this.CGPA = 0;
        this.arrear = 0;
        this.exams = new ArrayList<>();
        this.attendance = new Attendance();
    }

    public Exam createExam(Exam exam) {
        this.exams.add(exam);
        return exam;
    }

    public Subject calculateCGPA() {
        CGPA = 0;
        int count = 0;
        for (int i = 0; i < this.exams.size(); i++) {
            if (!this.exams.get(i).arrear) {
                CGPA += (this.exams.get(i).mark / this.exams.get(i).maximum) * 10;
                count++;
            }
        }
        if (count != 0) {
            CGPA /= count;
        }
        return this;
    }

    public Subject calculateArrears() {
        this.arrear = 0;
        this.exams.stream().filter((exam) -> (exam.arrear)).forEachOrdered((Exam aExam) -> {
            this.arrearExams.add(aExam);
            aExam.arrear = true;
            this.arrear++;
        });
        return this;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
