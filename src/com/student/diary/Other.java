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
public class Other extends Activity implements Serializable {

    String topic;
    String type;
    boolean hadPrize;
    private int place;

    public Other() {
        this.topic = "";
        this.type = "";
        this.hadPrize = false;
        this.place = -1;
    }

    public Other(String name, DateF sDate, DateF eDate, TimeF sTime, TimeF eTime, String topic, String type, boolean hadPrize) {
        super(name, sDate, eDate, sTime, eTime);
        this.topic = topic;
        this.type = type;
        this.hadPrize = hadPrize;
        this.place = -1;
    }

    public boolean setPlace(int place) {
        if (this.hadPrize) {
            this.place = place;
            return true;
        }
        return false;
    }

    public int getPlace() {
        if (this.hadPrize) {
            return this.place;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "Other: " + this.name;
    }
}
