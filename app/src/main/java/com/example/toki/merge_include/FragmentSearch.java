package com.example.toki.merge_include;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by toki on 2017/12/15.
 */

public class FragmentSearch extends Fragment{
    private static final String TAG = "FragmentHome";
    private ListView listview;
    Toolbar toolbar;
    TextView textView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mRootReference = firebaseDatabase.getReference();
    DatabaseReference mUser = mRootReference.child("Time");
    DatabaseReference Users;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_home, container, false);
        listview = (ListView) view.findViewById(R.id.listView);


//        firebaseAuth= FirebaseAuth.getInstance();
//        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Time");
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                collectData((Map<String,Object>) dataSnapshot.getValue());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//        return view;
//    }
//
//    private void collectData(Map<String, Object> users){
//        ArrayList<Card> list=new ArrayList<>();
//
//        for(Map.Entry<String,Object> entry : users.entrySet()){
//            Map singleUser= (Map) entry.getValue();
//
//
//
//
//
//            String name=(String) singleUser.get("Name");
//            String time=(String) singleUser.get("time");
//            String text=(String) singleUser.get("Text");
//
//
//            try{
//                list.add(new Card(""+ Uri.parse((String) singleUser.get("NamePhoto")),""+name,""+time,""+text,""+Uri.parse((String) singleUser.get("Photo"))));
//            }catch (Exception e){
//                list.add(new Card(""+Uri.parse((String) singleUser.get("NamePhoto")),""+name,""+time,""+text,null));
//
//            }
//
//        }
//        CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.layout_card_pic, list);
//        listview.setAdapter(adapter);
//    }
        return view;
    }
}
