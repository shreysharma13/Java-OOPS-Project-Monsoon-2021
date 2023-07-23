package com.example.managerappv1;

public class OrderItem {
    private String title = "Table ";
    private String name = "";
    private String phno = "";
    private String desc = "";
    private String datetime = "";
    OrderItem(String tableno, String name, String phno, String desc, String datetime){
        this.title = this.title + tableno;
        this.name = name;
        this.phno = phno;
        this.desc = desc;
        this.datetime = datetime;
    }
    String getTitle(){
        return this.title;
    }
    String getName(){
        return this.name;
    }
    String getPhno(){
        return this.phno;
    }
    String getDesc(){
        return this.desc;
    }
    String getDatetime(){
        return datetime;
    }
}
