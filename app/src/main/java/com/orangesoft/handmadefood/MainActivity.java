package com.orangesoft.handmadefood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orangesoft.handmadefood.main_fragments.CookerFragment;
import com.orangesoft.handmadefood.main_fragments.FavoriteFragment;
import com.orangesoft.handmadefood.main_fragments.ReceptFragment;
import com.orangesoft.handmadefood.main_fragments.RestoranFragment;
import com.orangesoft.handmadefood.main_fragments.SearchFragment;

public class MainActivity extends ActionBarActivity implements Navigations {

    ImageButton recepts;
    ImageButton restorans;
    ImageButton coockers;
    ImageButton search;
    ImageButton favorites;


    LinearLayout  recept_layout;
    LinearLayout  restorans_layout;
    LinearLayout  coockers_layout;
    LinearLayout  search_layout;
    LinearLayout  favorites_layout;


    Fragment receptFragment;
    RestoranFragment restoranFragment;
    CookerFragment cookerFragment;
    SearchFragment searchFragment;
    FavoriteFragment favoriteFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

         getSupportActionBar().setTitle("");
         getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         getSupportActionBar().setDisplayShowHomeEnabled(false);


        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.15f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);

        recepts = (ImageButton) findViewById(R.id.recepts);
        restorans = (ImageButton) findViewById(R.id.restorans);
        coockers = (ImageButton) findViewById(R.id.coockers);
        search = (ImageButton) findViewById(R.id.search);
        favorites = (ImageButton) findViewById(R.id.favorites);

        recept_layout = (LinearLayout) findViewById(R.id.recept_layout);
        restorans_layout = (LinearLayout) findViewById(R.id.restorans_layout);
        coockers_layout = (LinearLayout) findViewById(R.id.coockers_layout);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        favorites_layout = (LinearLayout) findViewById(R.id.favorites_layout);

        receptFragment = new ReceptFragment();
        restoranFragment = new RestoranFragment();
        cookerFragment = new CookerFragment();
        searchFragment = new SearchFragment();
        favoriteFragment = new FavoriteFragment();

        if (savedInstanceState == null) {
            changeFragment(receptFragment, false);
        }


        recept_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, receptFragment);
             //   fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                recepts.setBackgroundResource(R.drawable.recepts1);
                restorans.setBackgroundResource(R.drawable.restorans);
                coockers.setBackgroundResource(R.drawable.coockers);
                search.setBackgroundResource(R.drawable.search);
                favorites.setBackgroundResource(R.drawable.favorites);

            }
        });
        restorans_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, restoranFragment);
               // fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                recepts.setBackgroundResource(R.drawable.recepts);
                restorans.setBackgroundResource(R.drawable.restorans1);
                coockers.setBackgroundResource(R.drawable.coockers);
                search.setBackgroundResource(R.drawable.search);
                favorites.setBackgroundResource(R.drawable.favorites);
            }
        });
        coockers_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, cookerFragment);
             //   fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                recepts.setBackgroundResource(R.drawable.recepts);
                restorans.setBackgroundResource(R.drawable.restorans);
                coockers.setBackgroundResource(R.drawable.coockers1);
                search.setBackgroundResource(R.drawable.search);
                favorites.setBackgroundResource(R.drawable.favorites);
            }
        });
        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, searchFragment);
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                recepts.setBackgroundResource(R.drawable.recepts);
                restorans.setBackgroundResource(R.drawable.restorans);
                coockers.setBackgroundResource(R.drawable.coockers);
                search.setBackgroundResource(R.drawable.search1);
                favorites.setBackgroundResource(R.drawable.favorites);
            }
        });
        favorites_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, favoriteFragment);
               // fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                recepts.setBackgroundResource(R.drawable.recepts);
                restorans.setBackgroundResource(R.drawable.restorans);
                coockers.setBackgroundResource(R.drawable.coockers);
                search.setBackgroundResource(R.drawable.search);
                favorites.setBackgroundResource(R.drawable.favorites1);
            }
        });


    }


    @Override
    public void changeFragment(Fragment fragment) {
        changeFragment(fragment, true);
    }

    @Override
    public void back() {
        onBackPressed();
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.conteiner, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }



}
