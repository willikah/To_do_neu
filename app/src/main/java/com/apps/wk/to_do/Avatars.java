package com.apps.wk.to_do;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Avatars extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatars);

    }

    public void select_avatar(View view) {

        //avatar speichern
        int resID = getResources().getIdentifier(view.getTag().toString(), "mipmap", "com.apps.wk.to_do");
        Storage data = new Storage(this);
        data.setAvatar(resID,this);
        finish();

    }

}
