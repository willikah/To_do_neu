package com.apps.wk.to_do;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by willi on 20.07.2016.
 */
public class Storage {

    private static String TRENNZEICHEN = "%%%%%%%%";
    private String name;
    private int avatar;
    private String quests;
    private int XP=0;

    public void setXP(int XP,Context context) {

        this.XP = XP;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("XP",XP);
        editor.commit();
    }

    public int getXP() {

        return XP;
    }

    public Storage(Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        this.name = sharedPreferences.getString("name","");
        this.avatar = sharedPreferences.getInt("avatar",0);
        this.quests = sharedPreferences.getString("quests","");
        this.XP = sharedPreferences.getInt("XP",0);
    }

    public void setName(String name,Context context) {
        this.name = name;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("name",name);
        editor.commit();
    }

    public void setAvatar(int avatar,Context context) {
        this.avatar = avatar;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("avatar",avatar);
        editor.commit();
    }

    public void addQuest(String quest,Context context) {

        this.quests = this.quests + quest + TRENNZEICHEN;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("quests",this.quests);
        editor.commit();

    }

    public void removeQuest(String quest, Context context){
        List<String> list = getQuests();
        list.remove(quest);

        StringBuilder csvList = new StringBuilder();
        for(String s : list){
            csvList.append(s);
            csvList.append(TRENNZEICHEN);
        }
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("quests",csvList.toString());
        editor.commit();

    }

    public void setQuests(List<String> quests, Context context){

        StringBuilder csvList = new StringBuilder();
        for(String s : quests){
            csvList.append(s);
            csvList.append(TRENNZEICHEN);
        }
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("quests",csvList.toString());
        editor.commit();

    }

    public String getName() {
        return name;
    }

    public int getAvatar() {
        return avatar;
    }

    public List<String> getQuests() {

        List<String> emptyList = Collections.emptyList();

        if (quests.equals(""))
                return emptyList;
        else{
            List<String> quest_list = new ArrayList<String>();
            ;
            String[] items = quests.split(TRENNZEICHEN);
            for (int i = 0; i < items.length; i++) {
                quest_list.add(items[i]);
            }
            return quest_list;
        }
    }

    public void delete(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

    }
}
