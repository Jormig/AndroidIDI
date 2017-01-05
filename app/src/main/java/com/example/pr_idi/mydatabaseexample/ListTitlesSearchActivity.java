package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taos on 01/01/2017.
 */


public class ListTitlesSearchActivity extends BaseActivity {

    private List<Film> myDataset;
    private List<Integer> myCountFilms;
    private FilmData myFilmData;
    private ListView lv;
    private FilmData filmData;
    private ListTitleSearchAdapter adapter;
    private  String from = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDraweron(R.layout.list_titles);
        try{
            from  = getIntent().getExtras().getString("from");
        }
        catch (Exception e){
        }

    }

    protected void onResume() {
        super.onResume();

        myFilmData = new FilmData(this);
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_PROTAGONIST;
        myDataset = myFilmData.getAllActors(order.concat(" ASC"));
        lv = (ListView) findViewById(R.id.ListView_listado);
        myCountFilms = myFilmData.getCountFilms( myDataset);
        adapter = new ListTitleSearchAdapter(this, myDataset,myCountFilms);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int index,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                final  String prota = myDataset.get(index).getProtagonist();
                intent.putExtra("query",prota.toLowerCase() );
                startActivity(intent);
            }
        });
        myFilmData.close();
    }


    private void reloadAllData(){
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_PROTAGONIST;
        myDataset = myFilmData.getAllActors(order.concat(" ASC"));
        adapter.clear();
        adapter.addAll(myDataset,myCountFilms);
        adapter.notifyDataSetChanged();
        myFilmData.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if ( from !=null && from.equals("navigationdrawer"))
            inflater.inflate(R.menu.menu_search_active, menu);
        else inflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        /** Cambio para set search focus*/
        if ( from !=null && from.equals("navigationdrawer")){
            searchView.setIconifiedByDefault(false);
            searchView.setIconified(false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                intent.putExtra("query",query.toLowerCase() );
                startActivity(intent);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }




}
