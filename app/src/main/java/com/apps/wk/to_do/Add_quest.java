package com.apps.wk.to_do;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class Add_quest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);

        EditText editText= (EditText) findViewById(R.id.aq_edtxt_id);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

    }



    public void add(View view) {

        //Get quest
        EditText editText = (EditText) findViewById(R.id.aq_edtxt_id);
        String quest = editText.getText().toString();
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        int diffi = Math.round(ratingBar.getRating());
       // int diffi = ratingBar.getNumStars();


        //Add Quest
        Storage data = new Storage(this);
        data.addQuest(quest, this);
        data.add_diffi(diffi,this);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        //zur liste
        finish();

    }


}


