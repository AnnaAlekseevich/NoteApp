package com.example.notes.ui.activities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.noteslist.BaseNotesListFragment;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public static final int CARD_ITEM_SIZE = 4;
    private static final String TAG = "favorites";
    public String query;

    HashMap<Integer, WeakReference<Fragment>> createdFragments = new HashMap<>();


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
        createdFragments.put(position, new WeakReference<>(fragment));

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

    public void updateQuery(String query) {
        this.query = query;
        Log.d("SearchHandle", "query = " + query);
        for (Integer key : createdFragments.keySet()) {
            WeakReference weakFragment = createdFragments.get(key);
            if(weakFragment.get()!=null){
                ((BaseNotesListFragment)weakFragment.get()).updateQuery(query);
            }

        }
    }


}
