package com.example.notes.ui.activities.createnotefragment.listfragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.models.CheckNoteItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CheckItemsListsAdapter extends RecyclerView.Adapter<CheckItemsListsAdapter.BaseListHolder> {

    private Context context;
    private List<CheckNoteItem> checkNoteList = new ArrayList<>();
    private boolean editable;

    public CheckItemsListsAdapter(Context context, boolean editable) {
        this.context = context;
        this.editable = editable;
    }

    public void clearItems() {
        checkNoteList.clear();
    }

    public List<CheckNoteItem> getAllCheckNoteItems() {
        return checkNoteList;
    }

    public void addAndUpdate() {
        checkNoteList.add(new CheckNoteItem());
        notifyDataSetChanged();
    }


    public void addAndUpdate(List<CheckNoteItem> items) {
        checkNoteList.addAll(items);
        notifyDataSetChanged();
    }

    public void deleteItemAndUpdate(CheckNoteItem checkNoteItem) {
        checkNoteList.remove(checkNoteItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if(editable) {
            View checkListItem = layoutInflater.inflate(R.layout.item_check_list, parent, false);
            return new EditableListViewHolder(checkListItem);
        }else{
            View checkListItem = layoutInflater.inflate(R.layout.item_check_list_not_editable, parent, false);
            return new NotEditableListViewHolder(checkListItem);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListHolder holder, int position) {
        holder.updateHolder(checkNoteList.get(position));
    }

    @Override
    public int getItemCount() {
        return checkNoteList == null ? 0 : checkNoteList.size();
    }

    abstract class BaseListHolder extends RecyclerView.ViewHolder {

        public BaseListHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void updateHolder(CheckNoteItem checkNoteItem);
    }

    class NotEditableListViewHolder extends BaseListHolder {
        public CheckBox checkbox;
        public TextView et_description;


        public NotEditableListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox_list);
            et_description = itemView.findViewById(R.id.tv_description_list);
        }

        @Override
        void updateHolder(CheckNoteItem checkNoteItem) {
            //todo if fields are empty - show default names
            et_description.setText(checkNoteItem.descriptionItem);
            checkbox.setChecked(checkNoteItem.isChecked);
        }
    }

    class EditableListViewHolder extends BaseListHolder {
        public CheckBox checkbox;
        public EditText et_description;
        @Nullable
        private CheckNoteItem checkNoteItem;

        public EditableListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox_list);
            et_description = itemView.findViewById(R.id.et_description_list);
//            itemView.setOnClickListener(view -> noteListItemClickListener.onNoteClicked(note));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            et_description.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    checkNoteItem.descriptionItem = editable.toString();
                }
            });

            checkbox.setOnCheckedChangeListener((compoundButton, b) -> {
                checkNoteItem.isChecked = b;
            });

            itemView.findViewById(R.id.checkbox_list_basket).setOnClickListener(view -> {
                deleteItemAndUpdate(checkNoteItem);
            });

        }

        @Override
        public void updateHolder(CheckNoteItem checkNoteItem) {
            this.checkNoteItem = checkNoteItem;
            //todo if fields are empty - show default names
            et_description.setText(checkNoteItem.descriptionItem);
            checkbox.setChecked(checkNoteItem.isChecked);
        }
    }

}
