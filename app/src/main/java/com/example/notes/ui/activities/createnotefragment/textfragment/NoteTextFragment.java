package com.example.notes.ui.activities.createnotefragment.textfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;
import com.example.notes.ui.activities.createnotefragment.reminderfragment.ReminderFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteTextFragment extends BaseNoteFragment {

    private static final String TAG = NoteTextFragment.class.getSimpleName();
    EditText et_description;

    public static NoteTextFragment newInstance() {
        NoteTextFragment fragment = new NoteTextFragment();
        return fragment;
    }


    @Override
    protected void drawNote(Note note) {
        et_description.setText(note.getText());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        Log.d(TAG, "onCreateView ListFragment");
        et_description = view.findViewById(R.id.et_description_note);

        return view;
    }

    @Override
    public Note getFilledNote() {
        note.setText(et_description.getText().toString());
        return note;
    }
}
