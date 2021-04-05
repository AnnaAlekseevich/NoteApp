package com.example.notes.ui.activities;

import android.util.Log;

import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.noteslist.BaseNotesListFragment;
import com.example.notes.ui.activities.noteslist.BasketListFragment;
import com.example.notes.ui.activities.noteslist.FavoriteListFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public static final int CARD_ITEM_SIZE = 4;
    private static final String TAG = "favorites";

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
                Log.d(TAG, "Text");
                break;
            case 2:
                noteType = NoteType.List;
                Log.d(TAG, "List");
                break;
            case 3:
                noteType = NoteType.Reminder;
                break;
        }

        return BaseNotesListFragment.newInstance(noteType);
    }

    @Override
    public int getItemCount() {
        return CARD_ITEM_SIZE;
    }

    public static CharSequence getPageTitle(int position) {
        CharSequence title = null;
        switch (position) {
            case 0:
                return title = "Всё";
            case 1:
                return title = "Заметки";
            case 2:
                return title = "Списки";
            case 3:
                return title = "Напоминания";
            case 4:
            default:
                return "NEW";
        }
    }

}
