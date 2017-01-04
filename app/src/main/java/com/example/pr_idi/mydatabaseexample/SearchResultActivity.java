package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class SearchResultActivity extends BaseActivity {

    private List<Film> myDataset;
    private FilmData myFilmData;
    private ListView lv;
    //private FilmData filmData;
    private ListTitleAdapter adapter;
    private String query;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.searchresult);
        super.onCreateDraweron(R.layout.searchresult);


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

        myFilmData.close();
    }


}
