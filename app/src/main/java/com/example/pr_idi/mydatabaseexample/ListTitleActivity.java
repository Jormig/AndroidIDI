package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * Created by taos on 01/01/2017.
 */

public class ListTitleActivity extends BaseActivity {

    private List<Film> myDataset;
    private FilmData myFilmData;
    private ListView lv;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDraweron(R.layout.list_titles);



    }

    protected void onResume() {
        super.onResume();

        myFilmData = new FilmData(this);
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_TITLE;
        myDataset = myFilmData.getAllFilms(order.concat(" ASC"));
        lv = (ListView) findViewById(R.id.ListView_listado);
        ListTitleAdapter adapter = new ListTitleAdapter(this, myDataset);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                //TODO



            }
        });

    }




}
