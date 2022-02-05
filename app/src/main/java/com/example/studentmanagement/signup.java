package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {
    EditText user,pass;
    Button signup;
    studentDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        user =findViewById(R.id.txtUsername);
        pass =findViewById(R.id.txtPassword);
        signup= (Button)findViewById(R.id.btnSignup);
        db = new  studentDB(this);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String txtUsername=user.getText().toString();
                String txtPassword=pass.getText().toString();

                String msg=db.newSignup(txtUsername,txtPassword)?"Signup Successful":"Signup Failed";
                Toast.makeText(signup.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
}