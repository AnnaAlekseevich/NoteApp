package com.example.notes.ui.activities.noteslist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.NoteActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseNotesListFragment extends Fragment implements NoteListItemClickListener {

    private static final String TAG = BaseNotesListFragment.class.getSimpleName();
    private static final String ARG_TYPE = "ARG_TYPE";

    protected NotesAdapter notesAdapter;
    //for notes from hint

    ProgressBar pb_list_notes;
    TextView tv_error;
    TextView tv_hint_for_view_pagers;
    RecyclerView recyclerView;
    public NoteType currentType;


    public static BaseNotesListFragment newInstance(@Nullable NoteType type) {
        BaseNotesListFragment fragment = new BaseNotesListFragment();
        Bundle args = new Bundle();
        if (type != null) {
            args.putSerializable(ARG_TYPE, type);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().getSerializable(ARG_TYPE) != null) {
            currentType = (NoteType) getArguments().getSerializable(ARG_TYPE);
        }

    }

    protected int getLayoutId() {
        return R.layout.fragment_1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutId(), container, false);
        setHasOptionsMenu(true);

        notesAdapter = new NotesAdapter(getContext(), this);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
        recyclerView.setAdapter(notesAdapter);

        pb_list_notes = view.findViewById(R.id.pb_list_notes);
        tv_error = view.findViewById(R.id.tv_error);
        tv_hint_for_view_pagers = view.findViewById(R.id.tv_hint_for_view_pagers);

        //todo запихнуть в titleTextView строку из нового метода
        tv_hint_for_view_pagers.setText(getTitle());
        return view;
    }

    public String getTitle() {
        if (currentType == null) {
            return getResources().getString(R.string.all_notes);
        }

        switch (currentType) {
            case Text:
                return getResources().getString(R.string.notes);
            case List:
                return getResources().getString(R.string.lists);
            case Reminder:
                return getResources().getString(R.string.reminders);
            default:
                return "NEW";
        }

    }
    //todo добавить метод возвращаюсщтий строку/тайтл


    @Override
    public void onNoteClicked(Note note) {
        Intent intent = new Intent(getContext(), NoteActivity.class);
        intent.putExtra(NoteActivity.ARG_NOTE, note);
        startActivity(intent);
    }

    public void loadNotes() {
        Single<List<Note>> getNotesRequest = currentType == null ?
                NotesApp.getInstance().getDatabaseManager().getAllNotes() :
                NotesApp.getInstance().getDatabaseManager().getNotesByType(currentType);

        getNotesRequest
                .subscribeOn(Schedulers.io())//thread pool
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Note>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<Note> notes) {
                        // onNotesLoaded( notes)
                        onNotesLoaded(notes);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // onLoadError()
                        onLoadError(e);

                    }
                });
    }

    public void onNotesLoaded(List<Note> notes) {
        notesAdapter.setNotesAndUpdate(notes);
        pb_list_notes.setVisibility(View.GONE);
    }

    public void onLoadError(Throwable e) {
        pb_list_notes.setVisibility(View.GONE);
        tv_error.setVisibility(View.VISIBLE);
        tv_error.setText("" + e);
    }

    protected void updateAllNotes() {
        notesAdapter.clearNotes();
        tv_error.setVisibility(View.GONE);
        pb_list_notes.setVisibility(ProgressBar.VISIBLE);

        // loadNotes()
        loadNotes();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.view_headline:
                if (item.isChecked()) {
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
                } else if (!item.isChecked()) {
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
                }
                recyclerView.setAdapter(notesAdapter);
                notesAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

}