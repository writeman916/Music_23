package com.framgia.music_23.screen.my_song;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.framgia.music_23.R;
import com.framgia.music_23.data.model.Song;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;

public class MySongAdapter extends RecyclerView.Adapter<MySongAdapter.ViewHolder> {

    private List<Song> mSongs = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    MySongAdapter(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_items, parent, false);
        return new ViewHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public void addData(List<Song> songs) {
        if (songs == null) {
            return;
        }
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mImgAvatarSong;
        private TextView mTextNameSong;
        private TextView mTextArtistSong;
        private OnItemClickListener mOnItemClickListener;

        ViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            mImgAvatarSong = itemView.findViewById(R.id.image_avatar_song);
            mTextNameSong = itemView.findViewById(R.id.text_name_song);
            mTextArtistSong = itemView.findViewById(R.id.text_artist_song);
            mOnItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        void setData(Song song) {
            mTextNameSong.setText(song.getTitle());
            mTextArtistSong.setText(song.getArtist().getUsername());
            loadImage(song);
        }

        void loadImage(Song song) {
            Glide.with(itemView.getContext()).load(song.getArtworkUrl()).into(mImgAvatarSong);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClicked(getAdapterPosition());
        }
    }
}
