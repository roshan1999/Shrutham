package com.example.clarify.Audio;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.clarify.Words;

import java.util.ArrayList;
import java.util.Arrays;


public class AudioClass {

    // Properties
    String frequency;
    int trackNo;
    int noiseType;
    public boolean repeat;
    MediaPlayer mediaPlayer;

    //Constructor
    public AudioClass(String selectAudio) {
        String[] audioString = selectAudio.split("_");
        frequency = audioString[0];
        noiseType = Integer.parseInt(audioString[1]);
        trackNo = Integer.parseInt(audioString[2]);
        repeat = false;
    }

    // Methods
    public void playAudio(Context context){

        // Play the audio tape
        Resources res = context.getResources();
        String actualSound = frequency;


        // Identify the noise type
        switch (this.noiseType){
            case 1:
                actualSound+="s";
                break;
            case 2:
                actualSound+="c";
                break;
            case 3:
                actualSound+="t";
                break;
        }
        actualSound+=this.trackNo;


        // Getting the resource ID from the raw directory
        int soundId = res.getIdentifier(actualSound, "raw", context.getPackageName());


        // Playing the sound
        try{
            mediaPlayer = MediaPlayer.create(context, soundId);
            mediaPlayer.start();
            onStop();
        }


        // Exception
        catch(Exception e){
            Log.e("Media Exception", e.getMessage());
            Toast.makeText(context,"Audio File not found",Toast.LENGTH_LONG);
        }

        //Debug Print the sound Played
        Log.e("Sound: ", actualSound+".mp3");
    }


    // Releasing the mediaPlayer object, to play the next track
    protected void onStop(){
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer=null;
            }
        });
    }

    // Returns the respective kannada word from the trackNo.
    public String getString() {
        Log.e("Array Test:",""+ Words.word.size());
        return Words.word.get(trackNo % 150);
    }
}
