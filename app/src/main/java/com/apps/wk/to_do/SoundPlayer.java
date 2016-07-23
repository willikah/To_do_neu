package com.apps.wk.to_do;

import android.app.Application;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by willi on 21.07.2016.
 */
public class SoundPlayer extends Application implements SoundPool.OnLoadCompleteListener {
    Context mContext;
    SoundPool soundPool;

    int soundID;

    public SoundPlayer(Context context) {

        this.mContext=context;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder soundPoolBuilder;
            AudioAttributes attributes;
            AudioAttributes.Builder attributesBuilder;

            attributesBuilder = new AudioAttributes.Builder();
            attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            attributes = attributesBuilder.build();

            soundPoolBuilder = new SoundPool.Builder();
            soundPoolBuilder.setAudioAttributes(attributes);
            this.soundPool = soundPoolBuilder.build();
        }
        else{
            this.soundPool= new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        this.soundPool.setOnLoadCompleteListener(this);


    }

    public void play(Context context,String sound){

        soundID = soundPool.load(context,context.getResources().getIdentifier(sound,"raw", context.getPackageName()),1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        soundPool.play(sampleId,1,1,0,0,1);

    }
    public void release(){
        soundPool.release();
    }


}
