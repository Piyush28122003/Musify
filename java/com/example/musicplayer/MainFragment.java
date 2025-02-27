package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private LinearLayout musicButton, videoButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        musicButton = rootView.findViewById(R.id.musicButton);
        videoButton = rootView.findViewById(R.id.videoButton);

        musicButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
        });

        videoButton.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), VideoActivity.class));
        });

        return rootView;
    }
}
