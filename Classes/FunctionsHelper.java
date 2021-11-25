package com.example.csit242_project.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class FunctionsHelper {
    public static void setHints(EditText e1, EditText e2, EditText e3, boolean isChecked){
        if(isChecked){
            e1.setText("");
            e1.setHint("Username");
            e2.setText("");
            e2.setHint("Password");
            e3.setText("");
            e3.setHint("S(0)-A(1)");
        }
        else{
            e1.setText("");
            e1.setHint("First Name");
            e2.setText("");
            e2.setHint("Parent Name");
            e3.setText("");
            e3.setHint("Contact");
        }
    }

    public static boolean isValidDate(String date){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try{
            format.parse(date);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static Date getDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }

    public static String getStringCurrentDate(){
        Date date = new Date();
        return formatDay(date.getDate())+"/"+formatMonth(date.getMonth()+1) + "/" + formatYear(date.getYear()+1900);
    }

    public static String lettersAndNumbersOnly(String str){
        String temp = "";
        for(int i = 0; i < str.length();i++){
            if((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
                    || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')
            || (str.charAt(i) >= '0' && str.charAt(i) <= '9')) temp += str.charAt(i);
        }
        return temp;
    }

    public static String removeWhiteSpace(String str){
        String temp = "";
        for(int i = 0; i < str.length();i++){
            if(str.charAt(i) != ' ' && str.charAt(i) != '\n') temp += str.charAt(i);
        }
        return temp;
    }

    public static boolean isNotEmpty(EditText e1, EditText e2, EditText e3){
        String s1 = removeWhiteSpace(e1.getText()+"");
        String s2 = removeWhiteSpace(e2.getText()+"");
        String s3 = removeWhiteSpace(e3.getText()+"");
        return s1.length() != 0 && s2.length() != 0 && s3.length() != 0;
    }

    public static boolean isNotEmpty(EditText e1, EditText e2){
        String s1 = removeWhiteSpace(e1.getText()+"");
        String s2 = removeWhiteSpace(e2.getText()+"");
        return s1.length() != 0 && s2.length() != 0;
    }

    public static String[] maxDays(int days){
        String[] arr = new String[days];
        for(int i = 1; i <= arr.length;i++){
            arr[i-1] = i+"";
        }
        return arr;
    }

    public static String[] details(){
        return new String[]{"MONTHLY","YEARLY","CUSTOM"};
    }

    public static String[] months(){
        return new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
    }

    public static String[] years(){
        int currYear = new Date().getYear()+1900;
        String[] years = new String[20];
        for(int i = 0; i < 20;i++){
            years[i] = currYear + "";
            currYear--;
        }
        return years;
    }

    public static int getMaxDayByMonth(int month,int year){
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
            default:
                return isLeapYear(year) ? 29 : 28;
        }
    }

    public static boolean isLeapYear(int year){
        if(year % 4 == 0){
            if(year % 100 == 0)return year % 400 != 0;
            return true;
        }
        return false;
    }

    public static void showToast(Context c, String text){
        Toast.makeText(c,text,Toast.LENGTH_SHORT).show();
    }

    public static boolean hasLettersOnly(String text){
        return Pattern.compile("[a-z]+|[A-Z]+").matcher(text).matches();
    }

    public static String removeOtherThanLetters(String text){
        String temp = "";
        for(int i = 0; i < text.length();i++){
            if(hasLettersOnly(text.charAt(i)+"")) temp += text.charAt(i);
        }
        return temp;
    }

    public static void addTextWatcher(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            boolean wasEdited = false;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(wasEdited){
                    wasEdited = false;
                    return;
                }

                String curr = editable.toString();
                String newVal = FunctionsHelper.removeOtherThanLetters(curr);
                wasEdited = true;
                editable.replace(0,curr.length(),newVal);
            }
        });
    }

    public static double getTotalAmount(ArrayList<Expense> expenses){
        double amount = 0;
        for(Expense e : expenses) amount += e.getAmount();
        return amount;
    }


    private static String formatDay(int day){
        String sDay = day + "";
        if(sDay.length()==1) sDay = "0" + sDay;
        return sDay;
    }

    private static String formatMonth(int month){
        String sMonth = month + "";
        if(sMonth.length()==1) sMonth = "0" + sMonth;
        return sMonth;
    }

    private static String formatYear(int year){
        String sYear = year + "";
        while(sYear.length()<4) sYear = "0" + sYear;
        return sYear;
    }




}
