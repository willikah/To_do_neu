package com.apps.wk.to_do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class Create_Char extends AppCompatActivity {

    public SoundPlayer sp = new SoundPlayer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__char);

        //avatar laden/anzeigen wenn gewählt

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Storage data = new Storage(this);
        int avatar = data.getAvatar();
        if (avatar!=0) {
            ImageButton imageButton = (ImageButton) findViewById(R.id.ch_c_AvBut_id);
            imageButton.setImageResource(avatar);
        }
        sp.play(this,"select");
    }


    //beim klicken von save button
    public void save(View view) {

        sp.release();
        //Get Name
        EditText editText = (EditText) findViewById(R.id.ch_c_edName_id);
        String name=editText.getText().toString();
        //Save Name
        Storage data = new Storage(this);
        data.setName(name,this);
        //zur liste
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
        finish();

    }

    //beim klicken von avatar button
    public void goto_Ava(View view) {

        sp.play(this, "select");
        //Get Name
        EditText editText = (EditText) findViewById(R.id.ch_c_edName_id);
        String name=editText.getText().toString();
        //Save Name
        Storage data = new Storage(this);
        data.setName(name,this);
        //go to Avatars
        Intent intent = new Intent(this, Avatars.class);
        startActivity(intent);


    }
}
