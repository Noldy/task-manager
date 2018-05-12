package com.example.mitta.taskmanager.itemtouchhelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

//Created by Tasos Mittas

// Standard ItemTouchHelper movement methods for recycler view items
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter touchAdapter;

    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter){
        touchAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        touchAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        touchAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled(){
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled(){
        return true;
    }

}
