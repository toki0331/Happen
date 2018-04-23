package com.example.toki.merge_include;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private Toolbar toolbar;
    CircleImageView profile,exit;
    TextView name,email;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profile = findViewById(R.id.header_image);
        name = findViewById(R.id.header_name);
        email = findViewById(R.id.header_email);
        exit = findViewById(R.id.header_exit);


        //////// settig the Textview id ////////////////////////////////
        final TextView textView = findViewById(R.id.title);

        //////// seting the toolbar ///////////////////////////////
        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        ///////add the Tab layout(Fragment)/////////////////
        Fragment_SectionsPagerAdapter sectionsPagerAdapter = new Fragment_SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);
        final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.selector1);
        tabLayout.getTabAt(1).setIcon(R.drawable.selector2);
        tabLayout.getTabAt(2).setIcon(R.drawable.selector3);
        //set title in ActionBar
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        textView.setText("HOME");
                        break;
                    case 1:
                        textView.setText("SEARCH");
                        break;
                    case 2:
                        textView.setText("NOTIFICAtION");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        ///set NavigationView in MainActivity////////////

        drawerLayout = findViewById(R.id.dl);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = findViewById(R.id.NavView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.profile) {
                    Intent intent = new Intent(getApplicationContext(), Profile_Main.class);
                    startActivity(intent);
                } else if (id == R.id.login) {
                    Intent intent = new Intent(getApplicationContext(), LogIn_Main.class);
                    startActivity(intent);
                } else if (id == R.id.list) {
                    Intent intent = new Intent(getApplicationContext(), Lists_Main.class);
                    startActivity(intent);
                } else if (id == R.id.moment) {
                    Intent intent = new Intent(getApplicationContext(), Moment_Main.class);
                    startActivity(intent);
                } else if (id == R.id.createAccount) {
                    Intent intent = new Intent(getApplicationContext(), CreateAccount_Main.class);
                    startActivity(intent);
                } else if (id == R.id.setting) {
                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.privacy) {
                    Toast.makeText(MainActivity.this, "Privacy", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.helpCenter) {
                    Intent intent = new Intent(getApplicationContext(), HelpCenter_Main.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(),"See you again",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),LogIn_Main.class);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) ||
                super.onOptionsItemSelected(item);
    }

    public void disableNavigation(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        Fragment_SectionsPagerAdapter adapter = new Fragment_SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHome());

        adapter.addFragment(new FragmentNotification());
        adapter.addFragment(new FragmentSearch());
        viewPager.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        try{
            firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                if (user.getPhotoUrl() != null) {
                    Glide.with(this).load(user.getPhotoUrl().toString()).into(profile);
                }
                if (user.getDisplayName() != null) {
                    name.setText(user.getDisplayName());

                }
                if (user.getEmail() != null) {
                    email.setText(user.getEmail());
                }

            }
        }else{
            Intent intent = new Intent(this, LogIn_Main.class);
            startActivity(intent);

        }
    }catch (Exception e){
            Toast.makeText(this,""+e, Toast.LENGTH_SHORT).show();
        }

}}

