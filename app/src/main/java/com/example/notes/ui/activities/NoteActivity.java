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
import com.example.notes.models.Note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NoteActivity extends AppCompatActivity {

    public final static String ARG_NOTE = "arg_note";

    public static EditText etName;
    public static EditText etText;
    private ProgressBar progressBar;

    private Note currentNote;
    private boolean isNoteEditingMode = false; //todo use this flag to edit current note;

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
        etText = findViewById(R.id.et_description);
        progressBar = findViewById(R.id.progress_bar);

        if (getIntent().getParcelableExtra(ARG_NOTE) == null) {
            // it means that we have to create new note
            currentNote = createNewNote();
        } else {
            // it means that we have to download note by id
            //  currentNote = getNoteFromDb();
            Toast.makeText(this, "get ID in second activity", Toast.LENGTH_LONG).show();
            isNoteEditingMode = true;
            currentNote = getIntent().getParcelableExtra(ARG_NOTE);
            updateViewWithNote(currentNote);
        }
    }

    private void updateViewWithNote(Note note) {
        etName.setText(note.getName());
        etText.setText(note.getText());
    }


    private Note createNewNote() {
        Note note = new Note();
        note.setName("Test name");
        note.setText("Test Text");
        note.setCreateDate(System.currentTimeMillis());

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
        currentNote.setText(etText.getText().toString());
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
