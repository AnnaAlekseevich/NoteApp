package com.example.notes.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.CheckNoteItem;
import com.example.notes.models.Note;
import com.example.notes.models.NoteType;
import com.example.notes.models.Reminder;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;
import com.example.notes.ui.activities.createnotefragment.textfragment.NoteTextFragment;
import com.example.notes.ui.activities.createnotefragment.reminderfragment.ReminderFragment;
import com.example.notes.ui.activities.createnotefragment.listfragment.ListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {

    public final static String ARG_NOTE = "arg_note";
    public static final String ARG_NoteType = "NoteType";

    public static EditText etName;
    private ProgressBar progressBar;

    private NoteType currentNoteType;
    private Note currentNote;
    private boolean isNoteEditingMode = false; //todo use this flag to edit current note;
    BaseNoteFragment fragmentNotes;

    private void changeProgressBarVisibility(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);

        etName = findViewById(R.id.et_name);
        //etText = findViewById(R.id.et_description_note);
        progressBar = findViewById(R.id.progress_bar);

        if (getIntent().getParcelableExtra(ARG_NOTE) == null) {
            // it means that we have to create new note
            currentNoteType = (NoteType) getIntent().getSerializableExtra(ARG_NoteType);
            currentNote = createNewNote();
        } else {
            // it means that we have to download note by id
            //  currentNote = getNoteFromDb();
            Toast.makeText(this, "get ID in second activity", Toast.LENGTH_LONG).show();
            isNoteEditingMode = true;
            currentNote = getIntent().getParcelableExtra(ARG_NOTE);
            currentNoteType = currentNote.getNoteType();
            updateViewWithNote(currentNote);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        fragmentNotes = generateFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ARG_NOTE", currentNote);//todo move key name to BaseNoteFragment
        fragmentNotes.setArguments(bundle);
        ft.replace(R.id.fragment_note, fragmentNotes);
        ft.commit();
    }

    private BaseNoteFragment generateFragment() {

    switch (currentNoteType){
        case Text:
            return  NoteTextFragment.newInstance();
        case List:
            return ListFragment.newInstance();
        case Reminder:
            return ReminderFragment.newInstance();
    }
     throw new IllegalArgumentException("Unknown type");
    }

    private void updateViewWithNote(Note note) {
        etName.setText(note.getName());
    }


    private Note createNewNote() {
        Note note = new Note(currentNoteType);
        note.setCreateDate(System.currentTimeMillis());
        List<CheckNoteItem> firstChecklist  = new ArrayList<>();
        firstChecklist.add(new CheckNoteItem());
        note.setCheckItems(firstChecklist);
        note.setReminder(new Reminder(System.currentTimeMillis()));
        return note;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        fillInNote();
        saveNoteAndClose(currentNote);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillInNote() {
        currentNote.setName(etName.getText().toString());
        currentNote.setText(fragmentNotes.getFilledNote().getText());
        currentNote.setCheckItems(fragmentNotes.getFilledNote().getCheckItems());
        currentNote.setChangeDate(System.currentTimeMillis());
    }

    private void deleteNote(Note note) {
        NotesApp.getInstance().getDatabaseManager().deleteNote(note)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                //показывать прогресс бар
                                changeProgressBarVisibility(true);

                            }

                            @Override
                            public void onComplete() {
                                //прятать прогресс бар
                                changeProgressBarVisibility(false);
                                Toast.makeText(NoteActivity.this, "current Note = " + currentNote.toString(),
                                        Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onError(Throwable e) {
                                //прятать прогресс бар
                                changeProgressBarVisibility(false);
                                Toast.makeText(NoteActivity.this, "error = " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                );
    }

    private void saveNoteAndClose(Note note) {
        //todo show progressbar
        Completable dbNoteCompletable = isNoteEditingMode ?
                NotesApp.getInstance().getDatabaseManager().updateNote(note) :
                NotesApp.getInstance().getDatabaseManager().insertNote(note);

        dbNoteCompletable
                .subscribeOn(Schedulers.io())//thread pool
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new CompletableObserver() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                //todo hide progressbar
                                finish();//close current Activity
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(NoteActivity.this, "error = " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();

                            }
                        }
                );
    }
}
