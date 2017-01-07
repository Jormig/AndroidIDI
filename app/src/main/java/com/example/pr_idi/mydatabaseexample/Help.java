package com.example.pr_idi.mydatabaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import static com.example.pr_idi.mydatabaseexample.R.id.textView2;

public class Help extends AppCompatActivity {
    private List<String> menus;
    private ListView lv;
    private HelpListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Help");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tituloHelp = (TextView)   findViewById(R.id.titulo_help) ;
        tituloHelp.setText(R.string.title_help);
        menus = Arrays.asList(getString(R.string.help_menu1),
                            getString(R.string.help_menu2),
                            getString(R.string.help_menu3),
                            getString(R.string.help_menu4),
                            getString(R.string.help_menu5),
                            getString(R.string.help_menu6),
                            getString(R.string.help_menu7),
                            getString(R.string.help_menu8));
        adapter = new HelpListAdapter(this, menus);
        lv =(ListView)findViewById(R.id.list_help);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int index,
                                    long id) {
                Intent intent = new Intent(getApplicationContext(), BaseHelpDescriptionActivity.class);
                String menuitem = "helpdescription"+String.valueOf(index+1);
                intent.putExtra("help_menu_num", menuitem );
                startActivity(intent);


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}