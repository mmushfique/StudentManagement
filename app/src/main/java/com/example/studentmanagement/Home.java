package com.example.studentmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;


public class Home extends AppCompatActivity {
    EditText search,name,dob,email,registration;
    RadioGroup genderGroup;
    //RadioButton gender;
    Spinner course;
    Button btnSearch,btnAdd,btnDelete,btnUpdate,btnView,btnClear;
    KeyListener variable;
    studentDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //set data to spinner
        course = findViewById(R.id.spinnerCourse);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.courseArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(adapter);

        //textBoxes
        search =findViewById(R.id.txtSearch);
        name =findViewById(R.id.txtName);
        dob =findViewById(R.id.txtDOB);
        email=findViewById(R.id.txtEmail);
        registration=findViewById(R.id.txtRegistration);
        //radio
        genderGroup =findViewById(R.id.radioGroupGender);
        //buttons
        btnSearch =findViewById(R.id.btnSearch);
        btnAdd =findViewById(R.id.btnAdd);
        btnDelete =findViewById(R.id.btnDelete);
        btnUpdate =findViewById(R.id.btnUpdate);
        btnView =findViewById(R.id.btnView);
        btnClear =findViewById(R.id.btnClear);
        //creation of object for studentDB
        db = new  studentDB(this);
        lastReg();

        //Reset button
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                lastReg();
            }
        });


        //Event for add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = name.getText().toString();
                String txtDOB = dob.getText().toString();

                int selectedGender = genderGroup.getCheckedRadioButtonId();
                String txtGender = selectedGender==-1? "":((RadioButton)findViewById(selectedGender)).getText().toString();
//                gender = (RadioButton) findViewById(selectedGender);
//                String txtGender = gender.getText().toString();
                String txtCourse = course.getSelectedItem().toString();
                String txtEmail = email.getText().toString();
                String txtRegistration = registration.getText().toString();
                if (txtName.equals("") || txtEmail.equals("") || txtDOB.equals("") || txtGender.equals("") || txtCourse.equals("") || txtRegistration.equals("")) {
                    String msg= txtName.equals("")
                            ?"Please Enter a Name"
                            :txtDOB.equals("")
                                ?"Please Enter DOB"
                                :txtGender.equals("")
                                    ?"Please Select a Gender"
                                    :txtCourse.equals("")
                                        ?"Please Select a Course"
                                        :txtEmail.equals("")
                                            ?"Please Enter a Email"
                                            : "Please Enter a Registration Number";
                    Toast.makeText(Home.this,msg,Toast.LENGTH_SHORT).show();
                } else {
                    if(txtEmail.contains(".com") && txtEmail.contains("@") && txtEmail.indexOf('@')<txtEmail.indexOf('.') && txtEmail.indexOf('@')>0){
                        Boolean checkinsertdata;//to check whether data inserted
                        checkinsertdata = db.insertStudentData(txtName, txtEmail, txtDOB, txtGender, txtCourse, txtRegistration);
                        if (checkinsertdata) {
                            Toast.makeText(Home.this, "New Entry Entered", Toast.LENGTH_SHORT).show();
                            reset();
                            lastReg();
                        }else
                            Toast.makeText(Home.this, "New Entry Not Entered", Toast.LENGTH_SHORT).show();

//                            String msg = db.insertStudentData(txtName, txtEmail, txtDOB, txtGender, txtCourse, txtRegistration) ? "New Entry Entered" : "New Entry Not Entered";
//                            Toast.makeText(Home.this, msg, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Home.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Event for delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtSearch = registration.getText().toString();
                Boolean checkdeletedata;//to check whether data inserted
                checkdeletedata = db.deleteStudentData(txtSearch);
                if(checkdeletedata==true) {
                    Toast.makeText(Home.this, "Student Deleted", Toast.LENGTH_SHORT).show();
                    reset();
                    lastReg();
                }else
                    Toast.makeText(Home.this,"Enter a valid registration number",Toast.LENGTH_SHORT).show();
//                String msg=db.deleteStudentData(txtSearch)?"Student Deleted":"Deletion unsuccessful";
//                Toast.makeText(Home.this,msg,Toast.LENGTH_SHORT).show();
            }
        });

        //Event for delete button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtRegistration = registration.getText().toString();
                String txtEmail = email.getText().toString();
                String txtCourse = course.getSelectedItem().toString();

                if (txtEmail.equals("") || txtCourse.equals("") || txtRegistration.equals("")) {
                    String msg= txtRegistration.equals("")
                            ?"Please Enter a Registration Number or search"
                            :txtEmail.equals("")
                            ?"Please Enter a Email"
                            : "Please Select a Course";
                    Toast.makeText(Home.this,msg,Toast.LENGTH_SHORT).show();
                } else {
                    if (txtEmail.contains(".com") && txtEmail.contains("@") && txtEmail.indexOf('@') < txtEmail.indexOf('.') && txtEmail.indexOf('@') > 0) {

                        Boolean checkupdatedata;//to check whether data inserted
                        checkupdatedata = db.updateStudentData(txtRegistration, txtEmail, txtCourse);
                        if (checkupdatedata == true) {
                            Toast.makeText(Home.this, "Student Updated", Toast.LENGTH_SHORT).show();
                            reset();
                            lastReg();
                        } else
                            Toast.makeText(Home.this, "Please Enter a valid Registration Number", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Home.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Event for view button
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openviewStudents();
            }
        });



        //search and load data to textboxes
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtRegistration = search.getText().toString();
                Cursor dataSet=db.getSingleData(txtRegistration);
                if (txtRegistration.equals("")){
                    Toast.makeText(Home.this,"Enter a registration number to search",Toast.LENGTH_SHORT).show();
                }else{
                    if (dataSet!=null) {
                        while (dataSet.moveToNext()) {
                            int index;
                            index = dataSet.getColumnIndexOrThrow("name");
                            String dbname = dataSet.getString(index);
                            index = dataSet.getColumnIndexOrThrow("email");
                            String dbemail = dataSet.getString(index);
                            index = dataSet.getColumnIndexOrThrow("dob");
                            String dbdob = dataSet.getString(index);
                            index = dataSet.getColumnIndexOrThrow("gender");
                            String dbgender = dataSet.getString(index);
                            index = dataSet.getColumnIndexOrThrow("course");
                            String dbcourse = dataSet.getString(index);
                            index = dataSet.getColumnIndexOrThrow("registration");
                            String dbregistration = dataSet.getString(index);


                            name.setText(dbname);
                            email.setText(dbemail);
                            dob.setText(dbdob);


                            if (dbgender.equals("Male")) {
                                genderGroup.check(R.id.Male);
                            } else {
                                genderGroup.check(R.id.Female);
                            }
                            selectCourse(dbcourse);
                            variable = registration.getKeyListener();
                            registration.setText(dbregistration);
                            registration.setKeyListener(null);
                        }
                    } else {
                        Toast.makeText(Home.this, "No student on this searched id", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void openviewStudents(){
        Intent intent =new Intent(this,viewStudents.class);
        startActivity(intent);
    }
    public void selectCourse(String dbcourse){
//        if(dbcourse=="HNDIT") {
//            course.setSelection(1);
//        }else if(dbcourse=="HNDA"){
//            course.setSelection(2);
//        }else if(dbcourse=="HNDE"){
//            course.setSelection(3);
//        }else if(dbcourse=="HNDPM"){
//            course.setSelection(4);
//        }else{
//            course.setSelection(0);
//        }
        int sel=0;
        switch(dbcourse){
            case "HNDIT":sel=1;break;
            case "HNDA":sel=2;break;
            case "HNDE":sel=3;break;
            case "HNDPM":sel=4;break;
        }
        course.setSelection(sel);
    }

    //to load registration number
    public void lastReg(){
        if(db.retLastReg()!=null){
            String regNo=db.retLastReg();
            regNo=String.valueOf((Integer.parseInt(regNo))+1);
            int frontZeros=4-regNo.length();
            for(int i=0;i<frontZeros;i++){
                regNo="0"+regNo;
            }
            registration.setText(regNo);
        }else{
            registration.setText("0001");
        }
    }

    public void reset(){
        search.setText("");
        name.setText("");
        email.setText("");
        dob.setText("");
        genderGroup.check(-1);
        course.setSelection(0);

        registration.setKeyListener(variable);
        registration.setText("");
    }



}