/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webapplication3kg.Data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Base64;

/**
 *
 * @author karol
 */
public class T_Dane {
    int id=0;            //bigint
    String nr=null;          //varchar(50)
    String tytul=null;       //varchar(50)
    String opis=null;        //varchar(255)
    BigDecimal kwota=null;   //decimal(12)
    byte[] obraz=null;       //image
    private String base64image=null;
    
    public T_Dane(){
    }
    public T_Dane(int id, String nr, String tytul, String opis, BigDecimal kwota, byte[] obraz) {
        this.id = id;
        this.nr = nr;
        this.tytul = tytul;
        this.opis = opis;
        this.kwota = kwota;
        this.obraz = obraz;
        this.base64image=Base64.getEncoder().encodeToString(obraz);
        
        
    }

    public String getBase64image() {
        return base64image;
    }
        
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public void setId(String id){
        this.id = Integer.parseInt(id);
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getKwota() {
        return kwota;
    }
    public String sGetKwota(){
        return kwota.toString();
    }

    public void setKwota(BigDecimal kwota) {
        this.kwota = kwota;
    }
    public void setKwota(String kwota){
        this.kwota = new BigDecimal(kwota);
    }

    public byte[] getObraz() {
        return obraz;
    }

    public void setObraz(byte[] obraz) {
        this.obraz = obraz;
    }
    
    public String toString(){
    return "id:"+this.id+" nr:"+this.nr+" tytul:"+this.tytul+" opis:"+this.opis+" kwota"+this.kwota.toString()+" obraz:"+this.obraz.length;
    }
    
    
}
