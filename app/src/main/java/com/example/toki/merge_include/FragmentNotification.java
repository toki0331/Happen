package com.example.toki.merge_include;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by toki on 2017/12/15.
 */

public class FragmentNotification extends Fragment {
    private static final String TAG = "FragmentNotification";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.ativity_fragment_notification,container,false);
        return view;
    }
}
