package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by taos on 01/01/2017.
 */


public class SearchResultActivity extends AppCompatActivity {

    private List<Film> myDataset;
    private FilmData myFilmData;
    private ListView lv;
    //private FilmData filmData;
    private ListTitleAdapter adapter;
    private String query;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchresult);
        //super.onCreateDraweron(R.layout.searchresult);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


   protected void onResume() {
        super.onResume();
        query = getIntent().getExtras().getString("query");
        myFilmData = new FilmData(this);
        myFilmData.open();
        myDataset = myFilmData.getFilmsfromActor(query);
        lv = (ListView) findViewById(R.id.listview_searchresult_layout);
        adapter = new ListTitleAdapter(this, myDataset);
        lv.setAdapter(adapter);
        setTitle(capitalizeAllWords(query));
        myFilmData.close();
        String actor = " ";
       if (myDataset.size()<=0) lv.setEmptyView(findViewById(R.id.busquedaVacia));
       else actor = myDataset.get(0).getProtagonist();
       TextView tituloHelp = (TextView)   findViewById(R.id.titulo_search_films) ;
       tituloHelp.setText(getString(R.string.titulo_search_films) + " de " + actor);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
