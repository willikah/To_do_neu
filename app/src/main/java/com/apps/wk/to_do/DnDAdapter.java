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
import android.widget.RatingBar;
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
    private List<Integer> diffi;



    public DnDAdapter(Context context, List<String> movies, List<Integer> diffi) {
        this.mContext = context;
        this.mMovies = movies;
        this.diffi = diffi;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.bindMovie(mMovies.get(position),diffi.get(position));


        final TextView txt_v = holder.movieNameTextView;
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //HIER KLICKEVENTS IN DEN LISTENITEMS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                //quest entfernen
                String quest = txt_v.getText().toString();
                Storage data = new Storage(mContext);
                data.removeQuest(quest, mContext);
                remove(holder.getAdapterPosition());
                data.setQuest_cnt(data.getQuest_cnt()+1,mContext); // quest cnt

                //XP setzen
                int old_lvl = data.get_lvl();
                data.setXP(data.getXP() + data.get_questXP(quest), mContext);
                Toast.makeText(v.getContext(),"+ " + String.valueOf(data.get_questXP(quest)) + " XP!", Toast.LENGTH_SHORT).show();

                //Sounds...
                final SoundPlayer sp = new SoundPlayer(mContext);
                if(old_lvl==data.get_lvl()){
                    sp.play(mContext,"xp");
                }else{
                    sp.play(mContext,"lvl_up");
                    Toast.makeText(v.getContext(),"Level Up!", Toast.LENGTH_SHORT).show();
                }

                //refresh/blink Toolbar
                Activity activity = (Activity) mContext;
                final Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar_id);
                toolbar.setSubtitle("Level " + String.valueOf(data.get_lvl()));

                toolbar.setSubtitleTextColor(Color.YELLOW);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        //Do something after 100ms

                    }
                }, 300);

                //soundplayer wieder schließen
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toolbar.setSubtitleTextColor(Color.WHITE);
                        //Do something after 100ms
                        sp.release();

                    }
                }, 4000);

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
        List<Integer> diffi = data.get_diffi();

        Collections.swap(list, firstPosition, secondPosition);
        data.setQuests(list, mContext);

        Collections.swap(diffi, firstPosition, secondPosition);
        data.set_diffi(diffi, mContext);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView movieNameTextView;
        public final CheckBox btn;
        public final RatingBar ratingBar;
        //public View cv;

        public ViewHolder(View view) {
            super(view);
            movieNameTextView = (TextView) view.findViewById(R.id.l_item_txt);
            this.btn = (CheckBox) view.findViewById(R.id.l_item_btn);
            ratingBar= (RatingBar) view.findViewById(R.id.ratingBar);


        }

        public void bindMovie(String movie, int diffi) {

            this.movieNameTextView.setText(movie);
            this.ratingBar.setRating(diffi);
            // HIER SCHWIERIGKEIT SETZEN
        }
    }

}
