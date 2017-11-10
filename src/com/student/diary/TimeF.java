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
public class TimeF implements Serializable {

    private int minutes, hours;

    public TimeF() {
        this.minutes = this.hours = 0;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0) {
            minutes = 0;
        }
        if (minutes > 59) {
            this.setHours(minutes / 60);
            minutes = minutes % 60;
        }
        this.minutes = minutes;
    }

    public void setHours(int hours) {
        if (hours < 0) {
            hours = 0;
        }
        if (hours > 23) {
            hours = 23;
        }
        this.hours = hours;
    }

    public TimeF set(int minutes, int hours) {
        this.setMinutes(minutes);
        this.setHours(hours);
        return this;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public int getHours() {
        return this.hours;
    }
}
