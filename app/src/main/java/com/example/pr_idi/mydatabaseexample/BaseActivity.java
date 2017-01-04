package com.example.pr_idi.mydatabaseexample;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
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
import android.app.ActionBar;

import android.support.v4.widget.DrawerLayout;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


import static java.lang.String.*;


public class BaseActivity extends AppCompatActivity {

    protected Toolbar mtoolbar;
    protected FrameLayout content;
    protected ActionBarDrawerToggle drawerToggle;
    protected ActionBarDrawerToggle mToggle;

    private DrawerLayout drawerLayout;
    private String drawerTitle;


    protected void onCreateDraweron(final int layoutResID) {
        setContentView(R.layout.activity_drawer);
        //Contenido hacia para la vista principal
        //layoutResID: R.layout.{LAYOUT_ACTIVITY_CONTENT}
        content = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, content, true);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.left_drawer);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.app_name);



        drawerToggle = new ActionBarDrawerToggle(
                this,                  // host Activity
                drawerLayout,         // DrawerLayout object
              //  R.drawable.ic_action_image_dehaze,  // nav drawer icon to replace 'Up' caret
                R.string.drawer_open,  // "open drawer" description
                R.string.drawer_close  // "close drawer" description
        ) {

            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
               // getActionBar().setTitle(mTitle);
            }

            // Called when a drawer has settled in a completely open state. /
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

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

            case "ListTitleActivity":
                setTitle(R.string.ListTitleAct_TITLE);
                break;
            case "ListFilmActivity":
                setTitle(R.string.ListFilmAct_TITLE);
                break;
            case "PantallaAltaFilmActivity":
                setTitle(R.string.PantallaAltaAct_TITLE);
                break;
            case "ListTitlesSearchActivity":
                setTitle(R.string.ListTitleSearchAct_TITLE);
                break;
            case "ListCalificarActivity":
                setTitle(R.string.ListCalificarAct_TITLE);
                break;
            default:  setTitle(R.string.app_name);
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
                       // String title = menuItem.getTitle().toString();

                        selectItem(menuItem);
                        return true;
                    }
                }
        );
        String activity = this.getClass().getSimpleName();
        //Checked menu
        switch (activity) {
            case "ListTitleActivity":
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "ListFilmActivity":
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "PantallaAltaFilmActivity":
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
            case "ListTitlesSearchActivity":
                navigationView.getMenu().getItem(4).setChecked(true);
                break;
            case "ListCalificarActivity":
                navigationView.getMenu().getItem(5).setChecked(true);
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
            case R.id.nav_home: //Titulos
                if (!activity.equals("ListTitleActivity")) {
                    intent = new Intent(this, ListTitleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                //finish();
                break;


            case R.id.nav_1: // Todos los Datos
                if (!activity.equals("ListFilmActivity")) {
                    intent = new Intent(this, ListFilmActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.nav_2: //Añadir
                if (!activity.equals("PantallaAltaFilmActivity")) {
                    intent = new Intent(this, PantallaAltaFilmActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.nav_3: //Borrar
               /* if (!activity.equals("EraserActivity")) {
                    intent = new Intent(this, EraserActivity.class);
                    startActivity(intent);
                }*/
                break;

            case R.id.nav_4: //Buscar

                    intent = new Intent(getApplicationContext(), ListTitlesSearchActivity.class);
                    intent.putExtra("from", "navigationdrawer" );
                    startActivity(intent);
                break;

            case R.id.nav_8: //Calificar
                if (!activity.equals("ListCalificarActivity")) {
                    intent = new Intent(this, ListCalificarActivity.class);
                    startActivity(intent);
                }
                break;


            case R.id.nav_exit:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Esta a punto de salir de la aplicación")
                        .setTitle("Atención");
                alertDialogBuilder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                });
                alertDialogBuilder.setNegativeButton("Quedarme", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();
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
                return true;

            case R.id.action_about:
                intent = new Intent(this, About.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        /*Search code  2
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        //searchView.setQueryRefinementEnabled(true);
        searchView.setSearchableInfo(searchableInfo);

        */
        return super.onCreateOptionsMenu(menu);
    }


}
