package com.example.notes.ui.activities.createnotefragment.listfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CheckItemsListFragment extends BaseNoteFragment {
    //private static final String TAG = "ListFragment";
    private static final String TAG = CheckItemsListFragment.class.getSimpleName();

    private CheckItemsListsAdapter checkItemsListsAdapter;
    RecyclerView recyclerViewCheckList;


    @Override
    protected void drawNote(Note note) {
        checkItemsListsAdapter.addAndUpdate(note.getCheckItems());
    }


    @Override
    public Note getFilledNote() {
        note.setCheckItems(checkItemsListsAdapter.getAllCheckNoteItems());
        return note;
    }

    public static CheckItemsListFragment newInstance() {
        CheckItemsListFragment fragment = new CheckItemsListFragment();
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

        checkItemsListsAdapter = new CheckItemsListsAdapter(getContext(), true);
        recyclerViewCheckList = view.findViewById(R.id.rw_check_list);
        recyclerViewCheckList.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCheckList.setAdapter(checkItemsListsAdapter);

        view.findViewById(R.id.floatingAddListButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkItemsListsAdapter.addAndUpdate();
            }
        });

        return view;
    }

}
