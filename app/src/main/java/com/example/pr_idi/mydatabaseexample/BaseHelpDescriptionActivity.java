package com.example.pr_idi.mydatabaseexample;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;

public class BaseHelpDescriptionActivity extends AppCompatActivity {

    private String numHelp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getStringResourceByName(getIntent().getExtras().getString("help_menu_num")));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.help_TITLE));
    }

    private int getStringResourceByName(String aString) {
        String packageName = getPackageName();
        return  getResources().getIdentifier(aString, "layout", packageName);

       // return getString(resId);
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
