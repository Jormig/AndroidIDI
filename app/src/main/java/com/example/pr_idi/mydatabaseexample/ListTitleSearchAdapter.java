package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.logging.Filter;

/**
 * Created by taos on 01/01/2017.
 */

public class ListTitleSearchAdapter extends BaseAdapter  {

    protected Activity activity;
    protected List<Film> items;
    protected List<Integer> countFilms; //numero de peliculas por actor

    public ListTitleSearchAdapter(Activity activity, List<Film> items, List<Integer> countFilms) {
        this.activity = activity;
        this.items = items;
        this.countFilms = countFilms;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        countFilms.clear();
    }

    public void addAll(List<Film> films, List<Integer> countF) {
        for (int i = 0; i < films.size(); i++) {
            items.add(films.get(i));
            countFilms.add(countF.get(i));
        }
    }


    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_titlesearch, null);
        }
        Film dir = items.get(position);
        String prota = dir.getProtagonist();
        String capTitle = capitalizeAllWords(prota);
       // int numFilmsActor = dir.getCountFilms(prota);

        TextView title = (TextView) v.findViewById(R.id.category);
        title.setText(capTitle);

        TextView numFilms = (TextView) v.findViewById(R.id.texto2) ;
        numFilms.setText(Integer.toString(countFilms.get(position))+ " Films");

        return v;
    }





    private static String capitalizeAllWords(String str) {
        String phrase = "";
        boolean capitalize = true;
        for (char c : str.toLowerCase().toCharArray()) {
            if (Character.isLetter(c) && capitalize) {
                phrase += Character.toUpperCase(c);
                capitalize = false;
                continue;
            } else if (c == ' ') {
                capitalize = true;
            }
            phrase += c;
        }
        return phrase;
    }
}