package com.example.klarify;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.klarify.Audio.AudioClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Stage extends AppCompatActivity {

    // View variables
    FloatingActionButton level_audio, level_speech;
    TextView level_heading;
    TextView speech_text;
    TextView green_text;
    TextView red_text;

    // Variable
    String level_info;
    int level, stage, countAudioTracks = 150;
    static int audioPlayCount=0;
    public static final Integer RecordAudioRequestCode = 1;
    SpeechRecognizer speechRecognizer;
    String playText;
    boolean match =true;
    AudioClass audioObject[][] = new AudioClass[4][countAudioTracks/4 +1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hooks
        level_audio = findViewById(R.id.FABLvlAudio);
        level_heading = findViewById(R.id.TVLvl);
        level_speech = findViewById(R.id.FABSpeech);
        speech_text = findViewById(R.id.SpeechText);
        green_text = findViewById(R.id.greenText);
        red_text = findViewById(R.id.redText);

        level_info = getIntent().getStringExtra("selectedLevel");
        if(level_info!=null){
            level = Integer.parseInt(String.valueOf(level_info.charAt(0)));
            stage = Integer.parseInt(String.valueOf(level_info.charAt(2)));
        }
        else{
            level = 1;
            stage = 1;
        }

        // Set the heading to the corresponding stage and level
        level_heading.setText(String.format("Level %d Stage %d", level, stage));
    }


    // Method used to create Objects of the AudioClass
    public void createAudioObject(int randNoise, int randTrack){
        if (audioObject[randNoise][randTrack%(countAudioTracks/4+1)] == null) {
            switch (level) {
                case 1:
                    audioObject[randNoise][randTrack%(countAudioTracks/4+1)] = new AudioClass("hf_" + randNoise + "_" + randTrack);
                    audioObject[randNoise][randTrack%(countAudioTracks/4+1)].repeat = false;
                    break;
                case 2:
                    audioObject[randNoise][randTrack%(countAudioTracks/4+1)] = new AudioClass("mf_" + randNoise + "_" + randTrack);
                    audioObject[randNoise][randTrack%(countAudioTracks/4+1)].repeat = false;
                    break;
                case 3:
                    audioObject[randNoise][randTrack%(countAudioTracks/4+1)] = new AudioClass("lf_" + randNoise + "_" + randTrack);
                    audioObject[randNoise][randTrack%(countAudioTracks/4+1)].repeat = false;
                    break;
            }
        }
    }


    // Method called on clicking the repeat button in Stage Activity
    public void playAudio(View view) {

        // Defining the bounds
        int lb =  ((countAudioTracks*(level-1))+  ((countAudioTracks/4)*(stage-1)))+1;
        int ub =  ((countAudioTracks*(level-1))+  ((countAudioTracks/4)*(stage)))+1;

        // Generate random track and noise
        int randTrack = (int) (Math.random()*((ub-lb)+1))+lb;
        int randNoise = (int) (Math.random()*(3)+1);


        // Adjust the random track based upon the stage
        randTrack = randTrack>lb? randTrack: randTrack + lb;
        Log.e("Level and stage: "," " + level + " " + stage);
        Log.e("Bound: "," " + lb + " " + ub);
        Log.e("Noise and track: ", " " + randNoise + " " + randTrack);


        // Creating audio object setting track and noise
        if(audioObject[randNoise][randTrack%(countAudioTracks/4+1)]==null)
        {
            createAudioObject(randNoise,randTrack);
        }


        // To raise null reference as exception
        if(audioObject[randNoise][randTrack%(countAudioTracks/4+1)]==null)
        {
            Log.e("Track Memory error: ", "Null reference");
        }
        else {

            // Following code avoids repetition
            while (((audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)].repeat)) && (audioPlayCount != (ub - lb))) {


                // Since, track and noise repeated, finding new values
                randTrack = (int) (Math.random() * ((ub - lb) + 1)) + lb;
                randTrack = randTrack > lb ? randTrack : randTrack + lb;
                randNoise = (int) (Math.random() * (3) + 1);

                // Creating new object for the new unused track and noise
                // null test ensures audio not already used
                if (audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)] == null) {
                    createAudioObject(randNoise, randTrack);
                }
            }

            // Once all the audio is played at-least once, raise toast and clear
            if (audioPlayCount == (countAudioTracks / 4 + 1)) {

                // Toast raised to indicate level cleared
                Toast.makeText(this, "You have cleared this level!", Toast.LENGTH_LONG).show();

                // Clearing all repeated track and noise
                for (int i = 0; i < (countAudioTracks / 4 + 1); i++) {
                    for (int j = 0; j < 4; j++) {
                        if (audioObject[j][i] != null)
                            audioObject[j][i].repeat = false;
                    }
                    audioPlayCount = 0;
                }

            }


            //  Playing the audio by calling the method of AudioClass object
            else {
                audioPlayCount += 1;
                audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)].playAudio(this);
                audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)].repeat = true;
                playText = audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)].getString();
            }
        }
    }

    // Method called on clicking the mic button on Stage Activity
    public void recordAudio(View view){

        // Check permission to record audio
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }


        // Initialize speechRecognizer object
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        // Creating intent for recognizing speech
        String languagePref = "kn";
        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,languagePref);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,languagePref);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE,languagePref);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please repeat the word heard");


        // Exception raised if activity couldn't be called
        try{
            startActivityForResult(speechRecognizerIntent,100);}
        catch(ActivityNotFoundException a){
            Toast.makeText(this,"Does not support it",Toast.LENGTH_SHORT).show();
        }
    }


    // To call Google's speech activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Check if the speech is null and all dependencies are matched correctly
        switch (requestCode){

            // Case matched with speechRecognizerIntent
            case 100:if(resultCode==RESULT_OK&&data!=null){

                // Checking if the speech and the audio match
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                // Exception: When saying ನೀರು speech api auto converts to number.
                // Correction is as follows
                if (result.get(0).equals("2"))
                    speech_text.setText("ನೀರು");
                else
                    speech_text.setText(result.get(0));

                // Cannot compare with equals function as it is not UNICODE supported
                // Hence performing character wise comparison
                match = true;
                for(int i =0;i<playText.length();i++){
                    if(!(speech_text.getText().charAt(i) == playText.charAt(i))){
                        match = false;
                        break;
                    }
                }

                // Raising Toast and setting textColor green on match
                if(match)
                {
                    Log.e("Matched:","Speech: "+speech_text.getText() + "\nAudio: "+playText);
                    speech_text.setTextColor(Color.GREEN);
                    green_text.setVisibility(View.VISIBLE);
                    red_text.setVisibility(View.INVISIBLE);
                    Toast.makeText(this,"Congrats, the word matched",Toast.LENGTH_LONG).show();
                }

                // Raising Toast and setting textColor red on match
                else
                {
                    Log.e("Unmatched:","Speech: "+speech_text.getText() + "\nAudio: "+playText);
                    speech_text.setTextColor(Color.RED);
                    green_text.setVisibility(View.INVISIBLE);
                    red_text.setVisibility(View.VISIBLE);
                    Toast.makeText(this,"Sorry, the word didn't match",Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }


    // Checking permission if not granted in beginning of application
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }
}