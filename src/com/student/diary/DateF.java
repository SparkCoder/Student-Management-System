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
public class DateF implements Serializable {

    private int date, month, year;

    public DateF() {
        this.date = this.month = this.year = 0;
    }

    public boolean leapYear() {
        if ((this.year % 4) == 0) {
            if ((this.year % 100) == 0) {
                if ((this.year % 400) == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public void setDate(int date) {
        if (date < 1) {
            date = 1;
        } else if (((this.month <= 7) && ((this.month % 2) != 0)) || ((this.month > 7) && ((this.month % 2) == 0))) {
            if (date > 31) {
                date = 31;
            } else if (this.month == 2) {
                if (this.leapYear()) {
                    if (date > 29) {
                        date = 29;
                    } else if (date > 28) {
                        date = 28;
                    } else if (date > 30) {
                        date = 30;
                    }
                }
            }
        }
        this.date = date;
    }

    public void setMonth(int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        this.month = month;
        this.setDate(this.date);
    }

    public void setYear(int year) {
        this.year = year;
        this.setDate(this.date);
    }

    public DateF set(int date, int month, int year) {
        this.setDate(date);
        this.setMonth(month);
        this.setYear(year);
        return this;
    }

    public int getDate() {
        return this.date;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }
    
    @Override
    public String toString() {
        return this.date + "/" + this.month + "/" + this.year;
    }
}
