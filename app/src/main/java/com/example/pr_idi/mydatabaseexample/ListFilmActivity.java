package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import static android.graphics.Color.BLACK;

/**
 * Created by Haloman on 20/11/2016.
 */

public class ListFilmActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Film> myDataset;
    private static boolean deleteMode = false;
    @Override
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        // specify an adapter (see also next example)
        FilmData filmData = new FilmData(this);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        filmData.open();
        String order = MySQLiteHelper.COLUMN_YEAR_RELEASE;
        myDataset = filmData.getAllFilms(order.concat(" DESC"));
        mAdapter = new ListFilmAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("TAG","ASD");
                v.setBackgroundColor(Color.BLACK);
            }
        });

    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (deleteMode){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_delete_items, menu);
        }else{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menulistapelis, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_item:
                Intent intent = new Intent(this, PantallaAltaFilmActivity.class);
                startActivity(intent);
            return true;
            case R.id.action_delete_item:
                String text = "";
                for (Film model : myDataset) {
                    if (model.isSelected()) {
                        text += model.getTitle();
                    }
                }
                Log.d("TAG","Output : " + text);
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static void setDeleteMode(){
        deleteMode = !deleteMode;
    }
    public static boolean getDeleteMode(){
        return deleteMode;
    }
}
