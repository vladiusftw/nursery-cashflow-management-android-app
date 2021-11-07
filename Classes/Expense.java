package com.example.project;

import java.text.DecimalFormat;
import java.util.Date;

public class Expense {
    private int id;
    private String detail;
    private double amount;
    private final Date date;

    public Expense(){date = new Date();}
    public Expense(int id, String detail, double amount, Date date){
        this.id = id;
        this.detail = detail;
        this.amount = amount;
        this.date = date;
    }

    // getters
    public int getId(){return id;}
    public String getDetail(){return detail;}
    public double getAmount(){return amount;}
    public Date getDate(){return date;}

    public String toString(){
        return "ID: " + id + "\tDetail: " + detail + "\tAmount: "
                + new DecimalFormat("#.##").format(amount) + "\tDate(DD/MM/YYYY): "
                + date.getDate() + "/" + (date.getMonth()+1) + "/" + (date.getYear()+1900);
    }
}
