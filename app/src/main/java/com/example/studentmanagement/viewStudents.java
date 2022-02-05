package com.example.studentmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class viewStudents extends AppCompatActivity {

    studentDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        //creation of object for studentDB
        db = new studentDB(this);
        init();
//        Cursor response=db.getAllData();
//        if(response.getCount()==0)
//            Toast.makeText(viewStudents.this,"No students registered yet",Toast.LENGTH_SHORT).show();
//        else{
//            StringBuffer buffer = new StringBuffer();
//            while(response.moveToNext()){
//                buffer.append("name :"+ response.getString(0) + "\n");
//                buffer.append("email :"+ response.getString(1) + "\n");
//                buffer.append("dob :"+ response.getString(2) + "\n");
//                buffer.append("gender :"+ response.getString(3) + "\n");
//                buffer.append("course :"+ response.getString(4) + "\n");
//                buffer.append("registration :"+ response.getString(5) + "\n");
//            }
//            showMessage("Data",buffer.toString());
//        }


    }

    //        public void showMessage(String title,String message){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(true);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.show();
//    }
    public void init() {

        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setTypeface(Typeface.DEFAULT_BOLD);
        tv0.setText(" Reg No ");
        tv0.setTextColor(Color.WHITE);
        tv0.setTextSize(20);
        tv0.setBackgroundResource(R.drawable.tbl_border);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setBackgroundResource(R.drawable.tbl_border);
        tv1.setText(" Name ");
        tv1.setTextColor(Color.WHITE);
        tv1.setTypeface(Typeface.DEFAULT_BOLD);
        tv1.setTextSize(20);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setBackgroundResource(R.drawable.tbl_border);
        tv2.setText(" Gender ");
        tv2.setTextColor(Color.WHITE);
        tv2.setTypeface(Typeface.DEFAULT_BOLD);
        tv2.setTextSize(20);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setBackgroundResource(R.drawable.tbl_border);
        tv3.setText(" Course ");
        tv3.setTextColor(Color.WHITE);
        tv3.setTypeface(Typeface.DEFAULT_BOLD);
        tv3.setTextSize(20);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);


        // view data

        //view student details
        Cursor response = db.getAllData();
        if (response.getCount() == 0) {
            Toast.makeText(viewStudents.this, "No students registered yet", Toast.LENGTH_SHORT).show();
        } else {
            StringBuffer buffer = new StringBuffer();
            while (response.moveToNext()) {


                //for (int i = 0; i < 25; i++) {
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(response.getString(5));
                t1v.setTextColor(Color.WHITE);
                t1v.setPadding(0,2,0,2);
                t1v.setGravity(Gravity.CENTER);
                t1v.setBackgroundResource(R.drawable.tbl_border);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(response.getString(0));
                t2v.setTextColor(Color.WHITE);
                t2v.setPadding(0,2,0,2);
                t2v.setGravity(Gravity.CENTER);
                t2v.setBackgroundResource(R.drawable.tbl_border);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(response.getString(3));
                t3v.setTextColor(Color.WHITE);
                t3v.setPadding(0,2,0,2);
                t3v.setGravity(Gravity.CENTER);
                t3v.setBackgroundResource(R.drawable.tbl_border);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(response.getString(4));
                t4v.setTextColor(Color.WHITE);
                t4v.setPadding(0,2,0,2);
                t4v.setGravity(Gravity.CENTER);
                t4v.setBackgroundResource(R.drawable.tbl_border);
                tbrow.addView(t4v);
                stk.addView(tbrow);
            }

        }
    }
}
