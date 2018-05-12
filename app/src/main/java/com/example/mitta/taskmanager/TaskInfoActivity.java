package com.example.mitta.taskmanager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.AsyncTask;
import com.example.mitta.taskmanager.database.AppDatabase;
import com.example.mitta.taskmanager.database.Task;

import java.lang.ref.WeakReference;

// Created by Tasos Mittas

// Task Information Activity. Displays the task and the additional information when a task is tapped on the Main Activity.
public class TaskInfoActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    private int taskId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);

        // Title and back button
        setTitle("Task Information");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        taskId = intent.getIntExtra("Id",0);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        new getItemAsyncTask(this, appDatabase).execute(taskId);



    }


    // Returns to home activity when back button is pressed.
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    // AsyncTask that gathers the information needed from the database
    private static class getItemAsyncTask extends AsyncTask<Integer,Void,Task>{

        private AppDatabase database;


        private WeakReference<TaskInfoActivity> activityReference;

        // Only retain a weak reference to the activity
        getItemAsyncTask(TaskInfoActivity context, AppDatabase appDatabase) {
            database = appDatabase;
            activityReference = new WeakReference<>(context);
        }

        // Get the task in the background thread
        @Override
        protected Task doInBackground(Integer... integers) {
           return database.taskModel().getSelectedTask(integers[0]);
        }

        // Set the information on the textviews when the background thread is finished
        @Override
        protected void onPostExecute(Task result) {

            TaskInfoActivity activity = activityReference.get();

            String taskTitle = result.getTask();
            String taskInfo = result.getTaskInfo();


            TextView taskView = activity.findViewById(R.id.taskView);
            TextView infoView = activity.findViewById(R.id.infoView);

            taskView.setText(taskTitle);
            infoView.setText(taskInfo);
        }

    }
}
