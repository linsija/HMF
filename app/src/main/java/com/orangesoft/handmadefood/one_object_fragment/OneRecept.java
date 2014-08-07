package com.orangesoft.handmadefood.one_object_fragment;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.DataBaseHandler;
import com.orangesoft.handmadefood.FavoriteRecipe;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.abstraction.Recipe;
import com.orangesoft.handmadefood.main_fragments.ReceptFragment;

import static com.orangesoft.handmadefood.Constans.Count;
import static com.orangesoft.handmadefood.Constans.TABLE_FAVORITE_NAME;
import static com.orangesoft.handmadefood.Constans.Title;

/**
 * Created by Виолетта on 22.07.2014.
 */
public class OneRecept extends Fragment {


    TextView textViewName;
    TextView countPortion;
    TextView countTime;
    int numberItemRecept;
    ImageView receptPicture;
    TextView category;
    TextView generalIngridient;
    TextView receptText;
    ListView ingridientsList;
    Menu menu;
    public DataBaseHandler dataBaseHandler;
    Recipe needRecept;
    FavoriteRecipe[] favoriteRecipes;
    SQLiteDatabase db;
    boolean flag;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        setHasOptionsMenu(true);
        dataBaseHandler = new DataBaseHandler(getActivity().getApplicationContext());
        numberItemRecept = bundle.getInt("currentPosition");
        db = dataBaseHandler.getReadableDatabase();
        String FROM1 = "Id, Count, Title";
        String query = "SELECT " + FROM1 + " FROM " + TABLE_FAVORITE_NAME;
        Cursor cursor2 = db.rawQuery(query, null);
        favoriteRecipes = new FavoriteRecipe[cursor2.getCount()];
        int countElem = 0;
        while (cursor2.moveToNext()) {
            favoriteRecipes[countElem] = new FavoriteRecipe();
            favoriteRecipes[countElem].count = cursor2.getInt(1);
            favoriteRecipes[countElem].title = cursor2.getString(2);
            countElem++;
        }
        needRecept = new Recipe();

        for (int i = 0; i < ReceptFragment.allRecipes.length; i++) {
            if (ReceptFragment.allRecipes[i].id == numberItemRecept) {
                needRecept = ReceptFragment.allRecipes[i];
                break;
            }
        }


   /*     TabAdapter = new TabPagerAdapter(getFragmentManager());
        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);                    }
                });
        Tab.setAdapter(TabAdapter);
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener(){


            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                Tab.setCurrentItem(tab.getPosition());
            }
        };
        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Android").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("iOS").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Windows").setTabListener(tabListener));
*/

    }


    @Override
    public void onStart() {
        super.onStart();
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.one_recept, null);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.recept);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);


        final TabHost tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;
        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Описание");
        tabSpec.setContent(R.id.description_layout_recept);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Ингридиенты");
        tabSpec.setContent(R.id.ingridients_layout);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Приготовление");
        tabSpec.setContent(R.id.making_layout);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");

        TextView x = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x.setTextSize(10);
        TextView x1 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        x1.setTextSize(10);
        TextView x2 = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title);
        x2.setTextSize(10);


        textViewName = (TextView) v.findViewById(R.id.textView);
        countPortion = (TextView) v.findViewById(R.id.count_portion);
        countTime = (TextView) v.findViewById(R.id.count_time);
        receptPicture = (ImageView) v.findViewById(R.id.recept_picture);
        generalIngridient = (TextView) v.findViewById(R.id.general_ingridient);
        category = (TextView) v.findViewById(R.id.category);
        receptText = (TextView) v.findViewById(R.id.make_text);
        ingridientsList = (ListView) v.findViewById(R.id.ingridient_list);


        textViewName.setText(needRecept.title);
        countPortion.setText(needRecept.servings_number + " порций");
        countTime.setText(needRecept.cook_time + " минут");
        ImageLoader.getInstance().displayImage("http://handmadefood.ru/" + needRecept.general_image, receptPicture);
//        generalIngridient.setText(needRecept.ingridient_categories[0]);
//        contains.setText(ReceptFragment.allRecipes[numberItemRecept].ingridients.toString());
        category.setText(needRecept.food_categories[0]);
        receptText.setText(Html.fromHtml(needRecept.content));
  /*      String[] text = new String[needRecept.ingridients.length];
        for (int i = 0; i < needRecept.ingridients.length; i++) {
            text[i] = needRecept.ingridients[i];
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_view_text, R.id.text1, text) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                return view;
            }
        };

        ingridientsList.setAdapter(adapter); */
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.one_recept, menu);
        this.menu = menu;
        flag = false;
        MenuItem star = menu.findItem(R.id.favorites);
        for (int i = 0; i < favoriteRecipes.length; i++) {
            if (textViewName.getText().equals(favoriteRecipes[i].title)) {
                star.setIcon(R.drawable.starfull);
                flag = true;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret;
        if (item.getItemId() == R.id.favorites) {
            MenuItem star = menu.findItem(R.id.favorites);
            if (flag) {
                star.setIcon(R.drawable.star);
                db.execSQL("DELETE FROM " + TABLE_FAVORITE_NAME + " WHERE " + Count + " = " + numberItemRecept);
                flag = false;
            } else {
                star.setIcon(R.drawable.starfull);
                flag = true;
                ContentValues values = new ContentValues();
                values.put(Count, needRecept.id);
                values.put(Title, needRecept.title);
                db.insertOrThrow(TABLE_FAVORITE_NAME, null, values);
            }

            ret = true;

        } else if (item.getItemId() == R.id.send) {
            sendData();
            ret = true;
        } else
            ret = super.onOptionsItemSelected(item);

        return ret;
    }

    private void sendData() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, needRecept.title);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://handmadefood.ru//" + needRecept.general_image + ".jpg"));
        shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(needRecept.content).toString());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.hand_made_food)));
    }


}
