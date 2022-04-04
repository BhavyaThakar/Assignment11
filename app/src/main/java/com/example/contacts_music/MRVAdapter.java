package com.example.contacts_music;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MRVAdapter extends RecyclerView.Adapter<MRVAdapter.MusicViewHolder> {

    Context context;
    ArrayList<String> music = new ArrayList<>();

    @SuppressLint("Range")
    public MRVAdapter(Context context) {
        this.context = context;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                music.add(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            }
            cursor.close();
        }

    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =  inflater.inflate(R.layout.music_row,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return music.size();
    }

    protected class MusicViewHolder extends RecyclerView.ViewHolder {
        TextView tvMusic;
        ImageView imgMusic;
        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMusic = itemView.findViewById(R.id.tv_music);
            imgMusic = itemView.findViewById(R.id.iv_music);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.tvMusic.setText(music.get(position));
        holder.imgMusic.setImageResource(R.drawable.ic_baseline_music_note_24);
    }


}