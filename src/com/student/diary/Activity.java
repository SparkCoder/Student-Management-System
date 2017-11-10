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
public abstract class Activity implements Serializable {

    String name;
    DateF sDate, eDate;
    TimeF sTime, eTime;
    boolean hadPrize;
    int place;

    public Activity() {
        this.name = "";
        this.sDate = new DateF();
        this.eDate = new DateF();
        this.sTime = new TimeF();
        this.eTime = new TimeF();
    }

    public Activity(String name, DateF sDate, DateF eDate, TimeF sTime, TimeF eTime) {
        this.name = name;
        this.sDate = sDate;
        this.eDate = eDate;
        this.sTime = sTime;
        this.eTime = eTime;
    }
    
    @Override
    public String toString() {
        return "Activity: " + this.name;
    }
}
