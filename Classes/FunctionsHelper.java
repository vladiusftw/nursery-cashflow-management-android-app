package com.example.csit242_project.Classes;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            e1.setHint("Full Name");
            e2.setText("");
            e2.setHint("Parent Name");
            e3.setText("");
            e3.setHint("Contact");
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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

}
