package com.example.mitta.taskmanager;

// Used in TaskListViewModel activity to get the result of the getListSize() query from the background thread.
public interface OnIntegerListener {

    void onTaskComplete(Integer i);

}
