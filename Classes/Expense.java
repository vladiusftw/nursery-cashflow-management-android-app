package com.example.csit242_project.Classes;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private final String detail;
    private final double amount;
    private final String date;
    private int kidId;

    public Expense(String detail, double amount, String date){
        this.detail = detail;
        this.amount = amount;
        this.date = date;
    }

    public Expense(String detail, double amount, String date, int kidId){
        this.detail = detail;
        this.amount = amount;
        this.date = date;
        this.kidId = kidId;
    }

    // getters
    public String getDetail(){return detail;}
    public double getAmount(){return amount;}
    public String getStringDate(){return date;}

    public String toString(){
        return "Detail: " + detail + "\tAmount: "
                + new DecimalFormat("#.##").format(amount) + "\tDate(DD/MM/YYYY): "
                + date;
    }


}
