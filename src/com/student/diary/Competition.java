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
public class Competition extends Activity implements Serializable {

    String type;
    String Host;
    boolean hadPrize;
    private int place;

    public Competition() {
        this.type = "";
        this.Host = "";
        this.hadPrize = false;
        this.place = -1;
    }

    public Competition(String name, DateF sDate, DateF eDate, TimeF sTime, TimeF eTime, String type, String Host, boolean hadPrize) {
        super(name, sDate, eDate, sTime, eTime);
        this.type = type;
        this.Host = Host;
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
        return "Competition: " + this.name;
    }
}
