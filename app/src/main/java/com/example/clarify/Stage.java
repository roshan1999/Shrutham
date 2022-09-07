package com.example.clarify;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.example.clarify.Audio.AudioClass;
import com.example.clarify.Choice.BtnChoice;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Stage extends AppCompatActivity {

    // View variables
    FloatingActionButton level_audio, level_speech;
    TextView level_heading;
    TextView speech_text;
    Button []choice = new Button[4];
    int correct_answer =0;

    // Variable
    String level_info;
    int level, stage, buttonCount = 4, correctIndex = 0, countAudioTracks = 5;
    static int audioPlayCount=0;
    public static final Integer RecordAudioRequestCode = 1;
    boolean match = true;
    SpeechRecognizer speechRecognizer;
    String playText;
    AudioClass audioObject[][] = new AudioClass[4][countAudioTracks/4 +1];
    BtnChoice Btn[] = new BtnChoice[buttonCount];
    Toast current_Toast;
    int randTrack=-1;
    int randNoise=-1;


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
        choice[0] = findViewById(R.id.choice1);
        choice[1] = findViewById(R.id.choice2);
        choice[2] = findViewById(R.id.choice3);
        choice[3] = findViewById(R.id.choice4);

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

        for(int i =0;i<buttonCount;i++)
        {
            choice[i].setBackgroundColor(Color.WHITE);
            choice[i].setTextColor(Color.BLACK);
        }
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
    public void play(){
        // Defining the bounds
        int lb =  ((countAudioTracks*(level-1))+  ((countAudioTracks/4)*(stage-1)))+1;
        int ub =  ((countAudioTracks*(level-1))+  ((countAudioTracks/4)*(stage)))+1;

        // Generate random track and noise
        randTrack = ThreadLocalRandom.current().nextInt(lb, ub+1);
        randNoise = ThreadLocalRandom.current().nextInt(1,4);


        // Adjust the random track based upon the stage
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
                randTrack = ThreadLocalRandom.current().nextInt(lb,ub+1);
                randNoise = ThreadLocalRandom.current().nextInt(1,4);

                // Creating new object for the new unused track and noise
                // null test ensures audio not already used
                if (audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)] == null) {
                    createAudioObject(randNoise, randTrack);
                }
            }

            // Once all the audio is played at-least once, raise toast and clear
            if (audioPlayCount == (countAudioTracks / 4 + 1)) {

                // Toast raised to indicate level cleared
                Toast.makeText(this, "You have cleared this level!" + correct_answer, Toast.LENGTH_LONG).show();

                // Sending back to the trainPath activity to update stars
                Intent trace_star = new Intent(this, TrainPath.class);
                trace_star.putExtra("AudioCorrectCount", correct_answer);
                // Add this to database. Then update on TrainPath from database.
                finish();
                startActivity(trace_star);

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

    // Method called on clicking the repeat button in Stage Activity
    public void nextAudio(View view) {
        // Playing Audio After Randomizing
        play();

        // Making multiple choice answers
        createChoice();
    }

    private void createChoice() {
        // Make a random Correct Index
        correctIndex = ThreadLocalRandom.current().nextInt(0,4);

        // Initialize all buttons with some random word (not all correct at same time)
        for (int i =0;i<buttonCount;i++)
        {
            Btn[i] = new BtnChoice(choice[i], getApplicationContext());
            if(i!=correctIndex){
                Log.d("Debug", String.valueOf(i));
                Btn[i].setText();
            }
        }
        // Set one of the button as the correct choice
        Btn[correctIndex].setText(playText);


        // Setting all colors to normal
        for(int i =0;i<buttonCount;i++)
        {
            Btn[i].choice.setBackgroundColor(Color.WHITE);
            Btn[i].choice.setTextColor(Color.BLACK);
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
            case 100:
                if(resultCode==RESULT_OK&&data!=null){

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
                    matched();
                }

                // Raising Toast and setting textColor red on match
                else
                {
                    Log.e("Unmatched:","Speech: "+speech_text.getText() + "\nAudio: "+playText);
                    speech_text.setTextColor(Color.RED);
                    Toast.makeText(this,"Sorry, the word didn't match",Toast.LENGTH_LONG).show();
                }
                resetSpeech();
            }
            break;
        }
    }

    private void resetSpeech() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        speech_text.setText("Your speech will show here");
                        speech_text.setTextColor(Color.WHITE);
                    }
                });
            }
        }).start();
    }


    // Checking permission if not granted in beginning of application
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    public void clickChoice(View view) {
        if(current_Toast!=null)
        {
            current_Toast.cancel();
        }
        for(int i =0;i<4;i++)
        {
            if(findViewById(view.getId())==choice[i]){
                if(i==correctIndex) {
                    matched();
                }
                else{
                    current_Toast = Toast.makeText(getApplicationContext(), "Incorrect Answer", Toast.LENGTH_SHORT);
                    current_Toast.show();
                    Btn[i].setFalse();
                }
            }

        }

    }

    private void matched() {
        current_Toast = Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT);
        current_Toast.show();
        correct_answer+=1;
        Btn[correctIndex].setTrue();

        // Create a delay to indicate change to next audio
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        play();
                        createChoice();
                    }
                });
            }
        }).start();
    }

    public void repeatAudio(View view) {
        if(randTrack!=-1 && randNoise!=-1)
            audioObject[randNoise][randTrack % (countAudioTracks / 4 + 1)].playAudio(this);
        else
            Toast.makeText(this, "Press the play button first", Toast.LENGTH_SHORT).show();
    }
}