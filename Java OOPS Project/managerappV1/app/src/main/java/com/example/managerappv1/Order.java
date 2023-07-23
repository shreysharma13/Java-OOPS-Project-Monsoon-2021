package com.example.managerappv1;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Order {
    String name;
    String phno;
    String orderstring;
    String datetime;
    String tableno;
    Order(String name, String phno, String tableno){
        this.name = name;
        this.phno = phno;
        this.tableno = tableno;
        this.orderstring = "";
    }
    Order(String orderstring){
        String[] orderinfo = orderstring.split("=");
        this.name = orderinfo[0];
        this.phno = orderinfo[1];
        this.orderstring = orderinfo[2];
        this.datetime = orderinfo[3];
        this.tableno = orderinfo[4];
    }
    public String toString(){
        return this.name + "=" + this.phno + "=" + this.orderstring + "=" + this.datetime + "=" + this.tableno;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void setDateTime(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        datetime = myDateObj.format(myFormatObj);
    }
    void addDish(String dishno){
        this.orderstring = this.orderstring + " " + dishno;
    }
    void removeDish(String dishno){
        this.orderstring = orderstring.replaceFirst(" " + dishno,"");
    }
    String getTableno(){
        return this.tableno;
    }
    String getName(){
        return this.name;
    }
    String getPhno(){
        return this.phno;
    }
    String getOrderstring(){
        return this.orderstring;
    }
    String getDatetime(){
        return this.datetime;
    }

}
