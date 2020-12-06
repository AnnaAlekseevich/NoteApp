package com.example.notes.ui.activities.noteslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.models.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> notes;
    private NoteListItemClickListener noteListItemClickListener;

    public NotesAdapter(Context context, NoteListItemClickListener noteListItemClickListener) {
        this.context = context;
        this.noteListItemClickListener = noteListItemClickListener;
    }

    public void setNotesAndUpdate(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void clearNotes() {
        setNotesAndUpdate(null);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View noteItem = layoutInflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(noteItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.updateHolder(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }


    class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        private  Note note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_note_name);
//            itemView.setOnClickListener(view -> noteListItemClickListener.onNoteClicked(note));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noteListItemClickListener.onNoteClicked(note);
                }
            });
        }

        public void updateHolder(Note note) {
            this.note = note;
            //todo if fields are empty - show default names
            tvName.setText(note.getName());
        }
    }
}
