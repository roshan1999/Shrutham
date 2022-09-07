package com.example.clarify.Choice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.clarify.R;
import com.example.clarify.Words;

import java.util.concurrent.ThreadLocalRandom;

public class BtnChoice {
    public Button choice;
    Context context;
    public BtnChoice(Button choice, Context context){
        this.choice = choice;
        this.context = context;
    }
    public void setText(){ choice.setText(Words.word.get(ThreadLocalRandom.current().nextInt(1,Words.word.size()+1))); }
    public void setText(String text)
    {
        choice.setText(text);
    }

    public void setTrue() {
        Log.d("Debug","Setting True");
        choice.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        choice.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        choice.getBackground().setAlpha(255);
    }
    public void setFalse()
    {
        Log.d("Debug", "Setting False");
        choice.setTextColor(ContextCompat.getColor(context, R.color.white));
        choice.setBackgroundColor(ContextCompat.getColor(context,R.color.falseFG));
        choice.getBackground().setAlpha(255);
    }
    public void setNormal(Drawable background)
    {
        Log.d("Debug", "Setting Normal");
        choice.setBackground(background);
        choice.setTextColor(ContextCompat.getColor(context, R.color.white));
    }
}
