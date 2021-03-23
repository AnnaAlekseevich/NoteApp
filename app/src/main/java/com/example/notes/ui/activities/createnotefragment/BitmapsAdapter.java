package com.example.notes.ui.activities.createnotefragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.models.CheckNoteItem;


import java.util.List;

public class BitmapsAdapter extends RecyclerView.Adapter<BitmapsAdapter.BitmapViewHolder> {

    private Context context;
    private List<Bitmap> bitmapsList;


    public BitmapsAdapter (Context context){
        this.context = context;
    }

    public void addItemsAndUpdate(List<Bitmap> items) {
        bitmapsList = items;
        notifyDataSetChanged();
    }

    public void clearItems() {
        bitmapsList.clear();
        notifyDataSetChanged();
    }

    public List<Bitmap> getAllBitmapItems() {
        return bitmapsList;
    }


    @NonNull
    @Override
    public BitmapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View bitmapItem = layoutInflater.inflate(R.layout.item_bitmap, parent, false);
        return new BitmapViewHolder(bitmapItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BitmapsAdapter.BitmapViewHolder holder, int position) {
        holder.updateHolder(bitmapsList.get(position));
    }

    @Override
    public int getItemCount() {
        return bitmapsList == null ? 0 : bitmapsList.size();
    }

    abstract class BaseListHolder extends RecyclerView.ViewHolder {
        public BaseListHolder(@NonNull View itemView) {
            super(itemView);
        }

        abstract void updateHolder(Bitmap bitmap);
    }


    class BitmapViewHolder extends BaseListHolder {

        ImageView ivConvertedImage;

        public BitmapViewHolder(@NonNull View itemView) {
            super(itemView);
            ivConvertedImage = itemView.findViewById(R.id.iv_convert_to_image);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    bitmapListItemClickListener.onBitmapClicked(bitmap);
//                }
//            });
        }

        @Override
        public void updateHolder(Bitmap bitmap) {
            ivConvertedImage.setImageBitmap(bitmap);
        }
    }

}
