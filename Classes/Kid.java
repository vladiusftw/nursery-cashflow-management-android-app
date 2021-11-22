package com.example.csit242_project.Classes;

public class Kid {
    private int id;
    private String name,pName,contact;

    public Kid(){}
    public Kid(String name, String pName,String contact){
        this.name = name;
        this.pName = pName;
        this.contact = contact;
    }
    public Kid(int id, String name, String pName,String contact){
        this.id = id;
        this.name = name;
        this.pName = pName;
        this.contact = contact;
    }

    // getters
    public int getId(){return id;}
    public String getName(){return name;}
    public String getPName(){return pName;}
    public String getContact(){return contact;}

    public String toString(){
        return "ID: " + id + "\tName: " + name + "\tParent Name: " + pName + "\tContact: " + contact;
    }
}
