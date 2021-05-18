package com.eskim.picturesample.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eskim.picturesample.R;
import com.eskim.picturesample.api.PictureInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.PictureHolder> {
    private ArrayList<PictureInfo> items = new ArrayList<>();

    private OnItemClickListener listener;

    @NonNull
    @Override
    public MainAdapter.PictureHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);

        return new PictureHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MainAdapter.PictureHolder pictureHolder, int position) {

        PictureInfo picture = items.get(pictureHolder.getAdapterPosition());

        Glide.with(pictureHolder.itemView.getContext())
                .load(picture.getDownloadUrl())
                .into(pictureHolder.pictureImageView); // imageView

        pictureHolder.pictureUrl.setText(picture.getDownloadUrl());
        pictureHolder.pictureAuthor.setText(picture.getAuthor());
        pictureHolder.itemView.setOnClickListener(view
                -> listener.onClick(picture));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateView(PictureInfo picture) {
        int pos = getPosition(picture);

        if (pos == RecyclerView.NO_POSITION) {
            return;
        }

        items.set(pos, picture);
        notifyItemChanged(pos);
    }

    private int getPosition(PictureInfo picture) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(picture.getId())) {
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    public void setItems(ArrayList<PictureInfo> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(PictureInfo picture);
    }

    static class PictureHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.picture_item_image)
        ImageView pictureImageView;

        @BindView(R.id.picture_item_author)
        TextView pictureAuthor;

        @BindView(R.id.picture_item_url)
        TextView pictureUrl;

        PictureHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
