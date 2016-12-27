package com.example.pr_idi.mydatabaseexample;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static java.lang.String.*;


public class BaseActivity extends AppCompatActivity {
    protected String Titlevity;
    protected Toolbar mtoolbar;
    protected FrameLayout content;
    protected ActionBarDrawerToggle drawerToggle;

    private DrawerLayout drawerLayout;
    private String drawerTitle;


    protected void onCreateDraweron(final int layoutResID) {
        setContentView(R.layout.activity_drawer);
        //Contenido hacia para la vista principal
        //layoutResID: R.layout.{LAYOUT_ACTIVITY_CONTENT}
        content = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, content, true);

        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mtoolbar != null) {
            setSupportActionBar(mtoolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        } else Toast.makeText(this, "NULL TOOLBAR", Toast.LENGTH_SHORT).show();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.left_drawer);
        if (navigationView != null) {
            setupDrawerContent(navigationView);

        }
        drawerToggle = setupDrawerToggle();
        drawerTitle = getResources().getString(R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);


    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, mtoolbar, R.string.drawer_open, R.string.drawer_close);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
        String activity = this.getClass().getSimpleName();

        switch (activity) {
            case "ListFilmActivity":
                setTitle(R.string.app_name); // Setear título actual
                break;
            case "PantallaAltaFilmActivity":
                setTitle("Añadir Film"); // Setear título actual
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        //menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();

                        selectItem(menuItem);
                        return true;
                    }
                }
        );

        String activity = this.getClass().getSimpleName();

        //Checked menu
        switch (activity) {
            case "ListFilmActivity":
                navigationView.getMenu().getItem(0).setChecked(true);

                break;
            case "PantallaAltaFilmActivity":
                navigationView.getMenu().getItem(1).setChecked(true);

                break;
        }

    }


    private void selectItem(MenuItem item) {
        // Enviar título como arguemento del fragmento
        Bundle args = new Bundle();
        //TODO
        //CAMBIA A OTRO ACTIVITY
        Intent intent = null;
        String activity = this.getClass().getSimpleName();
        switch (item.getItemId()) {
            case R.id.nav_home:
                if (!activity.equals("ListFilmActivity")) {

                    intent = new Intent(this, ListFilmActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                //finish();
                break;


            case R.id.nav_1:
                if (!activity.equals("PantallaAltaFilmActivity")) {

                    intent = new Intent(this, PantallaAltaFilmActivity.class);
                    startActivity(intent);
                }
                //finish();
                break;

        }

        drawerLayout.closeDrawers(); // Cerrar drawer


    }


    @Override //Need OVERRIDE
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_add_item:
                Intent intent = new Intent(this, PantallaAltaFilmActivity.class);
                startActivity(intent);

        }


        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

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

        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }




        return super.onCreateOptionsMenu(menu);
    }


}
