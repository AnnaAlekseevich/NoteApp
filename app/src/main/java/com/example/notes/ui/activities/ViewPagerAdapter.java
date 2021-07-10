package com.example.notes.ui.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.noteslist.BaseNotesListFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public static final int CARD_ITEM_SIZE = 4;
    private static final String TAG = "favorites";
    public String query;


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

        Fragment fragment = BaseNotesListFragment.newInstance(noteType, query);
        return fragment;

    }


    @Override
    public int getItemCount() {
        return CARD_ITEM_SIZE;
    }

    public static CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Всё";
            case 1:
                return "Заметки";
            case 2:
                return "Списки";
            case 3:
                return "Напоминания";
            case 4:
            default:
                return "NEW";
        }
    }

    public NoteType getCurrentNoteType(int position) {
        switch (position) {
//            case 0:
//                return NoteType.AllNotes;
            case 1:
                return NoteType.Text;
            case 2:
                return NoteType.List;
            case 3:
                return NoteType.Reminder;
            default:
                return NoteType.AllNotes;
        }
    }



}
