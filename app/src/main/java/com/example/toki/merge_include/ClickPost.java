package com.example.toki.merge_include;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClickPost extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    CircleImageView circleImageView;
    EditText editText;
    String repName,time,time2;
    String Photourl="1";
    String userPhoto,path;
    ProgressDialog progressDialog;

    FloatingActionButton post;

    TextView cardName,cardText,cardTime;
    ImageView iv,cardProfile,cardPhoto,photo, location, imageView;
    String timekey,everytimekey;


    RecyclerView repRecyclerView;
    RecyclerView.Adapter adapter;

    FirebaseAuth firebaseAuth;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);



        iv = findViewById(R.id.iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        photo = findViewById(R.id.postPhoto);
        location = findViewById(R.id.location);
        imageView = findViewById(R.id.image);
        circleImageView = findViewById(R.id.post_profileImage);
        editText = findViewById(R.id.post_text);
        post=findViewById(R.id.floatingActionButton);
        repRecyclerView=findViewById(R.id.repRecycleView);

        progressDialog=new ProgressDialog(this);


        cardName=findViewById(R.id.Name);
        cardTime=findViewById(R.id.time);
        cardText=findViewById(R.id.text);
        cardProfile=findViewById(R.id.cardImage);
        cardPhoto=findViewById(R.id.imageCard);

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
                    repName = user.getDisplayName().toString();
                }
            }
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "coming soon...", Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = getIntent();
        timekey = intent.getStringExtra("toki");
        Toast.makeText(getBaseContext(), "timekey "+timekey, Toast.LENGTH_SHORT).show();


        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Time");
        DatabaseReference repref=FirebaseDatabase.getInstance().getReference().child("Time").child(timekey);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                everytimekey = dataSnapshot.getKey();

                if (everytimekey.equals(timekey)) {
                    getOneData(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        repref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showRepData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, ""+databaseError, Toast.LENGTH_SHORT).show();
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
            editText.setError("text required");
            editText.requestFocus();
            return;
        }

        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey).child("rep").child(time).child("Name");
        currentUserDb.setValue(repName);

        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey).child("rep").child((time)).child("Text");
        currentUserDb.setValue(text);

        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey).child("rep").child((time)).child("timekey");
        currentUserDb.setValue(time);


        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey).child("rep").child(time).child("time");
        currentUserDb.setValue(time2);


        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey).child("rep").child(time).child("Photo");
        currentUserDb.setValue(Photourl);

        currentUserDb = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey).child("rep").child(time).child("NamePhoto");
        currentUserDb.setValue(userPhoto);



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


    private void getOneData(DataSnapshot ds) {

        cardName.setText(ds.child("Name").getValue(String.class));
        cardText.setText(ds.child("Text").getValue(String.class));
        cardTime.setText(ds.child("time").getValue(String.class));

        try {
            Glide.with(this).load(Uri.parse(ds.child("Photo").getValue(String.class))).into(cardPhoto);
        } catch (NullPointerException n) {
            cardPhoto.setImageBitmap(null);
        }
        try {
            Glide.with(this).load(Uri.parse(ds.child("NamePhoto").getValue(String.class))).into(cardProfile);
        } catch (NullPointerException n) {
            cardProfile.setImageBitmap(null);
        }
    }



    private void showRepData(DataSnapshot dataSnapshot) {
        ArrayList<AlbumRepRecycleView> replist = new ArrayList<>();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Uri postPhoto;
            Uri namePhoto;


            String name = ds.child("Name").getValue(String.class);
            String text = ds.child("Text").getValue(String.class);
            final String time = ds.child("time").getValue(String.class);


            try {
                postPhoto = Uri.parse(ds.child("Photo").getValue(String.class));
            } catch (NullPointerException n) {
                postPhoto = null;
            }
            try {
                namePhoto = Uri.parse(ds.child("NamePhoto").getValue(String.class));
            } catch (NullPointerException n) {
                namePhoto = null;
            }

                try {
                    replist.add(new AlbumRepRecycleView("" + namePhoto, "" + name, "" + time, "" + text, "" + postPhoto));
                } catch (Exception e) {
                    replist.add(new AlbumRepRecycleView("" + namePhoto, "" + name, "" + time, "" + text, null));
                }
                adapter = new AdapterRepRecycleView(replist, this);
                repRecyclerView.setAdapter(adapter);
            }
        }
}

