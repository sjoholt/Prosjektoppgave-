package com.example.gautelokal.youtubetestapi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeStandalonePlayer;



public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = YouTubeStandalonePlayer.createVideoIntent(this,"AIzaSyC3BB6nhsBUlPGCJNRLSqCPg8vgr65Lqqk","fBYVlFXsEME");
        startActivity(intent);


    }




}





