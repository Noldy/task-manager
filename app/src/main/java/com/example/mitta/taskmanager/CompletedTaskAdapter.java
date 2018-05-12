package com.example.mitta.taskmanager;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mitta.taskmanager.database.Task;

import java.util.List;

// Created by Tasos Mittas

// The adapter for the CompletedTasks Activity
class CompletedTaskAdapter extends RecyclerView.Adapter<CompletedTaskAdapter.ViewHolder>{

    private List<Task> tasks;


    public  CompletedTaskAdapter(List<Task> tasks){
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public CompletedTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskAdapter.ViewHolder holder, int position) {

        holder.firstTask.setText(tasks.get(position).getTask());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addItems(List<Task> taskList) {
        tasks = taskList;
        notifyDataSetChanged();
    }


    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView firstTask;


        public ViewHolder(View itemView) {
            super(itemView);
            firstTask = itemView.findViewById(R.id.first_task);
        }


    }
}
