package com.loja.jesus.likeseries;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class FullScreenYoutube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,View.OnClickListener,YouTubePlayer.PlaybackEventListener {
   private YouTubePlayerView youtube;
   private String clave = "AIzaSyCgDcSvjc8SQSjk263E-NYnZp7abBm-Zeo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_full_screen_youtube);
        PersistenciaFirebase p = new PersistenciaFirebase();
        youtube = (YouTubePlayerView) findViewById(R.id.youtubeplayer);
        youtube.initialize(clave, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
if(!b)
{
    Intent intent = getIntent();
    youTubePlayer.cueVideo(intent.getStringExtra("trailer"));
}
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = "Error al iniciar Youtube" + youTubeInitializationResult.toString();
            Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultcode, Intent data) {
        if (resultcode == 1) {
            getYoutubePlayerProvider().initialize(clave, this);
        }
    }
    protected YouTubePlayer.Provider getYoutubePlayerProvider() {
        return youtube;
    }
    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
