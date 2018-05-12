package com.example.mitta.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.example.mitta.taskmanager.database.AppDatabase;
import com.example.mitta.taskmanager.database.Task;

// Created by Tasos Mittas

// ViewModel for item deletion query
public class DeleteTaskViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public DeleteTaskViewModel(Application application){
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void deleteItem(Task task){
        new DeleteAsyncTask(appDatabase).execute(task);
    }


    // Delete Item Async Task
    private static class DeleteAsyncTask extends AsyncTask<Task,Void,Void>{

        private AppDatabase database;

        DeleteAsyncTask(AppDatabase appDatabase) {
            database = appDatabase;
        }
        @Override
        protected Void doInBackground(Task... tasks) {
            database.taskModel().deleteTask(tasks[0]);
            return null;
        }
    }

}
