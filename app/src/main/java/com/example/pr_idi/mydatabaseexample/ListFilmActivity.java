package com.example.pr_idi.mydatabaseexample;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import static android.graphics.Color.BLACK;

/**
 * Created by Haloman on 20/11/2016.
 */

public class ListFilmActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Film> myDataset;
    private FilmData myFilmData;
    private static boolean deleteMode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.listfilmview);
        super.onCreateDraweron(R.layout.listfilmview);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //para snackbar consejos
        firstHelp();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // specify an adapter (see also next example)
        myFilmData = new FilmData(this);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_YEAR_RELEASE;
        myDataset = myFilmData.getAllFilms(order.concat(" DESC"));
        mAdapter = new ListFilmAdapter(myDataset,this);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulistapelis, menu);

        /** To search**/
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
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
        /**end - To search**/
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add_item:
                intent = new Intent(this, PantallaAltaFilmActivity.class);
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
            case R.id.action_about:
                intent = new Intent(this, About.class);
                startActivity(intent);
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void firstHelp() {
        final SharedPreferences settings  = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);

        if (settings.getBoolean("habilitarConsejoListFilmActivity", true)) {

            Snackbar.make(findViewById(R.id.my_recycler_view), R.string.consejo_lisFilmAct, Snackbar.LENGTH_INDEFINITE)
                    //.setActionTextColor(Color.CYAN)
                    .setActionTextColor(getResources().getColor(R.color.snackbar_action))
                    .setAction("[X]", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Snackbar", "Pulsada acción snackbar!");
                            SharedPreferences.Editor editor = settings .edit();
                            editor.putBoolean("habilitarConsejoListFilmActivity", false);
                            editor.commit();
                        }
                    })
                    .show();
        }
    }

    public FilmData getMyFilmData(){
        return myFilmData;
    }
    public List<Film> getMyDataset(){
        return myDataset;
    }
    public static void setDeleteMode(){
        deleteMode = !deleteMode;
    }
    public static boolean getDeleteMode(){
        return deleteMode;
    }
}
