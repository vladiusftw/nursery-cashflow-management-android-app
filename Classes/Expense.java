package com.example.csit242_project.Classes;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private final int id;
    private final String detail;
    private final double amount;
    private final String date;


    public Expense(int id, String detail, double amount, String date){
        this.id = id;
        this.detail = detail;
        this.amount = amount;
        this.date = date;
    }

    // getters
    public int getId(){return id;}
    public String getDetail(){return detail;}
    public double getAmount(){return amount;}
    public String getStringDate(){return date;}
    @SuppressLint("SimpleDateFormat")
    public Date getDate() throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    public String toString(){
        return "ID: " + id + "\tDetail: " + detail + "\tAmount: "
                + new DecimalFormat("#.##").format(amount) + "\tDate(DD/MM/YYYY): "
                + date;
    }


}
