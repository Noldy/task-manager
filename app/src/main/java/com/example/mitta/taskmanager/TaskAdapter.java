package com.example.mitta.taskmanager;

import android.app.Application;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mitta.taskmanager.database.Task;
import com.example.mitta.taskmanager.itemtouchhelper.ItemTouchHelperAdapter;
import com.example.mitta.taskmanager.viewmodel.DeleteTaskViewModel;
import com.example.mitta.taskmanager.viewmodel.UpdateTaskViewModel;


import java.util.Collections;
import java.util.List;

// Created by Tasos Mittas

// The adapter for the Main Activity.
class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private List<Task> tasks;
    Application application;

    public interface OnItemClickListener{
        void onItemClick(Task task);
    }

    private  OnItemClickListener listener;

    public TaskAdapter(List<Task> tasks, OnItemClickListener listener){
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, final int position) {
        holder.firstTask.setText(tasks.get(position).getTask());
        holder.itemView.setTag(position);
        holder.bind(tasks.get(position), listener);

        // CheckBox listener. When a checkbox is clicked, calls the completeItem query to update the database.
        holder.checkBoxState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean isChecked) {
                if(isChecked){
                    Log.i("CheckBox","Turn to true");
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            UpdateTaskViewModel updateTaskViewModel = new UpdateTaskViewModel(application);
                            updateTaskViewModel.completeItem(tasks.get(position));
                            notifyItemRemoved(position);
                        }
                    }, 1000);

                }
                else{
                    Log.i("CheckBox", "Do nothing");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addItems(List<Task> taskList) {
        tasks = taskList;
        notifyDataSetChanged();
    }



    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstTask;
        public CheckBox checkBoxState;

        public ViewHolder(View itemView) {
            super(itemView);
            firstTask = itemView.findViewById(R.id.first_task);
            checkBoxState = itemView.findViewById(R.id.checkBox);
        }



        // Sets a listener for the RecyclerView items.
        public void bind(final Task task, final OnItemClickListener listener){

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(task);
                }
            });
        }
    }



    // ItemTouchHelperAdapter methods
    public boolean onItemMove(int fromPosition, int toPosition){

        if(fromPosition < toPosition){
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(tasks, i, i+1);
            }
        }
        else{
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(tasks, i, i-1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void onItemDismiss(int position){
        DeleteTaskViewModel deleteTaskViewModel = new DeleteTaskViewModel(application);
        deleteTaskViewModel.deleteItem(tasks.get(position));
    }



}
