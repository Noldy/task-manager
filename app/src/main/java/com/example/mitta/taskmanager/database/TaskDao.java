package com.example.mitta.taskmanager.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

//Created by Tasos Mittas
@Dao
public interface TaskDao {

    // Select all non-completed tasks. Used in MainActivity's RecyclerView.
    @Query("SELECT * FROM Task WHERE completed == 0")
    LiveData<List<Task>> getAllTasks();

    // Select a specific task given the id.
    @Query("SELECT * FROM Task WHERE id == :taskId")
    Task getSelectedTask(int taskId);

    // Select all completed tasks. Used in CompletedTasksActivity's RecyclerView.
    @Query("SELECT * FROM Task WHERE completed == 1")
    LiveData<List<Task>> getCompletedTasks();

    // Insert a new task. Used in CreateTaskActivity.
    @Insert(onConflict = REPLACE)
    void insertTask(Task task);

    // Delete a specific task. Used when an RecyclerView item gets swiped.
    @Delete
    void deleteTask(Task task);

    // Mark a specific task as completed. Used when an item's checkbox is ticked.
    @Query("UPDATE Task SET completed = 1 WHERE id = :thisId")
    void completeTask(int thisId);

    // Returns the number of completed items in the table. Used for CompletedTaskActivity's TextView.
    @Query("SELECT COUNT(task) FROM Task WHERE completed == 1")
    int getListSize();
}
