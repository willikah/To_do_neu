package com.apps.wk.to_do;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.List;



public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Daten laden
        Storage data = new Storage(this);
        String name = data.getName();
        int avatar = data.getAvatar();
        List<String> quests = data.getQuests();

        //beim Erststart create char
        if (name.equals("")) {
            Intent intent = new Intent(this, Create_Char.class);
            startActivity(intent);
        }

        //Create Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);// Char name
        getSupportActionBar().setIcon(avatar); // Char pic
        getSupportActionBar().setSubtitle(String.valueOf(data.getXP()));


        //Liste
        // Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        // Setup Adapter
        DnDAdapter DnD_Adapter = new DnDAdapter(this, quests);
        recyclerView.setAdapter(DnD_Adapter);
        // Setup ItemTouchHelper
        ItemTouchHelper.Callback callback = new DnDTouchHelper(DnD_Adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

    }

    //Create Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //neue Quest
    public void add_q(View view){

        Intent intent = new Intent(this, Add_quest.class);
        startActivity(intent);
    }

    //Für Klicks in Menü
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_char_id:
                //Aktion
                Intent intent = new Intent(this, Create_Char.class);
                startActivity(intent);
                break;

            case R.id.menu_quit_id:
                //Aktion

                //Delete Data FÜR TESTZWECKE
                Storage data = new Storage(this);
                data.delete(this);

                finish();
                System.exit(0);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
