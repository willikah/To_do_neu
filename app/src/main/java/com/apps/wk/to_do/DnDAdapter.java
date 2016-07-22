package com.apps.wk.to_do;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import android.os.Handler;


/**
 * Für Drag n Drop
 */
public class DnDAdapter extends RecyclerView.Adapter<DnDAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mMovies;


    public DnDAdapter(Context context, List<String> movies) {
        this.mContext = context;
        this.mMovies = movies;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.bindMovie(mMovies.get(position));

        final TextView txt_v = holder.movieNameTextView;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //HIER KLICKEVENTS IN DEN LISTENITEMS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                new SoundPlayer(mContext).play(mContext, "lvl");

                //quest aus prefs entfernen
                String quest = txt_v.getText().toString();
                Toast.makeText(v.getContext(), "+50XP", Toast.LENGTH_SHORT).show();
                Storage data = new Storage(mContext);
                data.removeQuest(quest, mContext);
                data.setXP(data.getXP() + 50, mContext);
                //remove
                remove(holder.getAdapterPosition());

                //refresh/blink Toolbar
                Activity activity = (Activity) mContext;
                final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar_id);
                toolbar.setSubtitle(String.valueOf(data.get_lvl())+" "+String.valueOf(data.getXP()));

                toolbar.setSubtitleTextColor(Color.YELLOW);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        //Do something after 100ms

                    }
                }, 300);

                //Progressbar setzen
                ProgressBar progressBar = (ProgressBar) ((Activity) mContext).findViewById(R.id.progressbar_id);
                progressBar.setProgress(data.get_progress());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void remove(int position) {
        mMovies.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(int firstPosition, int secondPosition) {

        Collections.swap(mMovies, firstPosition, secondPosition);
        notifyItemMoved(firstPosition, secondPosition);

        //geänderte Reihenfolge speichern
        Storage data = new Storage(mContext);
        List<String> list = data.getQuests();
        Collections.swap(list, firstPosition, secondPosition);
        data.setQuests(list, mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView movieNameTextView;
        public final CheckBox btn;
        //public View cv;

        public ViewHolder(View view) {
            super(view);
            movieNameTextView = (TextView) view.findViewById(R.id.l_item_txt);
            this.btn = (CheckBox) view.findViewById(R.id.l_item_btn);
            //cv = view.findViewById(R.id.cv_id);

        }

        public void bindMovie(String movie) {

            this.movieNameTextView.setText(movie);
        }
    }

}
