package com.example.notes.ui.activities.createnotefragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.NoteActivity;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseNoteFragment extends Fragment implements NoteViewHandler {

    protected Note note;
    private static final String BaseNoteFragment = "BaseNoteFragmentLOG";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        note = getArguments().getParcelable("ARG_NOTE");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drawNote(note);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(note.basket ? R.menu.basket_menu : R.menu.notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuStar = menu.findItem(R.id.star);
        if (menuStar != null) {
            changeStarItemView(menuStar, note.favorites);
        }
    }

    private void changeStarItemView(MenuItem starItem, boolean isFavorite) {
        starItem.setChecked(isFavorite);
        starItem.setIcon(!isFavorite ? R.drawable.ic_star_border_black_24dp : R.drawable.ic_star_black_24dp);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.star:
                changeStarItemView(item, !item.isChecked());
                if (item.isChecked()) {
                    Log.d(BaseNoteFragment, "star before checked");
                    note.favorites = true;
                    Log.d(BaseNoteFragment, "star is true");
                } else if (!item.isChecked()) {
                    Log.d(BaseNoteFragment, "star after checked");
                    note.favorites = false;
                    Log.d(BaseNoteFragment, "star is false");
                }
                return true;
            case R.id.basket:
                note.basket = true;
                ((NoteActivity) getActivity()).saveNote(getFilledNote(), true);
                return true;
            case R.id.basket_return_back:
                note.basket = false;
                ((NoteActivity) getActivity()).saveNote(getFilledNote(), true);
                return true;
            case R.id.basket_remove:
                NotesApp.getInstance().getDatabaseManager().deleteNote(note)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                                getActivity().finish();
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void drawNote(Note note);

}
