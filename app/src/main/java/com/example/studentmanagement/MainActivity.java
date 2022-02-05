package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText user,pass;
    Button login;
    TextView signup;
    studentDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user =findViewById(R.id.Username);
        pass =findViewById(R.id.Password);
        login= (Button)findViewById(R.id.btnLogin);
        signup=findViewById(R.id.txtSignup);
        db=new studentDB(this);


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String txtUser = user.getText().toString();
                String txtPass = pass.getText().toString();
//                Log.d("user",txtUser+"kmdmk");
//                Log.d("pass",txtPass+"kmdmk");
                if(txtUser.equals("") && txtPass.equals("")){
                    String msg=txtUser.equals("")?"username is required":"Password is required";
                    Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                }
                else {
                    if (db.adminLogin(txtUser, txtPass))
                        openHome();
                    else {
                        String msg="Login Failed Invalid Credentials";
                        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openSignup();
            }
        });
    }

    public void openHome(){
        Intent intent =new Intent(this,Home.class);
        startActivity(intent);
    }

    public void openSignup(){
        Intent intent =new Intent(this,signup.class);
        startActivity(intent);
    }
}