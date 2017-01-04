package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
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

public class ListCalificarActivity extends BaseActivity {


    private List<Film> myDataset;
    private FilmData myFilmData;
    private ListView lv;
    private FilmData filmData;
    private ListTitleAdapter adapter;

    @Override
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
        adapter = new ListTitleAdapter(this, myDataset);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int index,
                                    long id) {
                final int puntajeIni;
                final int pos = index;
                final View fview = itemView;
                final Dialog rankDialog;
                final RatingBar ratingBar;
                final Film filme = myDataset.get(pos);
                puntajeIni = filme.getCritics_rate();
                rankDialog = new Dialog(ListCalificarActivity.this, R.style.FullHeightDialog);
                rankDialog.setContentView(R.layout.dialog_rating);
                rankDialog.setCancelable(true);
                ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                ratingBar.setRating((float)filme.getCritics_rate()/2);

                TextView text = (TextView) rankDialog.findViewById(R.id.rank_title);
                text.setText(filme.getTitle());

                final TextView rate = (TextView) rankDialog.findViewById(R.id.rate_dialog);
                rate.setText(String.valueOf(filme.getCritics_rate()+"/10"));

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

                        MySQLiteHelper  dbHelper = new MySQLiteHelper(ListCalificarActivity.this);
                        FilmData filmData = new FilmData(ListCalificarActivity.this);
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
                                        MySQLiteHelper  dbHelper = new MySQLiteHelper(ListCalificarActivity.this);
                                        FilmData filmData = new FilmData(ListCalificarActivity.this);
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





}