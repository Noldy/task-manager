package com.example.mitta.taskmanager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.mitta.taskmanager.database.Task;
import com.example.mitta.taskmanager.itemtouchhelper.ItemTouchHelperCallback;
import com.example.mitta.taskmanager.viewmodel.TaskListViewModel;
import java.util.ArrayList;
import java.util.List;
import android.view.MenuItem;

// Created by Tasos Mittas

// The app's Main Activity. Shows a list of all the tasks that need to be completed.
public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;
    protected DrawerLayout drawerLayout;
    private String TAG = "NavigationMenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Title, Toolbar and Navigation Menu Icon
        setTitle("Tasks");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));


        // Adapter
        adapter = new TaskAdapter(new ArrayList<Task>(), new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Intent intent = new Intent(getApplicationContext(), TaskInfoActivity.class);
                intent.putExtra("Id", task.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


        // Observer
        TaskListViewModel viewModel = ViewModelProviders.of(this).get(TaskListViewModel.class);
        viewModel.getTaskList().observe(MainActivity.this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> taskList) {
                adapter.addItems(taskList);
            }
        });


        // Touch Helper
        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);



        // FAB
        FloatingActionButton fab =  findViewById(R.id.addTaskButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent createTaskIntent = new Intent(getApplicationContext(), CreateTaskActivity.class);
                startActivity(createTaskIntent);
            }
        });
        fab.setImageResource(R.drawable.add_white);


        // Navigation Menu
        drawerLayout =  findViewById(R.id.drawer_layout);

        NavigationView navigationView =  findViewById(R.id.nav_view);
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
