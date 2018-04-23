package com.example.toki.merge_include;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.FileDescriptor;
import java.io.IOException;


public class Profile_Main extends AppCompatActivity {
    Button button;
    EditText editText;
    ImageView imageView,photo;
    private static final int READ_REQUEST_CODE=42;
    String name;
    String  photourl;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__main);

        button = findViewById(R.id.profile_btn);
        editText = findViewById(R.id.profile_name);
        photo = findViewById(R.id.profile_image);

        imageView = findViewById(R.id.iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Setting...");
                progressDialog.show();
                saveUserInfor();
            }
        });
    }


    private void saveUserInfor() {

        name = editText.getText().toString().trim();
        if(name.isEmpty()){
            editText.setError("userId required");
            editText.requestFocus();
            return;
        }

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null && photourl!=null){
            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(Uri.parse(photourl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                               Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                            }else{
                                Toast.makeText(Profile_Main.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == READ_REQUEST_CODE && resultCode== Activity.RESULT_OK ) {
            Uri photouri=null;
            if(data!=null){
                try{
                    progressDialog.setMessage("loading...");
                    progressDialog.show();
                    photouri=data.getData();
                    ParcelFileDescriptor parcelFileDescriptor=getContentResolver().openFileDescriptor(photouri,"r");
                    if(parcelFileDescriptor!=null){
                        FileDescriptor fileDescriptor=parcelFileDescriptor.getFileDescriptor();
                        Bitmap bitmap= BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();
                        photo.setImageBitmap(bitmap);

                        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
                        if (photouri != null) {

                            profileImageRef.putFile(photouri)

                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            photourl = taskSnapshot.getDownloadUrl().toString();
                                            progressDialog.dismiss();

                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }

                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

}



