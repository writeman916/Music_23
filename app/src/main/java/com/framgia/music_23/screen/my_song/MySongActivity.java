package com.framgia.music_23.screen.my_song;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.framgia.music_23.R;
import com.framgia.music_23.data.model.Song;
import com.framgia.music_23.data.repository.SongRepository;
import com.framgia.music_23.data.source.remote.SongRemoteDataSource;
import com.framgia.music_23.screen.playmusic.PlayMusicFragment;
import java.util.List;

public class MySongActivity extends AppCompatActivity
        implements MySongContract.View, OnItemClickListener {

    private RecyclerView mRecyclerViewSongs;
    private MySongPresenter mMySongPresenter;
    private MySongAdapter mMySongAdapter;
    private List<Song> mSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_song);
        initView();
    }

    void initView() {
        mRecyclerViewSongs = findViewById(R.id.recyclerview_list_song);
        mRecyclerViewSongs.setHasFixedSize(true);
        mRecyclerViewSongs.setLayoutManager(new LinearLayoutManager(this));
        SongRepository songRepository =
                SongRepository.getInstance(SongRemoteDataSource.getInstance());
        mMySongPresenter = new MySongPresenter(this, songRepository);
        // test function getSongByGenre() with Deep House genre
        mMySongPresenter.getSongsByGenre("Deep House");
        mMySongAdapter = new MySongAdapter(this);
        mRecyclerViewSongs.setAdapter(mMySongAdapter);
    }

    @Override
    public void onGetSongSuccess(List<Song> songs) {
        assert songs != null;
        mSongs = songs;
        mMySongAdapter.addData(songs);
    }

    @Override
    public void OnError(Exception e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.replace(R.id.activity_mysong,
                PlayMusicFragment.newInstance(mSongs, position));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
