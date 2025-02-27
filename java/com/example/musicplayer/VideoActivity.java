package com.example.musicplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button btnApnaBanaLe, btnTutaHaiPullWaha, btnTumSeHi;
    private ImageButton btnHome;

    private int[] videos = {R.raw.apna_bana_le, R.raw.tuta_pull_wahan, R.raw.tum_se_hi};
    private String[] videoNames = {"Apna Bana Le", "Tuta Hai Pull Waha", "Tum Se Hi"};
    private int currentVideoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.videoView);
        btnApnaBanaLe = findViewById(R.id.btnApnaBanaLe);
        btnTutaHaiPullWaha = findViewById(R.id.btnTutaHaiPullWaha);
        btnTumSeHi = findViewById(R.id.btnTumSeHi);
        btnHome = findViewById(R.id.btnHome);

        // Set up button click listeners
        btnApnaBanaLe.setOnClickListener(v -> playVideo(0));
        btnTutaHaiPullWaha.setOnClickListener(v -> playVideo(1));
        btnTumSeHi.setOnClickListener(v -> playVideo(2));

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(VideoActivity.this, MainMenuActivity.class));
            finish();
        });

        // Play the first video by default
        playVideo(currentVideoIndex);
    }

    private void playVideo(int videoIndex) {
        currentVideoIndex = videoIndex;
        String path = "android.resource://" + getPackageName() + "/" + videos[videoIndex];
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        videoView.start();
    }
}
