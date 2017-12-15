package com.example.toki.merge_include;

import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disableNavigation(navigationView);

        drawerLayout=findViewById(R.id.dl);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);

        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        NavigationView navigationView=findViewById(R.id.NavView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.profile){
                    Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.list) {
                    Toast.makeText(MainActivity.this, "Lists", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.moment) {
                        Toast.makeText(MainActivity.this, "Moments", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.hightlights) {
                        Toast.makeText(MainActivity.this, "Hightlights", Toast.LENGTH_SHORT).show();
                    }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||
                super.onOptionsItemSelected(item);
    }

    public void disableNavigation(NavigationView navigationView){
        if(navigationView!=null){
            NavigationMenuView navigationMenuView= (NavigationMenuView) navigationView.getChildAt(0);
            if(navigationMenuView!=null){
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}

