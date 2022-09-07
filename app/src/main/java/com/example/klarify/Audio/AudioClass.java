package com.example.klarify.Audio;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

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
        ArrayList<String> words =new ArrayList<String>(Arrays.asList("null","ಎಂದು","ಮತ್ತು",
                "ಅವರು","ಹಾಗೂ","ಅವರ","ಆದರೆ","ಎಂದರು","ಬಗ್ಗೆ","ಒಂದು","ಎಂಬ","ಮೇಲೆ","ಅಧ್ಯಕ್ಷ","ಹೇಳಿದರು","ಮೂಲಕ",
                "ಜಿಲ್ಲಾ","ಸರ್ಕಾರ","ತಮ್ಮ","ನೀರು","ತಿಳಿಸಿದರು","ನಂತರ","ರಾಜ್ಯ","ಹೆಚ್ಚು","ನಮ್ಮ","ಅವರಿಗೆ","ನಡೆದ","ಮಾತ್ರ",
                "ಎರಡು","ಎಲ್ಲ","ಅವರನ್ನು","ಕೆಲಸ","ಕುರಿತು","ಕೇಂದ್ರ","ಯಾವುದೇ","ಮಾಹಿತಿ","ಸೇರಿದಂತೆ","ಕೋಟಿ","ತಾಲ್ಲೂಕು",
                "ವಿರುದ್ಧ","ವಿವಿಧ","ನೀಡಿದರು","ಮಾತನಾಡಿ","ಇಲಾಖೆ","ನಗರದ","ಮಾತನಾಡಿದರು","ಮಾಡಿ","ಕನ್ನಡ","ಮೊದಲ",
                "ಹೊಸ","ತಾಲ್ಲೂಕಿನ","ನನ್ನ","ರಸ್ತೆ","ಲಕ್ಷ","ಸಚಿವ","ಪಂಚಾಯಿತಿ","ಇಲ್ಲ","ಅಧಿಕಾರಿಗಳು","ಸರ್ಕಾರಿ","ಶಿಕ್ಷಣ",
                "ವರ್ಷ","ನಾನು","ಕರ್ನಾಟಕ","ಇಲ್ಲಿ","ಮೂರು","ಕಾರಣ","ಕೃಷಿ","ಅಥವಾ","ಕ್ರಮ","ಬಿಜೆಪಿ","ಗ್ರಾಮ","ಕಳೆದ",
                "ಹಲವು","ಹಣ","ಉತ್ತಮ","ಹೆಚ್ಚಿನ","ಹಿಂದೆ","ಪ್ರತಿ","ಅಭಿವೃದ್ಧಿ","ಸಾವಿರ","ಸರ್ಕಾರದ","ಮಾಡುವ","ಕೆಲವು",
                "ಮುಖ್ಯ","ಕೂಡ","ಮಾಡಲು","ಯೋಜನೆ","ಸುಮಾರು","ಇರುವ","ನಾವು","ಭಾರತ","ಸಮಿತಿ","ಪಿಟಿಐ","ಅದನ್ನು",

                "ಅವಕಾಶ","ಬಳಿಕ","ಇದರಿಂದ","ರೈತರು","ಮಾಜಿ","ಸದಸ್ಯ","ಬೆಂಗಳೂರು","ಸಂಘದ","ನೀರಿನ","ತನ್ನ","ಬಾರಿ",
                "ತಂಡ","ಜಿಲ್ಲೆಯ","ವಿಶೇಷ","ತಿಳಿಸಿದ್ದಾರೆ","ಪೊಲೀಸರು","ನೀಡಿ","ಹೀಗಾಗಿ","ಪಕ್ಷದ","ಸಂದರ್ಭದಲ್ಲಿ","ಕಾರ್ಯದರ್ಶಿ",
                "ಸಮಸ್ಯೆ","ಹಿರಿಯ","ಭೇಟಿ","ಮನವಿ","ದಿನ","ಇಲ್ಲಿನ","ಎಂಬುದು","ಮಕ್ಕಳ","ಒಟ್ಟು","ಗ್ರಾಮದ","ಶಾಸಕ",
                "ಯಾವ","ರಾಷ್ಟ್ರೀಯ","ಮುಂದೆ","ಇದ್ದರು","ಸೋಮವಾರ","ಜನರು","ಬಳಿ","ಗುರುವಾರ","ಇದಕ್ಕೆ","ವೇಳೆ",
                "ನೀಡುವ","ವ್ಯವಸ್ಥೆ","ಕಡಿಮೆ","ಮುಖ್ಯಮಂತ್ರಿ","ಜನ","ಕಾರ್ಯ","ಕೇವಲ","ಇನ್ನೂ","ಮುಂದಿನ","ಉತ್ತರ",
                "ಭಾರತದ","ದೊಡ್ಡ","ಮತ್ತೆ","ಶುಕ್ರವಾರ","ಬುಧವಾರ","ಅದರ","ಮನೆ"));
        Log.e("Array Test:",""+words.size());
        return words.get(trackNo % 150);
    }
}
