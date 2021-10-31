package com.example.kinduya.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.kinduya.MainActivity;
import com.example.kinduya.R;
import com.example.kinduya.container.VideoBackgroundView;
import com.example.kinduya.datalayer.KinduyaDatabase;
import com.example.kinduya.viewmodel.SplashScreenViewModel;

public class SplashScreen extends AppCompatActivity {

    private VideoView videoView;
    MediaPlayer mMediaPlayer;
    KinduyaDatabase kinduyaDatabase;
    private SplashScreenViewModel splashScreenViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_splash_screen);
        kinduyaDatabase = KinduyaDatabase.getInstance(this.getApplicationContext());
        splashScreenViewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);
        splashScreenViewModel.insertQuestion(kinduyaDatabase);
        videoView = findViewById(R.id.videoview);
        Handler handler = new Handler();


        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.splash_new);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                videoView.start();

            }
        });
        videoView.setOnCompletionListener(mediaPlayer -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

}