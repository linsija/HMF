package com.orangesoft.handmadefood.main_fragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.DataBaseHandler;
import com.orangesoft.handmadefood.FoodApplication;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.abstraction.Recipe;
import com.orangesoft.handmadefood.net.RestService;
import com.orangesoft.handmadefood.one_object_fragment.OneRecept;

import static com.orangesoft.handmadefood.Constans.Content;
import static com.orangesoft.handmadefood.Constans.Cook_time;
import static com.orangesoft.handmadefood.Constans.Food_categories;
import static com.orangesoft.handmadefood.Constans.General_image;
import static com.orangesoft.handmadefood.Constans.Id;
import static com.orangesoft.handmadefood.Constans.Ingredient_categories;
import static com.orangesoft.handmadefood.Constans.Ingredients;
import static com.orangesoft.handmadefood.Constans.Rss_title;
import static com.orangesoft.handmadefood.Constans.Servings_number;
import static com.orangesoft.handmadefood.Constans.Slug;
import static com.orangesoft.handmadefood.Constans.TABLE_RECIPES_NAME;
import static com.orangesoft.handmadefood.Constans.Title;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ReceptFragment extends Fragment implements
        ActionBar.OnNavigationListener, ActionBar.TabListener{

    private  GridView gvMain;
    private  ArrayAdapter<Recipe> gridadapter;
    private FilterFragment filterFragment;

    private FoodApplication application;
    private RestService restService;

    public static Recipe[] allRecipes;
    public DataBaseHandler dataBaseHandler;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataBaseHandler = new DataBaseHandler(getActivity().getApplicationContext());
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        try {
            serverLoader();
        }
        catch (Exception ex){

        }

    }

    private void serverLoader(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                application = (FoodApplication) getActivity().getApplication();
                restService = application.getRestService();
                Recipe[] recipes = restService.getRecipe();
                addEvent(recipes);
                return null;
            }
            @Override
            protected void onPostExecute(String content) {
               try {
                   onStart();
               }
               catch (Exception ex){
               }
            }
        }.execute();
    }
    @Override
    public void onStart() {
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        String FROM1 = "Id ,Title,Rss_title, Content, Cook_time, Servings_number, Slug, General_image, Ingredients, Ingredient_categories, Food_categories"; //
        String query = "SELECT " + FROM1 + " FROM " + TABLE_RECIPES_NAME;
        Cursor cursor2 = db.rawQuery(query, null);
        super.onStart();
        allRecipes = new Recipe[cursor2.getCount()];
        int countElem = 0;
        while (cursor2.moveToNext()) {
            allRecipes[countElem] = new Recipe();
            allRecipes[countElem].id = cursor2.getInt(0);
            allRecipes[countElem].title = cursor2.getString(1);
            allRecipes[countElem].rss_title = cursor2.getString(2);
            allRecipes[countElem].content = cursor2.getString(3);
            allRecipes[countElem].cook_time = cursor2.getString(4);
            allRecipes[countElem].servings_number = cursor2.getString(5);
            allRecipes[countElem].slug = cursor2.getString(6);
            allRecipes[countElem].general_image = cursor2.getString(7);
            Gson gson = new Gson();
            allRecipes[countElem].ingridient_categories = gson.fromJson(cursor2.getString(9), String[].class);
            allRecipes[countElem].ingridients = gson.fromJson(cursor2.getString(8), String[].class);
            allRecipes[countElem].food_categories = gson.fromJson(cursor2.getString(10), String[].class);

            countElem++;
        }
        cursor2.close();
        gvMain = (GridView) getView().findViewById(R.id.gridView);

        gridadapter = new ArrayAdapter<Recipe>(getActivity(), R.layout.grid_item, R.id.grid_text,allRecipes ){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.grid_text);
                textView.setText(allRecipes[position].title);

                ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
                ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ allRecipes[position].general_image ,grid_picture);
                return view;
            }
        };
        gvMain.setAdapter(gridadapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OneRecept oneRecept = new OneRecept();
                Bundle bundle = new Bundle();
                bundle.putInt("currentPosition", allRecipes[position].id);
                oneRecept.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, oneRecept);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.recepts);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        View v = inflater.inflate(R.layout.recepts_layout, null);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_recepts, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret;
        if (item.getItemId() == R.id.itm) {
            ret = true;
            filterFragment = new FilterFragment();

            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.conteiner, filterFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            ret = super.onOptionsItemSelected(item);
        }
        return ret;
    }

    public void addEvent(Recipe[] getrecipes) {
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES_NAME);
        db.execSQL("CREATE TABLE " + TABLE_RECIPES_NAME + " (" + Id
                + " INTEGER, " + Title + " TEXT NOT NULL,"+ Rss_title + " TEXT,"
                + Content + " TEXT," + Cook_time + " TEXT,"
                + Servings_number + " TEXT,"+ Slug+ " TEXT,"
                + General_image+ " TEXT," +Ingredients+ " TEXT,"
                + Ingredient_categories+" TEXT,"+ Food_categories+ " TEXT);" );
        Gson gson = new Gson();

        for (int i=0; i<getrecipes.length;i++){
            ContentValues values = new ContentValues();
            values.put(Id, getrecipes[i].id);
            values.put(Title, getrecipes[i].title );
            values.put(Rss_title, getrecipes[i].rss_title );
            values.put( Content, getrecipes[i].content);
            values.put(Cook_time, getrecipes[i].cook_time);
            values.put(Servings_number,getrecipes[i].servings_number );
            values.put(Slug,getrecipes[i].slug );
            values.put(General_image, getrecipes[i].general_image );

            String ingridients = gson.toJson(getrecipes[i].ingridients, String[].class);
            values.put(Ingredients, ingridients);

            String categories = gson.toJson(getrecipes[i].ingridient_categories, String[].class);
            values.put(Ingredient_categories, categories);

            String foodcategories = gson.toJson(getrecipes[i].food_categories, String[].class);
            values.put(Food_categories, foodcategories);

            db.insertOrThrow(TABLE_RECIPES_NAME, null, values);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {

    }
}
