/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapplication3kg.Data;

import com.mycompany.webapplication3kg.Data.User;
import java.util.ArrayList;

/**
 *
 * @author karol
 */
public class ApplicationData {
    ArrayList<T_Dane> t_dane = new ArrayList<T_Dane>();
    
    public void addDane(T_Dane t_dane){
        this.t_dane.add(t_dane);
    }
    public ArrayList<T_Dane> getDane(){
        return this.t_dane;
    }
    public int getLength(){
        return t_dane.size();
    }
    public T_Dane findId(int id){
        for(T_Dane dane:t_dane){
            if(dane.getId()==id){
                return dane;
            }
        }
        return null;
    }
}
