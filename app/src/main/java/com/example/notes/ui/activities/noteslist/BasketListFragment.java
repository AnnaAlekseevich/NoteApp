package com.example.notes.ui.activities.noteslist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BasketListFragment extends BaseNotesListFragment {

    private static final String TAG = "favoritesLOG";
    private ImageButton basketButton;

    @Override
    public String getTitle() {
        return getResources().getString(R.string.basket);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        basketButton = view.findViewById(R.id.Basket_button);

        // создаем обработчик нажатия
        View.OnClickListener basketListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete
                NotesApp.getInstance().getDatabaseManager().clearBasket()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }
                        });

            }
        };

        // присвоим обработчик кнопке
        basketButton.setOnClickListener(basketListener);

        return view;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.busket_layout_fragment;
    }

    @Override
    public void loadNotes() {


        NotesApp.getInstance().getDatabaseManager().getDeletedNotes()
                .subscribeOn(Schedulers.io())//thread pool
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {

                    @Override
                    public void accept(List<Note> notes) {
                        onNotesLoaded(notes);
                        Log.d(TAG, "Deleted List<Note>= " + notes);
                    }

                });
    }


}
