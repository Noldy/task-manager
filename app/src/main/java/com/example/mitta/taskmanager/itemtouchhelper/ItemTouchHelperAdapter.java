package com.example.mitta.taskmanager.itemtouchhelper;

//Created by Tasos Mittas
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}
