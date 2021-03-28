package com.example.notes.ui.activities.createnotefragment.textfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;
import com.example.notes.ui.activities.createnotefragment.BitmapsAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.notes.utils.BitmapUtils.createBitmaps;
import static com.example.notes.utils.BitmapUtils.saveImage;

public class NoteTextFragment extends BaseNoteFragment {

    private static final String TAG = NoteTextFragment.class.getSimpleName();
    EditText et_description;
    private BitmapsAdapter bitmapsListAdapter;
    RecyclerView recyclerViewBitmapList;
    MenuItem shareItem;

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

        bitmapsListAdapter = new BitmapsAdapter(getContext());
        recyclerViewBitmapList = view.findViewById(R.id.rv_bitmaps);
        recyclerViewBitmapList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerViewBitmapList.setAdapter(bitmapsListAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

         shareItem = menu.findItem(R.id.share);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.convert_text_to_image:
                bitmapsListAdapter.addItemsAndUpdate(createBitmaps(getContext(),et_description.getText().toString()));
                shareItem.setVisible(true);
                return true;
            case R.id.share:
                //send images to another apps
                shareImageUri(saveImage(getContext(), bitmapsListAdapter.getAllBitmapItems().get(0)));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Note getFilledNote() {
        note.setText(et_description.getText().toString());
        return note;
    }

    private void shareImageUri(Uri uri){
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/png");
        startActivity(intent);
    }

}
