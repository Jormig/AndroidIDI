package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Haloman on 20/11/2016.
 */

public class ListFilmActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listfilmview);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        FilmData filmData = new FilmData(this);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        filmData.open();
        String order = MySQLiteHelper.COLUMN_YEAR_RELEASE;
        order.concat(" DESC");
        List<Film> myDataset = filmData.getAllFilms(order);
        mAdapter = new ListFilmAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
