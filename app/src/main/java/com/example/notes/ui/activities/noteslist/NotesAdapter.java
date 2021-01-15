package com.example.notes.ui.activities.noteslist;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.createnotefragment.listfragment.CheckItemsListsAdapter;

import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        public TextView tvData;
        private CheckItemsListsAdapter checkItemsListsAdapter;
        private RecyclerView recyclerView;
        public TextView tv_note_date;


        private Note note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_note_name);
            tvData = itemView.findViewById(R.id.tv_note_data);
            recyclerView = itemView.findViewById(R.id.rv_check_note);
            tv_note_date = itemView.findViewById(R.id.tv_note_date);

            checkItemsListsAdapter = new CheckItemsListsAdapter(itemView.getContext(), false);

            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recyclerView.setAdapter(checkItemsListsAdapter);

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
            tvData.setText(note.getText());
            checkItemsListsAdapter.clearItems();
            checkItemsListsAdapter.addAndUpdate(note.getCheckItems());

            if (note.getNoteType() == NoteType.List) {
                recyclerView.setVisibility(View.VISIBLE);
                tvData.setVisibility(View.GONE);
                tv_note_date.setVisibility(View.GONE);
            } else if (note.getNoteType() == NoteType.Text) {
                recyclerView.setVisibility(View.GONE);
                tvData.setVisibility(View.VISIBLE);
                tv_note_date.setVisibility(View.GONE);
            } else if (note.getNoteType() == NoteType.Reminder) {
                recyclerView.setVisibility(View.GONE);
                tv_note_date.setVisibility(View.VISIBLE);
                tvData.setVisibility(View.VISIBLE);
                putFormattedDateToTextView(note.getReminder().getReminderDate());
            }
        }

        private void putFormattedDateToTextView(long date) {
            Calendar dateAndTime = Calendar.getInstance();
            dateAndTime.setTimeInMillis(date);
            tv_note_date.setText(DateUtils.formatDateTime(context,
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_SHOW_TIME));
        }
    }


}
