package com.example.notes.ui.activities.noteslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.Fragment2;
import com.example.notes.ui.activities.MainActivity;
import com.example.notes.ui.activities.NoteActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotesListFragment extends Fragment implements NoteListItemClickListener {

    private static final String TAG = NotesListFragment.class.getSimpleName();
    private static final String ARG_COUNT = "param1";

    private NotesAdapter notesAdapter;
    //for notes from hint
    EditText et_Name;
    ProgressBar pb_list_notes;
    TextView tv_error;


    public static NotesListFragment newInstance(Integer counter) {
        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, counter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1, container, false);

        notesAdapter = new NotesAdapter(getContext(),this);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(notesAdapter);
        notesAdapter.setNotesAndUpdate(getStubNotes());

        et_Name = view.findViewById(R.id.et_name);
        pb_list_notes = view.findViewById(R.id.pb_list_notes);
        tv_error = view.findViewById(R.id.tv_error);

        return view;
    }

    @Override
    public void onNoteClicked(Note note) {
    //todo open activity
        Intent intent = new Intent(getContext(), NoteActivity.class);
        intent.putExtra(NoteActivity.ARG_NOTE, note);
        startActivity(intent);
    }

    private List<Note> getStubNotes() {


        List<Note> notes = new ArrayList();

        Note note2 = new Note();
        note2.setName("Privet vsem");
        notes.add(note2);

        Note note3 = new Note();
        note3.setName("Very loooooong name. So long that it is scaring me");
        notes.add(note3);

        Note note4 = new Note();
        note4.setName("Esenia is my love");
        notes.add(note4);


        return notes;
    }

    private void updateAllNotes() {
        notesAdapter.clearNotes();
        tv_error.setVisibility(View.GONE);
        pb_list_notes.setVisibility(ProgressBar.VISIBLE);
        NotesApp.getInstance().getDatabaseManager().getAllNotes()
                .subscribeOn(Schedulers.io())//thread pool
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Note> notes) {
                        notesAdapter.setNotesAndUpdate(notes);
                        pb_list_notes.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        pb_list_notes.setVisibility(View.GONE);
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText("" + e);

                    }
                });


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAllNotes();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

}
