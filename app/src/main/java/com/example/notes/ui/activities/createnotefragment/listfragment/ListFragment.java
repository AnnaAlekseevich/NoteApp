package com.example.notes.ui.activities.createnotefragment.listfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notes.R;
import com.example.notes.models.CheckNoteItem;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class ListFragment extends BaseNoteFragment implements CheckNoteItemListListener {
    //private static final String TAG = "ListFragment";
    private static final String TAG = ListFragment.class.getSimpleName();

    private ListsAdapter listsAdapter;
    RecyclerView recyclerViewCheckList;


    @Override
    protected void drawNote(Note note) {
        listsAdapter.addAndUpdate(note.getCheckItems());
    }


    @Override
    public Note getFilledNote() {
        note.setCheckItems(listsAdapter.getAllCheckNoteItems());
        return note;
    }

    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);

        listsAdapter = new ListsAdapter(getContext(), this, true);
        recyclerViewCheckList = view.findViewById(R.id.rw_check_list);
        recyclerViewCheckList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCheckList.setAdapter(listsAdapter);

        view.findViewById(R.id.floatingAddListButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listsAdapter.addAndUpdate();
            }
        });

        return view;
    }

    @Override
    public void onCheckItemClicked(CheckNoteItem checkNoteItem) {
        //todo handle item ckick
    }
}
