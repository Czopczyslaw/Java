/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapplication3kg.Data;

/**
 *
 * @author karol
 */
public class User {
    String name;
    String surname;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
}
