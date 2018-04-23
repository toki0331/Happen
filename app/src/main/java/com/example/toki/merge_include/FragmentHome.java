package com.example.toki.merge_include;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 * Created by toki on 2017/12/15.
 */

public class FragmentHome extends Fragment {
    private static final String TAG = "FragmentHome";

    FloatingActionButton button;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    FirebaseAuth firebaseAuth;

    Uri rep1,rep2;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);
       recyclerView=view.findViewById(R.id.recycleView);

       recyclerView.setHasFixedSize(true);

       layoutManager =new LinearLayoutManager(getContext());
       recyclerView.setLayoutManager(layoutManager);




        button = view.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Post_Main.class);
                startActivity(intent);

            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Time");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showAllData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error" + databaseError, Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }


    private void showAllData(DataSnapshot dataSnapshot) {
        ArrayList<AlbumRecycleView> list = new ArrayList<>();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Uri postPhoto;
            Uri namePhoto;
            String timekey;


            try {
                timekey = ds.child("timekey").getValue(String.class);
            } catch (Exception e) {
                timekey = null;
            }

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

            if (dataSnapshot.getChildrenCount() > 6) {
                if (timekey != null) {
                  //  repList.add(timekey);

                    DatabaseReference repref = FirebaseDatabase.getInstance().getReference().child("Time").child(timekey);

                    repref.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            ArrayList<Uri> repArray=new ArrayList<>();

                            for (DataSnapshot datasnap : dataSnapshot.getChildren()) {
                             //   repArray.add(Uri.parse(datasnap.child("NamePhoto").getValue(String.class)));

                            }

                            if (repArray.size() >= 2) {
                                rep1 = repArray.get(0);
                                rep2 = repArray.get(1);
                            }
                            if (repArray.size() == 1) {
                                rep1 = repArray.get(0);
                                rep2 = null;
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

                }



                try {
                    list.add(new AlbumRecycleView("" + namePhoto, "" + name, "" + time, "" + text, "" + postPhoto, "" + timekey, "" + rep1, "" + rep2));
                } catch (Exception e) {
                    list.add(new AlbumRecycleView("" + namePhoto, "" + name, "" + time, "" + text, null, "" + timekey, "" + rep1, "" + rep2));
                }
                adapter = new AdapterRecycleView(list, getContext());
                recyclerView.setAdapter(adapter);
            }

        }
    }


}
