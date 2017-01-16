package com.example.rita_pc.hardwarenavigation;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlay extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.activity_video_play,container,false);

        VideoView mv = (VideoView)v.findViewById(R.id.videoview);

        Uri uri = Uri.parse("android.resource://com.example.sd.videoplay/"
                + R.raw.myvideo);
        MediaController mc = new MediaController(getContext());
        mv.setMediaController(mc);
        mv.setVideoURI(uri);
        mv.start();

        return v;
    }
}
