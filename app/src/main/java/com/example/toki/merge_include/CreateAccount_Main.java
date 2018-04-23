package com.example.toki.merge_include;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class CreateAccount_Main extends AppCompatActivity {
    Button button;
    EditText email,password;
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account__main);

        imageView=findViewById(R.id.iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button=findViewById(R.id.btnCreate);
        email=findViewById(R.id.Loginemail);
        password=findViewById(R.id.LoginPassword);

        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerFirebase();

            }
        });
    }


    private void registerFirebase() {

        String e=email.getText().toString().trim();
        String p=password.getText().toString().trim();

        if (TextUtils.isEmpty(e)) {
            Toast.makeText(CreateAccount_Main.this, "please fill the email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(p)) {
            Toast.makeText(CreateAccount_Main.this, "please fill the password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("checking....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(e,p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(getApplication(),Profile_Main.class);
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(CreateAccount_Main.this, "Welcome", Toast.LENGTH_SHORT).show();
                        }else if(task.getException() instanceof FirebaseAuthUserCollisionException){

                            Toast.makeText(CreateAccount_Main.this, "this email is already register", Toast.LENGTH_SHORT).show();
                        }else {


                            Toast.makeText(CreateAccount_Main.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
