package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ImageButton btnPlay, btnPause, btnNext, btnPrevious;
    private Button btnDespacito, btnShapeOfYou, btnMercy, btnLetMeLoveYou, btnDaruBadnaam, btnBackbone, btn3Peg;
    private ImageButton btnToMainMenu;
    private SeekBar seekBar;
    private TextView songTitle;
    private ImageView songImage;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int[] songs = {R.raw.song1, R.raw.song2, R.raw.song3, R.raw.song4, R.raw.song5, R.raw.song6, R.raw.song7};
    private String[] songNames = {"Despacito", "Shape Of You", "Mercy", "Let Me Love You", "Daru Badnaam", "Backbone", "3 Peg"};
    private int[] songImages = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6,R.drawable.img7};
    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Initializing views");

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnDespacito = findViewById(R.id.btnDespacito);
        btnShapeOfYou = findViewById(R.id.btnShapeOfYou);
        btnMercy = findViewById(R.id.btnMercy);
        btnLetMeLoveYou = findViewById(R.id.btnLetMeLoveYou);
        btnDaruBadnaam = findViewById(R.id.btnDaruBadnaam);
        btnBackbone = findViewById(R.id.btnBackbone);
        btn3Peg = findViewById(R.id.btn3Peg);
        btnToMainMenu = findViewById(R.id.btnToMainMenu);
        seekBar = findViewById(R.id.seekBar);
        songTitle = findViewById(R.id.songTitle);
        songImage = findViewById(R.id.songImage);

        initializeMediaPlayer();

        btnPlay.setOnClickListener(v -> {
            playMusic();
            updatePlayPauseButtons();
        });

        btnPause.setOnClickListener(v -> {
            pauseMusic();
            updatePlayPauseButtons();
        });

        btnNext.setOnClickListener(v -> nextSong());
        btnPrevious.setOnClickListener(v -> previousSong());

        btnDespacito.setOnClickListener(v -> playSong(0));
        btnShapeOfYou.setOnClickListener(v -> playSong(1));
        btnMercy.setOnClickListener(v -> playSong(2));
        btnLetMeLoveYou.setOnClickListener(v -> playSong(3));
        btnDaruBadnaam.setOnClickListener(v -> playSong(4));
        btnBackbone.setOnClickListener(v -> playSong(5));
        btn3Peg.setOnClickListener(v -> playSong(6));

        btnToMainMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            nextSong();
            updatePlayPauseButtons();
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void initializeMediaPlayer() {
        try {
            Log.d(TAG, "initializeMediaPlayer: Creating MediaPlayer instance");
            mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]);
            songTitle.setText(songNames[currentSongIndex]);
            songImage.setImageResource(songImages[currentSongIndex]);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (Exception e) {
            Log.e(TAG, "initializeMediaPlayer: Error initializing MediaPlayer", e);
        }
    }

    private void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.d(TAG, "playMusic: Playing music");
        }
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.d(TAG, "pauseMusic: Pausing music");
        }
    }

    private void nextSong() {
        if (currentSongIndex < songs.length - 1) {
            currentSongIndex++;
        } else {
            currentSongIndex = 0;
        }
        playSong(currentSongIndex);
    }

    private void previousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
        } else {
            currentSongIndex = songs.length - 1;
        }
        playSong(currentSongIndex);
    }

    private void playSong(int songIndex) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(this, songs[songIndex]);
                currentSongIndex = songIndex;
                songTitle.setText(songNames[currentSongIndex]);
                songImage.setImageResource(songImages[currentSongIndex]);
                seekBar.setMax(mediaPlayer.getDuration());
                playMusic();
                updatePlayPauseButtons();
                Log.d(TAG, "playSong: Playing song " + songNames[currentSongIndex]);
            }
        } catch (Exception e) {
            Log.e(TAG, "playSong: Error playing song", e);
        }
    }

    private void updatePlayPauseButtons() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                btnPlay.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
            } else {
                btnPause.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d(TAG, "onDestroy: MediaPlayer released");
        }
        handler.removeCallbacks(runnable);
    }
}
