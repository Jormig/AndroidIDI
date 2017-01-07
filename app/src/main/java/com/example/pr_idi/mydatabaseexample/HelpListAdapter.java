package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by taos on 01/01/2017.
 */

public class HelpListAdapter extends BaseAdapter  {

    protected Activity activity;
    protected List<String> items;


    public HelpListAdapter(Activity activity, List<String> items) {
        this.activity = activity;
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();

    }

    public void addAll(List<String> menu) {
        for (int i = 0; i < menu.size(); i++) {
            items.add(menu.get(i));

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
            v = inf.inflate(R.layout.item_help, null);
        }
        String itemMenu = items.get(position);

        TextView menuTView = (TextView) v.findViewById(R.id.item_help_text);
        menuTView.setText(itemMenu);




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