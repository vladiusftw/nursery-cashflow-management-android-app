package com.example.csit242_project.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyNurseryDB";
    private static final String KID_TABLE_NAME = "Kid";
    private static final String STAFF_TABLE_NAME = "Staff";
    private static final String EXPENSE_TABLE_NAME = "Expense";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private int getMonth(String date){   // gets the month of the date of the expense
        String[] temp = date.split("/");
        return Integer.parseInt(temp[1]);
    }

    private int getYear(String date){  //gets the year of the date of the expense
        String[] temp = date.split("/");
        return Integer.parseInt(temp[0]);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    public void onCreate(SQLiteDatabase db) {  // create the tables in the database
        db.execSQL("CREATE TABLE " + KID_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL,pName TEXT NOT NULL,contact TEXT NOT NULL)");
        db.execSQL("CREATE TABLE " + STAFF_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE,password TEXT NOT NULL,isAdmin INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE " + EXPENSE_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "detail TEXT,amount REAL,date TEXT,kidId INTEGER, FOREIGN KEY (kidId) REFERENCES "
                + KID_TABLE_NAME + "(id) ON DELETE CASCADE)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*if(oldVersion < 2){

        }
        if(oldVersion < 3){

        }*/
    }

    // insert a kid into the database
    public void insertKid(Kid kid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", kid.getName());
        values.put("pName", kid.getPName());
        values.put("contact", kid.getContact());
        db.insert(KID_TABLE_NAME, null, values);
        db.close();
    }

    // insert a staff into the database
    public void insertStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", staff.getUsername());
        values.put("password", staff.getPassword());
        values.put("isAdmin", staff.isAdmin());
        db.insert(STAFF_TABLE_NAME, null, values);
        db.close();
    }

    // insert a kid's expense into the database
    public void insertKidExpense(Expense expense, int kidId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("detail", expense.getDetail());
        values.put("amount", expense.getAmount());
        values.put("date", expense.getStringDate());
        values.put("kidId",kidId);
        db.insert(EXPENSE_TABLE_NAME,null,values);
        db.close();
    }

    // insert a nursery's expense into the database
    public void insertNurseryExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("detail", expense.getDetail());
        values.put("amount", expense.getAmount());
        values.put("date", expense.getStringDate());
        values.putNull("kidId");
        db.insert(EXPENSE_TABLE_NAME,null,values);
        db.close();
    }

    // get All kids from the database
    public ArrayList<Kid> getKids() {  // display all kids
        ArrayList<Kid> kids = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + KID_TABLE_NAME, null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int pNameIndex = cursor.getColumnIndex("pName");
            int contactIndex = cursor.getColumnIndex("contact");
            while (cursor.moveToNext()) {
                kids.add(new Kid(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(pNameIndex),
                        cursor.getString(contactIndex)));
            }
            cursor.close();
            db.close();
            return kids;
        }catch (SQLiteException e){
            return kids;
        }
    }





    // get all staff from the database
    public ArrayList<Staff> getStaff() { // display all of the staff members
        ArrayList<Staff> staffList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + STAFF_TABLE_NAME, null);
            int idIndex = cursor.getColumnIndex("id");
            int usernameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");
            int isAdminIndex = cursor.getColumnIndex("isAdmin");
            int picIndex = cursor.getColumnIndex("pic");
            while (cursor.moveToNext()){
                staffList.add(new Staff(
                        cursor.getInt(idIndex),
                        cursor.getString(usernameIndex),
                        cursor.getString(passwordIndex),
                        cursor.getInt(isAdminIndex)));
            }
            cursor.close();
            db.close();
            return staffList;
        }catch (SQLiteException e){
            return staffList;
        }
    }

    // get nursery expenses based on a specific month
    public ArrayList<Expense> getNurseryExpensesByMonth( int month ) {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId IS NULL", null);
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                String date = cursor.getString(dateIndex);
                if(getMonth(date) == month) {
                    expenses.add(new Expense(
                            cursor.getString(detailIndex),
                            cursor.getDouble(amountIndex),
                            date));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get nursery expenses based on specific year
    public ArrayList<Expense> getNurseryExpensesByYear(int year) {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId IS NULL", null);
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                String date = cursor.getString(dateIndex);
                if(getYear(date) == year) {
                    expenses.add(new Expense(
                            cursor.getString(detailIndex),
                            cursor.getDouble(amountIndex),
                            date));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get nursery expenses between specific dates
    public ArrayList<Expense> getNurseryExpensesByCustom(Date date1, Date date2) throws ParseException {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId IS NULL", null);
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                String stringDate = cursor.getString(dateIndex);
                Date date = FunctionsHelper.getDate(stringDate);
                if(date.after(date1) && date.before(date2)){
                    String detail = cursor.getString(detailIndex);
                    double amount = cursor.getDouble(amountIndex);
                    expenses.add(new Expense(detail,amount,stringDate));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get all kids that start with specific id
    public ArrayList<Kid> getKidsIdStartsWith (int id){
        ArrayList<Kid> kids = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + KID_TABLE_NAME + " WHERE id LIKE  '" + id + "%'", null);
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int pNameIndex = cursor.getColumnIndex("pName");
            int contactIndex = cursor.getColumnIndex("contact");
            while (cursor.moveToNext()) {
                kids.add(new Kid(
                        cursor.getInt(idIndex),
                        cursor.getString(nameIndex),
                        cursor.getString(pNameIndex),
                        cursor.getString(contactIndex)));
            }
            cursor.close();
            db.close();
            return kids;
        }catch (SQLiteException e){
            return kids;
        }
    }

    // get all staffs that start with a specific ic
    public ArrayList<Staff> getStaffsIdStartsWith ( int id){
        ArrayList<Staff> staffList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM staff WHERE id LIKE  '" + id + "%'", null);
            int idIndex = cursor.getColumnIndex("id");
            int usernameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");
            int isAdminIndex = cursor.getColumnIndex("isAdmin");
            int picIndex = cursor.getColumnIndex("pic");
            while (cursor.moveToNext()){
                staffList.add(new Staff(
                        cursor.getInt(idIndex),
                        cursor.getString(usernameIndex),
                        cursor.getString(passwordIndex),
                        cursor.getInt(isAdminIndex)));
            }
            cursor.close();
            db.close();
            return staffList;
        }catch (SQLiteException e){
            return staffList;
        }
    }

    // get specific kid statements on specific month
    public ArrayList<Expense> getStatementByMonth(int id, int month, int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId = " + id, null);
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                if(getMonth((cursor.getString(dateIndex)))==month && getYear(cursor.getString(dateIndex)) == year){
                    expenses.add(new Expense(
                            cursor.getString(detailIndex),
                            cursor.getDouble(amountIndex),
                            cursor.getString(dateIndex)));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get specific kid statements on specific year
    public ArrayList<Expense> getStatementByYear(int id, int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId = " + id, null); // where foreign key is null ont forget to add!!!!!
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                if(getYear((cursor.getString(dateIndex)))==year){
                    expenses.add(new Expense(
                            cursor.getString(detailIndex),
                            cursor.getDouble(amountIndex),
                            cursor.getString(dateIndex)));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get specific kid statements between specific dates
    public ArrayList<Expense> getStatementByCustom(int id, Date date1, Date date2) throws ParseException {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId = " + id, null); // where foreign key is null ont forget to add!!!!!
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                String stringDate = cursor.getString(dateIndex);
                Date date = FunctionsHelper.getDate(stringDate);
                if(date.after(date1) && date.before(date2)){
                    String detail = cursor.getString(detailIndex);
                    double amount = cursor.getDouble(amountIndex);
                    expenses.add(new Expense(detail,amount,stringDate));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get all kids statements on specific month
    public ArrayList<Expense> getStatementsByMonth(int month, int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId IS NOT NULL", null); // where foreign key is null ont forget to add!!!!!
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                if(getMonth((cursor.getString(dateIndex)))==month && getYear(cursor.getString(dateIndex)) == year){
                    expenses.add(new Expense(
                            cursor.getString(detailIndex),
                            cursor.getDouble(amountIndex),
                            cursor.getString(dateIndex)));

                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }
    }

    // get all kids statements on specific year
    public ArrayList<Expense> getStatementsByYear(int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            int fkIndex= cursor.getColumnIndex("kidId");
            while (cursor.moveToNext()) {
                if(cursor.getInt(fkIndex)!=0 && getYear((cursor.getString(dateIndex)))==year){
                    expenses.add(new Expense(
                            cursor.getString(detailIndex),
                            cursor.getDouble(amountIndex),
                            cursor.getString(dateIndex)));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        }catch (SQLiteException e){
            return expenses;
        }

    }

    // get all kids statements between specific dates
    public ArrayList<Expense> getStatementsByCustom(Date date1, Date date2) throws ParseException {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE kidId IS NOT NULL", null);
            int detailIndex = cursor.getColumnIndex("detail");
            int amountIndex = cursor.getColumnIndex("amount");
            int dateIndex = cursor.getColumnIndex("date");
            while (cursor.moveToNext()) {
                String stringDate = cursor.getString(dateIndex);
                Date date = FunctionsHelper.getDate(stringDate);
                if(date.after(date1) && date.before(date2)){
                    String detail = cursor.getString(detailIndex);
                    double amount = cursor.getDouble(amountIndex);
                    expenses.add(new Expense(detail,amount,stringDate));
                }
            }
            cursor.close();
            db.close();
            return expenses;
        } catch (SQLiteException e){
            return expenses;
        }
    }

    // gets the staff that has the given username and password
    // if doesn't exist then returns null
    public int getStaffIdByUserPass(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        int id = 0;
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + STAFF_TABLE_NAME + " WHERE username ='"
                    + username + "' AND password ='" + password + "'",null);
            int idIndex = cursor.getColumnIndex("id");
            while(cursor.moveToNext()){
                id = cursor.getInt(idIndex);
            }
            cursor.close();
            db.close();
            return id;
        } catch (SQLiteException e){
            return id;
        }
    }


    public boolean isValidKidId(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + KID_TABLE_NAME + " WHERE id = " + id,
                    null);
            if(cursor.getCount() <= 0){
                cursor.close();
                db.close();
                return false;
            }
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public boolean isValidStaffId(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + STAFF_TABLE_NAME + " WHERE id = " + id,
                    null);
            if(cursor.getCount() <= 0){
                cursor.close();
                db.close();
                return false;
            }
            cursor.close();
            db.close();
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }


    public boolean updateKid(Kid kid, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("name",kid.getName());
        values.put("pName",kid.getPName());
        values.put("contact",kid.getContact());
        try{
            db.update(KID_TABLE_NAME, values, "id = ?", new String[]{id+""});
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public boolean updateStaff(Staff staff, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("username",staff.getUsername());
        values.put("password",staff.getPassword());
        values.put("isAdmin",staff.isAdmin());
        try{
            db.update(STAFF_TABLE_NAME, values, "id = ?", new String[]{id+""});
            return true;
        }catch (SQLiteException e){
            return false;
        }
    }

    public Kid getKidId(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + KID_TABLE_NAME + " WHERE id=" + id,null);
            int nameIndex = cursor.getColumnIndex("name");
            int parentIndex = cursor.getColumnIndex("pName");
            int contactIndex = cursor.getColumnIndex("contact");
            Kid kid = null;
            if(cursor.moveToNext()){
                kid = new Kid(cursor.getString(nameIndex),cursor.getString(parentIndex),cursor.getString(contactIndex));
            }
            cursor.close();
            db.close();
            return kid;
        }catch (SQLiteException e){
            return null;
        }
    }

    public Staff getStaffById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + STAFF_TABLE_NAME + " WHERE id=" + id,null);
            int userIndex = cursor.getColumnIndex("username");
            int passIndex = cursor.getColumnIndex("password");
            int adminIndex = cursor.getColumnIndex("isAdmin");
            Staff staff = null;
            if(cursor.moveToNext()){
                staff = new Staff(cursor.getString(userIndex),cursor.getString(passIndex),cursor.getInt(adminIndex));
            }
            cursor.close();
            db.close();
            return staff;
        }catch (SQLiteException e){
            return null;
        }
    }
}