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
public class Exam {

    String title;
    Subject subject;
    float mark;
    float minimum;
    float maximum;
    boolean arrear;
    static int count = 0;

    public Exam(String title, Subject subject, float minimum, float maximum) {
        this.title = title;
        this.subject = subject;
        this.mark = 0;
        this.minimum = minimum;
        this.maximum = maximum;
        this.arrear = false;
        count++;
    }

    void setMarks(float mark) {
        this.mark = mark;
        this.arrear = mark < this.minimum;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "\nSubject: " + this.subject.name + "\nMaximum mark: " + this.maximum + "\nMinimum:" + this.minimum;
    }
}
