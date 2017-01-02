package com.example.pr_idi.mydatabaseexample;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
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
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                // TODO
                final Dialog rankDialog;
                final RatingBar ratingBar;
                final Film filme = myDataset.get(pos);
                rankDialog = new Dialog(ListTitleActivity.this, R.style.FullHeightDialog);
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

                        MySQLiteHelper  dbHelper = new MySQLiteHelper(ListTitleActivity.this);
                        FilmData filmData = new FilmData(ListTitleActivity.this);
                        filmData.open();
                        String st[] = rate.getText().toString().split("/");
                        int rating = Integer.parseInt(st[0]);
                        filmData.updateRating(rating,filme.getId() );
                        filmData.close();
                        reloadAllData();
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





}
