package com.example.mitta.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.os.AsyncTask;

import com.example.mitta.taskmanager.database.AppDatabase;
import com.example.mitta.taskmanager.database.Task;

// Created by Tasos Mittas

// ViewModel for the Insert Task query
public class InsertTaskViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public InsertTaskViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }


    public void insertItem(Task task){
        new insertAsyncTask(appDatabase).execute(task);
    }

    // AsyncTask. Inserts an item in the background thread.
    private static class insertAsyncTask extends AsyncTask<Task, Void, Void>{

        private AppDatabase database;

        insertAsyncTask(AppDatabase appDatabase){
            database = appDatabase;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            database.taskModel().insertTask(tasks[0]);
            return null;
        }
    }
}
