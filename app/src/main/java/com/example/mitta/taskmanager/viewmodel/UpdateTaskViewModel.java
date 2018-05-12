package com.example.mitta.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import com.example.mitta.taskmanager.database.AppDatabase;
import com.example.mitta.taskmanager.database.Task;

// Created by Tasos Mittas

// ViewModel for the updateTask query
public class UpdateTaskViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public UpdateTaskViewModel(Application application){
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    // Mark a task in the database as completed
    public void completeItem(Task task){
        new UpdateAsyncTask(appDatabase).execute(task);
    }

    // Async Task. Updates an item as completed in the background thread
    private static class UpdateAsyncTask extends AsyncTask<Task,Void,Void>{

        private AppDatabase database;

        UpdateAsyncTask(AppDatabase appDatabase) {
            database = appDatabase;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            Task task = tasks[0];
            database.taskModel().completeTask(task.getId());
            return null;
        }
    }

}
