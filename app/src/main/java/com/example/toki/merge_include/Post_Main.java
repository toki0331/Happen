package com.example.toki.merge_include;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.MediaStore.Images.Media.insertImage;

public class Post_Main extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    Button close, post;
    ImageView photo, location, imageView;
    FirebaseAuth firebaseAuth;
    CircleImageView circleImageView;
    EditText editText;
    String name,time,time2;
    String Photourl="1";
    String userPhoto;
    ProgressDialog progressDialog;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_main);
        close = findViewById(R.id.btnclose);
        post = findViewById(R.id.btnhappen);
        photo = findViewById(R.id.postPhoto);
        location = findViewById(R.id.location);
        imageView = findViewById(R.id.image);
        circleImageView = findViewById(R.id.post_profileImage);
        editText = findViewById(R.id.post_text);

        progressDialog=new ProgressDialog(this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postText();

            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                if (user.getPhotoUrl() != null) {
                    userPhoto=user.getPhotoUrl().toString();
                    Glide.with(this).load(userPhoto).into(circleImageView);
                }
                if (user.getDisplayName() != null) {
                    name = user.getDisplayName().toString();
                }
            }
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Post_Main.this, "coming soon...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri photouri = null;
            Bitmap bitmap,bitmap1;
            if (data != null) {

                photouri=data.getData();
                try {

                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(photouri, "r");

                    if (parcelFileDescriptor != null) {
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        bitmap1=Bitmap.createScaledBitmap(bitmap,250,200,false);
                        imageView.setImageBitmap(bitmap1);
                        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");
                        if (photouri != null) {
                                progressDialog.setMessage("loading...");
                                progressDialog.show();
                            profileImageRef.putFile(photouri)

                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            Photourl = taskSnapshot.getDownloadUrl().toString();
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void postText() {

        timestamp();
        final String text = editText.getText().toString();
        if (text.isEmpty()) {
            editText.setError("name required");
            editText.requestFocus();
            return;
        }

        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(time).child("Name");
        currentUserDb.setValue(name);

        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child((time)).child("Text");
        currentUserDb.setValue(text);

        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child((time)).child("timekey");
        currentUserDb.setValue(time);


        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(time).child("time");
        currentUserDb.setValue(time2);


        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(time).child("Photo");
        currentUserDb.setValue(Photourl);

        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(time).child("NamePhoto");
        currentUserDb.setValue(userPhoto);



        finish();


        }

    public String timestamp() {

        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdHHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss d/MM/yyyy");
          long time1=Long.parseLong(sdf1.format(date));
          time=String.valueOf(99999999999999l-time1);

            time2=sdf2.format(date);
            return time;
    }

}

