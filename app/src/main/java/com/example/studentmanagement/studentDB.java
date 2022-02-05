package com.example.studentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class studentDB extends SQLiteOpenHelper {
    public studentDB(Context context){
        super(context, "student.db", null ,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table studentDetails(name Text , email TEXT,dob Text,gender Text ,course Text, registration TEXT primary key)");
        db.execSQL("CREATE TABLE IF NOT EXISTS admin (username TEXT PRIMARY KEY,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(("drop Table if exists studentDetails"));
    }

    public Boolean insertStudentData(String name, String email,String dob,String gender,String course, String registration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("dob",dob);
        contentValues.put("gender",gender);
        contentValues.put("course", course);
        contentValues.put("registration", registration);
            long result = db.insert("studentDetails", null, contentValues);
            if ((result == -1)) {
                return false;
            } else {
                return true;
            }
    }

    public Boolean deleteStudentData(String registration) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cusor=db.rawQuery("SELECT * FROM studentDetails WHERE registration=?",new String[]{registration});
        if(cusor.getCount()>0){
            long result =db.delete("studentDetails","registration=?",new String[]{registration});
            if((result==-1)){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean updateStudentData(String registration,String email,String course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("registration", registration);
        contentValues.put("email", email);
        contentValues.put("course", course);
        Cursor cusor = db.rawQuery("SELECT * FROM studentDetails WHERE registration=?", new String[]{registration});
        if (cusor.getCount() > 0) {
            long result = db.update("studentDetails", contentValues, "registration=?", new String[]{registration});
            if ((result == -1)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cusor = db.rawQuery("SELECT * FROM studentDetails",null);
        return cusor;
    }



    public Cursor getSingleData(String regno)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM studentDetails WHERE registration=?", new String[]{regno});
        if (cursor.getCount()==1) {
            return cursor;
        }
        else {
            return null;
        }
    }

    // Select the last registered number
    public String retLastReg(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT MAX(registration) AS regNo FROM studentDetails",null);
        if(cursor.getCount()>0){
            cursor.moveToNext();
            return cursor.getString(0);
        }else{
            return null;
        }
    }

    //this is for admin signup
    public boolean newSignup(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM admin",null);
        if(cursor.getCount()<1){
            ContentValues cv=new ContentValues();
            cv.put("username",username);
            cv.put("password",password);
            return db.insert("admin", null, cv) != -1;
        }else{
            return false;
        }
    }

    public boolean adminLogin(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM admin WHERE username='"+username+"' AND password='"+password+"'",null);
        return cursor.getCount()>0;
    }
}
