package com.example.mitta.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

// Created by Tasos Mittas

// Simple activity used for showing the developer's information.
public class AboutActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    private String TAG = "NavigationMenu";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Title, Toolbar and Navigation Menu Icon
        setTitle("About");
        Toolbar toolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //TextView
        String about = "App created by Tasos Mittas\n\nEmail: mittast@gmail.com\n\nThis app is made for the purpose of practising on " +
                "Room, RecyclerView, ViewHolders, Adapters and general techniques used to make apps in Android Studio.";

        TextView textView = findViewById(R.id.aboutTextView);
        textView.setText(about);

        // Navigation Menu
        drawerLayout = findViewById(R.id.about_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view_about);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.navigation_tasks:
                        final Intent tasksIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(tasksIntent);
                        break;
                    case R.id.navigation_completed:
                        final Intent completedTasksIntent = new Intent(getApplicationContext(), CompletedTasksActivity.class);
                        startActivity(completedTasksIntent);
                        break;
                    case R.id.navigation_about:
                        final Intent aboutIntent = new Intent(getApplicationContext(), AboutActivity.class);
                        startActivity(aboutIntent);
                        break;
                    default:
                        Log.i(TAG, "Error With Navigation Menu");
                        break;
                }
                return false;
            }
        });
    }

    // Open navigation menu when navigation menu icon is tapped
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}