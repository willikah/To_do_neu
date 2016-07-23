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
    private int XP;
    private int quest_cnt;
    private int last_needed_XP; // XP die für das erreichen des letzten lvls benötigt wurde
    private int needed_XP; //XP die für das erreichen vom letzten zum nächsten lvl benötig wird
    private int lvl;
    private String diffi;
    Context context;

    public void setQuest_cnt(int quest_cnt,Context context) {
        this.quest_cnt = quest_cnt;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("quest_cnt",quest_cnt);
        editor.commit();
    }

    public int getQuest_cnt() {

        return quest_cnt;
    }

    public void setXP(int XP, Context context) {

        this.XP = XP;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("XP",XP);



            //lvl up
            while(get_XP_left()<= 0) {
                this.last_needed_XP = needed_XP + last_needed_XP;
                this.needed_XP = needed_XP * 2;
                this.lvl++;
            }


        editor.putInt("lvl",lvl);
        editor.putInt("needed_XP",needed_XP);
        editor.putInt("last_needed_XP",last_needed_XP);

        editor.commit();
    }

    public int get_XP_left(){
        return needed_XP-(XP-last_needed_XP);
    }

    public int get_progress(){
        double p =(((double)XP-(double)last_needed_XP)/(double)needed_XP)*100;
        return (int) p;
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
        this.lvl = sharedPreferences.getInt("lvl",1);
        this.last_needed_XP = sharedPreferences.getInt("last_needed_XP",0);
        this.needed_XP = sharedPreferences.getInt("needed_XP",100);
        this.quest_cnt = sharedPreferences.getInt("quest_cnt",0);
        this.diffi = sharedPreferences.getString("diffi","");
        this.context = context;
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

    public void add_diffi(int diffi,Context context) {

        this.diffi = this.diffi + String.valueOf(diffi) + TRENNZEICHEN;
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("diffi",this.diffi);
        editor.commit();

    }
    ////////////////////////////////////////////////////////////

    public void removeQuest(String quest, Context context){

        List<String> list = getQuests();
        List<Integer> diffi_list = get_diffi();

        //entfernen

        diffi_list.remove(list.indexOf(quest));
        list.remove(quest);

        setQuests(list,context);
        set_diffi(diffi_list,context);

    }

    public int get_questXP(String quest){
        List<String> list = getQuests();
        List<Integer> diffi_list = get_diffi();
        int diffi= diffi_list.get(list.indexOf(quest));
        int XP;
        if(diffi==0){
            XP = 50;
        }else if(diffi==1) {
            XP = 150;
        }else if(diffi==2) {
            XP = 250;
        }else if(diffi==3) {
            XP = 400;
        }else if(diffi==4) {
            XP = 800;
        }else {
            XP=0;
        }

        return XP;
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

    public void set_diffi(List<Integer> diffi, Context context){

        StringBuilder csvList = new StringBuilder();
        for(Integer s : diffi){
            csvList.append(s.toString());
            csvList.append(TRENNZEICHEN);
        }
        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("diffi",csvList.toString());
        editor.commit();

    }
    ////////////////////////////////////////////////////////////////

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

    public List<Integer> get_diffi() {

        List<Integer> emptyList = Collections.emptyList();

        if (diffi.isEmpty())
            return emptyList;
        else{
            List<Integer> diffi_list = new ArrayList<Integer>();
            String[] items = diffi.split(TRENNZEICHEN);
            for (int i = 0; i < items.length; i++) {
                diffi_list.add(Integer.parseInt(items[i]));
            }
            return diffi_list;
        }
    }

    public void delete(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();

    }

    public int get_lvl(){
        return lvl;

    }




}
