package com.example.mitta.taskmanager;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mitta.taskmanager.database.Task;
import com.example.mitta.taskmanager.viewmodel.InsertTaskViewModel;

// Created by Tasos Mittas

// Prompts the user to input a new Task and add some additional information if needed.
public class CreateTaskActivity extends AppCompatActivity{

    private Button createButton;

    private InsertTaskViewModel insertTaskViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // Title and back button
        setTitle("New Task");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create button. On click go to MainActivity and insert the new task in the database.
        insertTaskViewModel = ViewModelProviders.of(this).get(InsertTaskViewModel.class);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText taskView = findViewById(R.id.taskEditTextView);
                EditText infoView = findViewById(R.id.infoEditTextView);

                String thisTask = taskView.getText().toString();
                String thisInfo = infoView.getText().toString();
                Log.i("Created", thisTask + thisInfo);

                insertTaskViewModel.insertItem(new Task(thisTask,thisInfo));

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }


    // Returns to home activity when back button is pressed.
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
