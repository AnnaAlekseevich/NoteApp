package com.example.notes.ui.activities.noteslist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ContextMenuRecyclerView extends RecyclerView {

    private RecyclerViewContextMenuInfo mContextMenuInfo;

    public ContextMenuRecyclerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public ContextMenuRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContextMenuRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int longPressPosition = getChildPosition(originalView);
        if (longPressPosition >= 0) {
            final long longPressId = getAdapter().getItemId(longPressPosition);
            mContextMenuInfo = new RecyclerViewContextMenuInfo(longPressPosition, longPressId);
            return super.showContextMenuForChild(originalView);
        }
        return false;
    }

    public class RecyclerViewContextMenuInfo implements ContextMenu.ContextMenuInfo {

        public RecyclerViewContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }

        final public int position;
        final public long id;
    }
}
