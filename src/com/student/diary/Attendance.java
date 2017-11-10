/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.student.diary;

import java.io.Serializable;

/**
 *
 * @author ABHISHEK D
 */
public class Attendance implements Serializable {

    float attendance;
    float classesAttended;
    float totalClasses;
    boolean attended;

    public Attendance() {
        this.attendance = this.classesAttended = this.totalClasses = 0;
    }

    public void Miss() {
        this.totalClasses++;
        this.attendance = (this.classesAttended / this.totalClasses);
    }

    public void Cancel() {
        if (attended) {
            this.classesAttended--;
            this.totalClasses--;
            if (this.classesAttended == 0 && this.totalClasses == 0) {
                this.attendance = 0;
            } else {
                this.attendance = (this.classesAttended / this.totalClasses);
            }
            attended = false;
        }
    }

    public void Attend() {
        this.classesAttended++;
        this.totalClasses++;
        this.attendance = (this.classesAttended / this.totalClasses);
        attended = true;
    }
}
