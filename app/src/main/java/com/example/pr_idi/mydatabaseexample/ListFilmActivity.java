package com.example.pr_idi.mydatabaseexample;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Haloman on 20/11/2016.
 */

<<<<<<< HEAD
public class ListFilmActivity extends BaseActivity {
=======
public class ListFilmActivity extends AppCompatActivity {
>>>>>>> 0156792a01e8ab1f9097cba32894dd6101015cdf
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        //setContentView(R.layout.listfilmview);
        super.onCreateDraweron(R.layout.listfilmview); //SetContent in BaseActivy Layout

=======
        setContentView(R.layout.listfilmview);
>>>>>>> 0156792a01e8ab1f9097cba32894dd6101015cdf
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
        List<Film> myDataset = filmData.getAllFilms(order.concat(" DESC"));
        mAdapter = new ListFilmAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
<<<<<<< HEAD
        //setTitle(R.string.app_name);
    }
    /*public boolean onCreateOptionsMenu(Menu menu) {
=======
    }
    public boolean onCreateOptionsMenu(Menu menu) {
>>>>>>> 0156792a01e8ab1f9097cba32894dd6101015cdf
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_item:
                Intent intent = new Intent(this,PantallaAltaFilmActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
<<<<<<< HEAD
    }*/
=======
    }
>>>>>>> 0156792a01e8ab1f9097cba32894dd6101015cdf
}
