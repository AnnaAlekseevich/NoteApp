package com.example.notes.ui.activities;

import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.noteslist.NotesListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public static final int CARD_ITEM_SIZE = 4;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        NoteType noteType = null;
        switch (position) {
            case 1:
                noteType = NoteType.Text;
                break;
            case 2:
                noteType = NoteType.List;
                break;
            case 3:
                noteType = NoteType.Reminder;
                break;
        }

        return NotesListFragment.newInstance(noteType);
    }

    @Override
    public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}
