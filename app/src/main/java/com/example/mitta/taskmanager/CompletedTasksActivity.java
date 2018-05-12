package com.example.mitta.taskmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mitta.taskmanager.database.Task;
import com.example.mitta.taskmanager.viewmodel.TaskListViewModel;

import java.util.ArrayList;
import java.util.List;

// Created by Tasos Mittas

// Shows a list of all the completed tasks as well as their total number
public class CompletedTasksActivity extends AppCompatActivity {

    private CompletedTaskAdapter adapter;
    protected DrawerLayout drawerLayout;
    private String TAG = "NavigationMenu";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);

        // Title, Toolbar and Navigation Menu Icon
        setTitle("Completed Tasks");
        Toolbar toolbar = findViewById(R.id.completed_tasks_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.completedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Adapter
        adapter = new CompletedTaskAdapter(new ArrayList<Task>());
        recyclerView.setAdapter(adapter);


        // Observer
        TaskListViewModel viewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);
        viewModel.getCompletedTaskList().observe(CompletedTasksActivity.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> taskList) {
                adapter.addItems(taskList);
            }
        });


        // Navigation Menu
        drawerLayout =  findViewById(R.id.completed_drawer_layout);
        NavigationView navigationView =  findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
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


        // Number of completed tasks TextView
        TextView completedTextView = findViewById(R.id.tasksComletedTextView);
        int listSize= viewModel.getListSize();
        String completedItemsString = listSize + " Tasks Completed";
        completedTextView.setText(completedItemsString);

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
