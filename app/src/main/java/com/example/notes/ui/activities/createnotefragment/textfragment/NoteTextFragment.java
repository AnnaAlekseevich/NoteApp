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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;
import com.example.notes.ui.activities.createnotefragment.BitmapsAdapter;
import com.example.notes.utils.BitmapUtils;

import static com.example.notes.utils.BitmapUtils.saveImage;

public class NoteTextFragment extends BaseNoteFragment implements DialogBitmapSettingListener {

    private static final String TAG = NoteTextFragment.class.getSimpleName();
    private static final String LogC = "Color";
    EditText et_description;
    private BitmapsAdapter bitmapsListAdapter;
    RecyclerView recyclerViewBitmapList;
    MenuItem shareItem;
    public  BitmapUtils bitmapUtils;
    ImageView imageSetting;
    DialogImageSetting dialogFragment;

    public static NoteTextFragment newInstance() {
        NoteTextFragment fragment = new NoteTextFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitmapUtils = new BitmapUtils();
        dialogFragment = new DialogImageSetting();
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
        imageSetting = view.findViewById(R.id.image_setting);
        imageSetting.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // show fragmentDialog
                showEditDialog();
            }
        });
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
                bitmapsListAdapter.addItemsAndUpdate(BitmapUtils.createBitmaps(getContext(), et_description.getText().toString(), bitmapUtils));
                shareItem.setVisible(true);
                imageSetting.setVisibility(View.VISIBLE);
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

    private void showEditDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DialogImageSetting editNameDialogFragment = DialogImageSetting.newInstance("Настройки картинки");
        editNameDialogFragment.setDialogBitmapSettingListener(this);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    @Override
    public void onApplySettingData(int chosenColor, int chosenSize) {
        bitmapUtils.setСolor(chosenColor);
        bitmapUtils.setChooseTextNoteSize(chosenSize);
        bitmapsListAdapter.addItemsAndUpdate(BitmapUtils.createBitmaps(getContext(), et_description.getText().toString(), bitmapUtils));
        Log.d("SettingNTF", "chosenColor = " + chosenColor + "chosenSize = " + chosenSize );
    }

}
