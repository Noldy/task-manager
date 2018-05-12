package com.example.mitta.taskmanager.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//Created by Tasos Mittas

//Task table. Includes 3 columns; task(string), info(string), and completed(boolean).
@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "task")
    private String task;

    @ColumnInfo(name = "info")
    private String taskInfo;

    @ColumnInfo(name = "completed")
    private boolean isCompleted = false;


    public Task(String task, String taskInfo){
        this.task = task;
        this.taskInfo = taskInfo;
    }


    public int getId(){
        return this.id;
    }

    public String getTask(){
        return this.task;
    }

    public String getTaskInfo(){
        return this.taskInfo;
    }

    public boolean getIsCompleted(){
        return this.isCompleted;
    }

    public void setId(int position){
        this.id = position;
    }

    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }

}
