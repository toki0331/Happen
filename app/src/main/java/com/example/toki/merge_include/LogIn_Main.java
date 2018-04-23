package com.example.toki.merge_include;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Main extends AppCompatActivity {
    Button button;
    ImageView imageView;
    TextView textView;
    EditText email,password;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__main);
        button=findViewById(R.id.btnLogin);
        textView=findViewById(R.id.forgetPassword);
        email=findViewById(R.id.Loginemail);
        password=findViewById(R.id.LoginPassword);
        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        imageView=findViewById(R.id.iv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFireBase();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();     }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),CreateAccount_Main.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            }
        });
    }

    private void LoginFireBase() {
        String e=email.getText().toString().trim();
        String p=password.getText().toString().trim();

        if (TextUtils.isEmpty(e)) {

            Toast.makeText(getApplicationContext(), "please enter Name", Toast.LENGTH_SHORT).show();
            return;

        }

        if (TextUtils.isEmpty(p)) {

            Toast.makeText(getApplicationContext(), "please enter Email", Toast.LENGTH_SHORT).show();
            return;

        }

        progressDialog.setMessage("Checking...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    Toast.makeText(LogIn_Main.this, "enter successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LogIn_Main.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
