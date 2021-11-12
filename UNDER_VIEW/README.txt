upload ur work here and i will take a look at it then proofread and check it then upload it to the main

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

class DatabaseHelper extends SQLiteOpenHelper {
    private Context c;
    private static final String DATABASE_NAME = "MyNurseryDB";
    private static final String KID_TABLE_NAME = "Kid";
    private static final String STAFF_TABLE_NAME = "Staff";
    private static final String EXPENSE_TABLE_NAME = "Expense";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
        this.c = c;
        this.getWritableDatabase().delete(EXPENSE_TABLE_NAME, null, null);
    }
    private int getMonth(String date){   // gets the month of the date of the expense
        String[] temp = date.split("/");
         return Integer.parseInt(temp[1]);
    }

    private int getYear(String date){  //gets the year of the date of the expense
        String[] temp = date.split("/");
        return Integer.parseInt(temp[0]);
    }


    public void onCreate(SQLiteDatabase db) {  // create the tables in the database
        db.execSQL("CREATE TABLE " + KID_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT,pName TEXT,contact TEXT)");
        db.execSQL("CREATE TABLE " + STAFF_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT,password TEXT,isAdmin INTEGER)");
        db.execSQL("CREATE TABLE " + EXPENSE_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "detail TEXT,amount TEXT,date TEXT,kidId INTEGER, FOREIGN KEY (kidId) REFERENCES "
        + KID_TABLE_NAME + "(id))");
    }

    public void onUpgrade(SQLiteDatabase db, int i, int j) {  // delete the tables within the database
        db.delete(KID_TABLE_NAME, null, null);
        db.delete(STAFF_TABLE_NAME, null, null);
        db.delete(EXPENSE_TABLE_NAME, null, null);
        onCreate(db);
    }

    public void insertKid(Kid kid) {  // insert a new kid into the kids' table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", kid.getName());
        values.put("pName", kid.getPName());
        values.put("contact", kid.getContact());
        db.insert(KID_TABLE_NAME, null, values);
        db.close();
    }




    public void insertStaff(Staff staff) {  // insert a new staff member into the staff's table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", staff.getUsername());
        values.put("password", staff.getPassword());
        values.put("isAdmin", staff.isAdmin());
        db.insert(STAFF_TABLE_NAME, null, values);
        db.close();
    }






    public void insertKidExpense(Expense expense, int kidId) {  // insert a kid's expense into the expenses' table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("detail", expense.getDetail());
        values.put("amount", expense.getAmount());
        values.put("date", expense.getDate().toString());
        values.put("kidId",kidId);
        db.insert(EXPENSE_TABLE_NAME,null,values);
        db.close();
    }






    public void insertNurseryExpense(Expense expense) { // insert a nursery expense into the expenses' table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("detail", expense.getDetail());
        values.put("amount", expense.getAmount());
        values.put("date", expense.getDate().toString());
        values.put("kidId",0);
        db.insert(EXPENSE_TABLE_NAME,null,values);
        db.close();
    }






    public ArrayList<Kid> getKids() {  // display all kids
        ArrayList<Kid> kids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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
        return kids;
    }






    public ArrayList<Staff> getStaff() { // display all of the staff members
        ArrayList<Staff> staffList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STAFF_TABLE_NAME, null);
        int idIndex = cursor.getColumnIndex("id");
        int usernameIndex = cursor.getColumnIndex("username");
        int passwordIndex = cursor.getColumnIndex("password");
        int isAdminIndex = cursor.getColumnIndex("isAdmin");
            while (cursor.moveToNext()){
               staffList.add(new Staff(
                       cursor.getInt(idIndex),
                       cursor.getString(usernameIndex),
                       cursor.getString(passwordIndex),
                       cursor.getInt(isAdminIndex)));
            }
        return staffList;
    }





    public ArrayList<Expense> getNurseryExpensesByMonth( int month ) { // display a nursery expense based on its month
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)==0 && getMonth((cursor.getString(dateIndex)))==month) {
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }
        return expenses;
    }





    public ArrayList<Expense> getNurseryExpensesByYear( int year ) { // display a nursery expense based on its year
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)==0 && getYear((cursor.getString(dateIndex)))==year) {
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }
        return expenses;
    }




// display a nursery expense based on a specific date

    public ArrayList<Expense> getNurseryExpensesByCustom( String date ) {
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)==0 && cursor.getString(dateIndex).equals(date)) {
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }
        return expenses;
    }




// display all kids based on the beginning of their id

    public ArrayList<Kid> getKidsIdStartsWith ( int id){
        ArrayList<Kid> kids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM kid WHERE id LIKE  '" + id + "%'";
        Cursor cursor = db.rawQuery(q, null);
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
            return kids;
    }





// display staff members based on the start of their id

    public ArrayList<Staff> getStaffsIdStartsWith ( int id){
        ArrayList<Staff> staffList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String q = "SELECT * FROM staff WHERE id LIKE  '" + id + "%'";
        Cursor cursor = db.rawQuery(q, null);
            int idIndex = cursor.getColumnIndex("id");
            int usernameIndex = cursor.getColumnIndex("username");
            int passwordIndex = cursor.getColumnIndex("password");
            int isAdminIndex = cursor.getColumnIndex("isAdmin");
            while (cursor.moveToNext()){
                staffList.add(new Staff(
                        cursor.getInt(idIndex),
                        cursor.getString(usernameIndex),
                        cursor.getString(passwordIndex),
                        cursor.getInt(isAdminIndex)));
            }
            return staffList;
    }




// display a kid's specific statement by month
    
    public ArrayList<Expense> getStatementByMonth(int id, int month){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)!=0 && cursor.getInt(fkIndex)==id && getMonth((cursor.getString(dateIndex)))==month){
            expenses.add(new Expense(
                    cursor.getInt(idIndex),
                    cursor.getString(detailIndex),
                    cursor.getDouble(amountIndex),
                    cursor.getString(dateIndex)));

             }
        }

        return expenses;
    }





// display a kid's specific statement based on its year
    
    public ArrayList<Expense> getStatementByYear(int id, int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)!=0 && cursor.getInt(fkIndex)==id && getYear((cursor.getString(dateIndex)))==year){
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }

        return expenses;
    }




// display a kid's specific statement based on a specific date
    
    public ArrayList<Expense> getStatementByCustom(int id, String date){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)!=0 && cursor.getInt(fkIndex)==id && (cursor.getString(dateIndex).equals(date))){
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }

        return expenses;
    }



// display all of a kid's statements by month

    public ArrayList<Expense> getStatementsByMonth( int month){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)!=0 && getMonth((cursor.getString(dateIndex)))==month){
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));

            }
        }

        return expenses;
    }



// display all of a kid's statements by year

    public ArrayList<Expense> getStatementsByYear(int year){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)!=0 && getYear((cursor.getString(dateIndex)))==year){
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }

        return expenses;
    }



// display all of a kid's statements by a specific date

    public ArrayList<Expense> getStatementsByCustom(String date){
        ArrayList<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EXPENSE_TABLE_NAME, null); // where foreign key is null ont forget to add!!!!!
        int idIndex = cursor.getColumnIndex("id");
        int detailIndex = cursor.getColumnIndex("detail");
        int amountIndex = cursor.getColumnIndex("amount");
        int dateIndex = cursor.getColumnIndex("date");
        int fkIndex= cursor.getColumnIndex("kidId");
        while (cursor.moveToNext()) {
            if(cursor.getInt(fkIndex)!=0 && (cursor.getString(dateIndex).equals(date))){
                expenses.add(new Expense(
                        cursor.getInt(idIndex),
                        cursor.getString(detailIndex),
                        cursor.getDouble(amountIndex),
                        cursor.getString(dateIndex)));
            }
        }

        return expenses;
    }
}
