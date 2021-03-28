package com.example.notes.ui.activities.noteslist;

import android.util.Log;
import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;
import java.util.List;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FavoriteListFragment extends BaseNotesListFragment {

    private static final String TAG = "favoritesLOG";

    @Override
    public String getTitle() {
        return getResources().getString(R.string.favorites);
    }

    @Override
    public void loadNotes() {
        Flowable<List<Note>> getNotesRequest =
                NotesApp.getInstance().getDatabaseManager().getFavoriteNotes() ;

        getNotesRequest
                .subscribeOn(Schedulers.io())//thread pool
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {

                    @Override
                    public void accept(List<Note> notes) {
                        onNotesLoaded(notes);
                        Log.d(TAG, "List<Note>= " + notes);
                    }

                });
    }

}
