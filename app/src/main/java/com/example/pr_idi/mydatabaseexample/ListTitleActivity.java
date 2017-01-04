package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by taos on 01/01/2017.
 */

public class ListTitleActivity extends BaseActivity {

    private List<Film> myDataset;
    private FilmData myFilmData;
    private ListView lv;
    private FilmData filmData;
    private ListTitleAdapter adapter;
    private  String from = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateDraweron(R.layout.list_titles);
        firstHelp();
    }

    protected void onResume() {
        super.onResume();

        myFilmData = new FilmData(this);
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_TITLE;
        myDataset = myFilmData.getAllFilms(order.concat(" ASC"));
        lv = (ListView) findViewById(R.id.ListView_listado);
        adapter = new ListTitleAdapter(this, myDataset);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int puntajeIni;
                final int pos = position;
                final Dialog rankDialog;
                final View fview = view;
                final RatingBar ratingBar;
                final Film filme = myDataset.get(pos);
                rankDialog = new Dialog(ListTitleActivity.this, R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.dialog_rating);
                rankDialog.setCancelable(true);
                ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating((float)filme.getCritics_rate()/2);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_title);
                text.setText(filme.getTitle());

                puntajeIni = filme.getCritics_rate();
                final TextView rate = (TextView) rankDialog.findViewById(R.id.rate_dialog);
                rate.setText(String.valueOf(puntajeIni+"/10"));
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                boolean fromUser) {
                        //Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_LONG).show();
                        rate.setText(String.valueOf((int)(rating*2)+"/10"));
                    }
                });
                Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MySQLiteHelper  dbHelper = new MySQLiteHelper(ListTitleActivity.this);
                        FilmData filmData = new FilmData(ListTitleActivity.this);
                        filmData.open();
                        String st[] = rate.getText().toString().split("/");
                        int rating = Integer.parseInt(st[0]);
                        filmData.updateRating(rating,filme.getId() );
                        filmData.close();
                        reloadAllData();
                        Snackbar.make(fview, "Calificación cambiada", Snackbar.LENGTH_LONG)
                                //.setActionTextColor(Color.CYAN)
                                .setActionTextColor(getResources().getColor(R.color.snackbar_action))
                                .setAction("DESHACER", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.i("Snackbar", "Pulsada acción snackbar!");
                                        MySQLiteHelper  dbHelper = new MySQLiteHelper(ListTitleActivity.this);
                                        FilmData filmData = new FilmData(ListTitleActivity.this);
                                        filmData.open();
                                        filmData.updateRating(puntajeIni,filme.getId() );
                                        filmData.close();
                                        reloadAllData();
                                    }
                                })
                                .show();
                        rankDialog.dismiss();
                    }
                });
                //now that the dialog is set up, it's time to show it
                rankDialog.show();
                return true;
            }
        });
        myFilmData.close();
    }


    private void reloadAllData(){
        myFilmData.open();
        String order = MySQLiteHelper.COLUMN_TITLE;
        myDataset = myFilmData.getAllFilms(order.concat(" ASC"));
        adapter.clear();
        adapter.addAll(myDataset);
        adapter.notifyDataSetChanged();
        myFilmData.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

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

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
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



    public void firstHelp() {
        final SharedPreferences settings  = getSharedPreferences("MiPreferencia", Context.MODE_PRIVATE);

        if (settings .getBoolean("habilitarConsejoListTitleActivity", true)) {

            Snackbar.make(findViewById(R.id.ListView_listado), R.string.consejo_lisTitleAct, Snackbar.LENGTH_INDEFINITE)
                    //.setActionTextColor(Color.CYAN)
                    .setActionTextColor(getResources().getColor(R.color.snackbar_action))
                    .setAction("[X]", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.i("Snackbar", "Pulsada acción snackbar!");
                            SharedPreferences.Editor editor = settings .edit();
                            editor.putBoolean("habilitarConsejoListTitleActivity", false);
                            editor.commit();
                        }
                    })
                    .show();
            }
        }
}
