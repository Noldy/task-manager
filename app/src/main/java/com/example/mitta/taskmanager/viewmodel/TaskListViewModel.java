package com.example.mitta.taskmanager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.os.AsyncTask;

import com.example.mitta.taskmanager.OnIntegerListener;
import com.example.mitta.taskmanager.database.AppDatabase;
import com.example.mitta.taskmanager.database.Task;

import java.util.List;

// Created by Tasos Mittas

// The Main ViewModel. Retrieves Completed and not Completed tasks, as well as the size of the Completed Tasks list.
public class TaskListViewModel extends AndroidViewModel{

    private AppDatabase appDatabase;
    private final LiveData<List<Task>> taskList;
    private final LiveData<List<Task>> completedTaskList;
    private static int listSize;


    public TaskListViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        taskList = appDatabase.taskModel().getAllTasks();
        completedTaskList = appDatabase.taskModel().getCompletedTasks();
        getListSize();
    }

    public LiveData<List<Task>> getTaskList(){
        return taskList;
    }

    public LiveData<List<Task>> getCompletedTaskList() {
        return completedTaskList;
    }




    // Used the OnIntegerListener interface to return the value from the background thread
    public int getListSize(){
        new getSizeAsyncTask(appDatabase, new OnIntegerListener() {
            @Override
            public void onTaskComplete(Integer i) {
                listSize = i;
            }
        }).execute();
        return listSize;
    }



    // Async Task for the getListSize() method
    private static class getSizeAsyncTask extends AsyncTask<Void,Void,Integer>{

        private AppDatabase database;
        private OnIntegerListener listener;

        getSizeAsyncTask(AppDatabase appDatabase,OnIntegerListener onIntegerListener) {
            database = appDatabase;
            listener = onIntegerListener;
        }

        // Get the list size from the background thread.
        @Override
        protected Integer doInBackground(Void... voids) {
            return database.taskModel().getListSize();
        }

        // Pass the return value on the listener.
        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            listener.onTaskComplete(i);
        }
    }
}
