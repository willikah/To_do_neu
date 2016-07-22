package com.apps.wk.to_do;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;



public class Main extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Daten laden
        Storage data = new Storage(this);

        //beim Erststart create char
        if (data.getName().equals("")) {
            Intent intent = new Intent(this, Create_Char.class);
            startActivity(intent);
            finish();
            return;
        }

        //Create Toolbar
        create_toolbar(data);

        //Liste
       create_List(data);

    }

    public void create_toolbar(Storage data){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(data.getName());// Char name
        getSupportActionBar().setIcon(data.getAvatar()); // Char pic
        getSupportActionBar().setSubtitle(String.valueOf(data.get_lvl())+" "+String.valueOf(data.getXP()));
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar_id);
        progressBar.setProgress(data.get_progress());

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // neue Liste
        Storage data = new Storage(this);
        create_List(data);

    }


    public void create_List(Storage data){

        List<String> quests = data.getQuests();
        // Setup RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
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
        new SoundPlayer(this).play(this, "select");
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
