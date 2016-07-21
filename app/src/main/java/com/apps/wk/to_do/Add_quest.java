package com.apps.wk.to_do;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

public class Add_quest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);
    }

    public void add(View view){

        //Get quest
        EditText editText = (EditText) findViewById(R.id.aq_edtxt_id);
        String quest=editText.getText().toString();
        //Add Quest
        Storage data = new Storage(this);
        data.addQuest(quest,this);
        //zur liste
        Intent intent = new Intent(this, Main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}


