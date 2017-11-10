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
public class Credentials implements Serializable {

    private String rollno, password;

    public Credentials setRollno(String rollno) {
        this.rollno = rollno;
        return this;
    }

    public Credentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRollno() {
        return this.rollno;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean compareTo(Credentials o) {
        if (this.rollno.contentEquals(o.getRollno())) {
            if (this.password.contentEquals(o.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
