package com.orangesoft.handmadefood.main_fragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.orangesoft.handmadefood.abstraction.Restaurant;
import com.orangesoft.handmadefood.abstraction.User;
import com.orangesoft.handmadefood.net.RestService;
import com.orangesoft.handmadefood.one_object_fragment.OneCoocker;
import com.orangesoft.handmadefood.one_object_fragment.OneRecept;
import com.orangesoft.handmadefood.one_object_fragment.OneRestoran;

import static com.orangesoft.handmadefood.Constans.About;
import static com.orangesoft.handmadefood.Constans.About_me;
import static com.orangesoft.handmadefood.Constans.Address;
import static com.orangesoft.handmadefood.Constans.Avatar_medium;
import static com.orangesoft.handmadefood.Constans.Avatar_w220;
import static com.orangesoft.handmadefood.Constans.Contacts;
import static com.orangesoft.handmadefood.Constans.Content;
import static com.orangesoft.handmadefood.Constans.Cook_time;
import static com.orangesoft.handmadefood.Constans.Favorite;
import static com.orangesoft.handmadefood.Constans.First_name;
import static com.orangesoft.handmadefood.Constans.Food_categories;
import static com.orangesoft.handmadefood.Constans.General_image;
import static com.orangesoft.handmadefood.Constans.Id;
import static com.orangesoft.handmadefood.Constans.Image_big;
import static com.orangesoft.handmadefood.Constans.Image_square;
import static com.orangesoft.handmadefood.Constans.Ingredient_categories;
import static com.orangesoft.handmadefood.Constans.Ingredients;
import static com.orangesoft.handmadefood.Constans.Latitude;
import static com.orangesoft.handmadefood.Constans.Longitude;
import static com.orangesoft.handmadefood.Constans.Rss_title;
import static com.orangesoft.handmadefood.Constans.Servings_number;
import static com.orangesoft.handmadefood.Constans.Slug;
import static com.orangesoft.handmadefood.Constans.Surname;
import static com.orangesoft.handmadefood.Constans.TABLE_RECIPES_NAME;
import static com.orangesoft.handmadefood.Constans.TABLE_RESTORAN_NAME;
import static com.orangesoft.handmadefood.Constans.TABLE_USER_NAME;
import static com.orangesoft.handmadefood.Constans.Title;
import static com.orangesoft.handmadefood.Constans.Type;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SearchFragment extends Fragment implements
        ActionBar.OnNavigationListener  {

    private static final String LOG_TAG = "log";
    private String[] data = new String[]{"Все рецепты", "Все рестораны", "Все повара"};
    public DataBaseHandler dataBaseHandler;

    private FoodApplication application;
    private RestService restService;
    private int flag;

    private GridView gvMain;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataBaseHandler = new DataBaseHandler(getActivity().getApplicationContext());
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplication().getApplicationContext(),
                android.R.layout.simple_spinner_item, data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bar.setListNavigationCallbacks(adapter, this);
        flag=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.search);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        View v = inflater.inflate(R.layout.recepts_layout, null);
        return v;
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition) {
            case 0: {
                try {
                    serverLoader(0);
                }
                catch (Exception ex){}
                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                String FROM1 = "Id,Title,Rss_title, Content, Cook_time, Servings_number, Slug, General_image, Ingredients, Ingredient_categories, Food_categories"; //
                String query = "SELECT " + FROM1 + " FROM " + TABLE_RECIPES_NAME;
                Cursor cursor2 = db.rawQuery(query, null);
                ReceptFragment.allRecipes = new Recipe[cursor2.getCount()];
                int countElem = 0;
                while (cursor2.moveToNext()) {
                    ReceptFragment.allRecipes[countElem] = new Recipe();
                    ReceptFragment.allRecipes[countElem].id = cursor2.getInt(0);
                    ReceptFragment.allRecipes[countElem].title = cursor2.getString(1);
                    ReceptFragment.allRecipes[countElem].rss_title = cursor2.getString(2);
                    ReceptFragment.allRecipes[countElem].content = cursor2.getString(3);
                    ReceptFragment.allRecipes[countElem].cook_time = cursor2.getString(4);
                    ReceptFragment.allRecipes[countElem].servings_number = cursor2.getString(5);
                    ReceptFragment.allRecipes[countElem].slug = cursor2.getString(6);
                    ReceptFragment.allRecipes[countElem].general_image = cursor2.getString(7);

                    Gson gson = new Gson();
                    ReceptFragment.allRecipes[countElem].ingridient_categories = gson.fromJson(cursor2.getString(9), String[].class);
                    ReceptFragment.allRecipes[countElem].ingridients = gson.fromJson(cursor2.getString(8), String[].class);
                    ReceptFragment.allRecipes[countElem].food_categories = gson.fromJson(cursor2.getString(10), String[].class);

                    countElem++;
                }
                cursor2.close();
                flag=0;
                break;
            }
            case 1: {
                try {
                    serverLoader(1);
                }
                catch (Exception ex){}
                String FROM1 = "Id, Title, About, Address, Latitude, Longitude,  Slug, Contacts, Image_big, Image_square";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_RESTORAN_NAME;
                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                Cursor cursor = db.rawQuery(query, null);

                RestoranFragment.allRestorans = new Restaurant[cursor.getCount()];
                int countElem = 0;
                while (cursor.moveToNext()) {
                    RestoranFragment.allRestorans[countElem] = new Restaurant();
                    RestoranFragment.allRestorans[countElem].id = cursor.getInt(0);
                    RestoranFragment.allRestorans[countElem].title = cursor.getString(1);
                    RestoranFragment.allRestorans[countElem].about = cursor.getString(2);
                    RestoranFragment.allRestorans[countElem].address = cursor.getString(3);
                    RestoranFragment.allRestorans[countElem].latitude = cursor.getFloat(4);
                    RestoranFragment.allRestorans[countElem].longitude = cursor.getFloat(5);
                    RestoranFragment.allRestorans[countElem].slug = cursor.getString(6);
                    Gson gson1 = new Gson();
                    RestoranFragment.allRestorans[countElem].contacts = gson1.fromJson(cursor.getString(7), String[].class);
                    RestoranFragment.allRestorans[countElem].image_big = cursor.getString(8);
                    RestoranFragment.allRestorans[countElem].image_square = cursor.getString(9);
                    countElem++;
                }
                cursor.close();
                flag=1;
                break;
            }
            case 2: {
                try {
                    serverLoader(2);
                }
                catch (Exception ex){}
                String FROM1 = "Id, First_name, Surname, About_me, Type, Avatar_medium, Avatar_w220";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_USER_NAME;
                SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                Cursor cursor2 = db.rawQuery(query, null);
                CookerFragment.allAvtors = new User[cursor2.getCount()];
                int countElem = 0;
                while (cursor2.moveToNext()) {
                    CookerFragment.allAvtors[countElem] = new User();
                    CookerFragment.allAvtors[countElem].id = cursor2.getInt(0);
                    CookerFragment.allAvtors[countElem].first_name = cursor2.getString(1);
                    CookerFragment.allAvtors[countElem].surname = cursor2.getString(2);
                    CookerFragment.allAvtors[countElem].about_me = cursor2.getString(3);
                    CookerFragment.allAvtors[countElem].type = cursor2.getString(4);
                    CookerFragment.allAvtors[countElem].avatar_medium = cursor2.getString(5);
                    CookerFragment.allAvtors[countElem].avatar_w220 = cursor2.getString(6);
                    countElem++;
                }
                cursor2.close();
                flag=2;
                break;
            }}
            return false;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem =  menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(LOG_TAG, s);

                hideKeyboard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length()>0){
                s = s.substring(0, 1).toUpperCase() + s.substring(1);
                gvMain = (GridView) getView().findViewById(R.id.gridView);
                gvMain.setVisibility(View.VISIBLE);

                switch (flag){
                    case 0:{
                        String FROM1 = "Id ,Title,Rss_title, Content, Cook_time, Servings_number, Slug, General_image, Ingredients, Ingredient_categories, Food_categories";
                        String query = "SELECT " + FROM1 + " FROM " + TABLE_RECIPES_NAME +" WHERE "+ Title +" LIKE '" +s+"%' " ;
                        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                        Cursor cursor2 = db.rawQuery(query, null);


                        final Recipe[] searchRecipes = new Recipe[cursor2.getCount()];
                        int countElem = 0;
                        while (cursor2.moveToNext()) {
                            searchRecipes[countElem] = new Recipe();
                            searchRecipes[countElem].id = cursor2.getInt(0);
                            searchRecipes[countElem].title = cursor2.getString(1);
                            searchRecipes[countElem].rss_title = cursor2.getString(2);
                            searchRecipes[countElem].content = cursor2.getString(3);
                            searchRecipes[countElem].cook_time = cursor2.getString(4);
                            searchRecipes[countElem].servings_number = cursor2.getString(5);
                            searchRecipes[countElem].slug = cursor2.getString(6);
                            searchRecipes[countElem].general_image = cursor2.getString(7);
                            Gson gson = new Gson();
                            searchRecipes[countElem].ingridient_categories = gson.fromJson(cursor2.getString(9), String[].class);
                            searchRecipes[countElem].ingridients = gson.fromJson(cursor2.getString(8), String[].class);
                            searchRecipes[countElem].food_categories = gson.fromJson(cursor2.getString(10), String[].class);

                            countElem++;
                        }
                        cursor2.close();


                        ArrayAdapter<Recipe> gridadapter = new ArrayAdapter<Recipe>(getActivity(), R.layout.grid_item, R.id.grid_text,searchRecipes ){

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                View view = super.getView(position, convertView, parent);
                                TextView textView = (TextView) view.findViewById(R.id.grid_text);
                                textView.setText(searchRecipes[position].title);

                                ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
                                ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ searchRecipes[position].general_image ,grid_picture);
                                return view;
                            }
                        };
                        gvMain.setAdapter(gridadapter);
                        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                OneRecept oneRecept = new OneRecept();
                                Bundle bundle = new Bundle();
                                bundle.putInt("currentPosition", searchRecipes[position].id-1);
                                oneRecept.setArguments(bundle);
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.conteiner, oneRecept);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
                        break;
                    }
                    case 1:{

                        String FROM1 = "Id, Title, About, Address, Latitude, Longitude,  Slug, Contacts, Image_big, Image_square";
                        String query = "SELECT " + FROM1 + " FROM " + TABLE_RESTORAN_NAME +" WHERE "+ Title +" LIKE '" +s+"%' ";
                        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                        Cursor cursor = db.rawQuery(query, null);

                        final Restaurant[] searchRestorans = new Restaurant[cursor.getCount()];
                        int countElem = 0;
                        while (cursor.moveToNext()) {
                            searchRestorans[countElem] = new Restaurant();
                            searchRestorans[countElem].id = cursor.getInt(0);
                            searchRestorans[countElem].title = cursor.getString(1);
                            searchRestorans[countElem].about = cursor.getString(2);
                            searchRestorans[countElem].address = cursor.getString(3);
                            searchRestorans[countElem].latitude = cursor.getFloat(4);
                            searchRestorans[countElem].longitude = cursor.getFloat(5);
                            searchRestorans[countElem].slug = cursor.getString(6);
                            Gson gson1 = new Gson();
                            searchRestorans[countElem].contacts = gson1.fromJson(cursor.getString(7), String[].class);
                            searchRestorans[countElem].image_big = cursor.getString(8);
                            searchRestorans[countElem].image_square = cursor.getString(9);
                            countElem++;
                        }
                        cursor.close();
                        ArrayAdapter<Restaurant> gridadapter = new ArrayAdapter<Restaurant>(getActivity(), R.layout.grid_item, R.id.grid_text, searchRestorans){

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                View view = super.getView(position, convertView, parent);
                                TextView textView = (TextView) view.findViewById(R.id.grid_text);
                                textView.setText(searchRestorans[position].title);
                                ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
                                ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ searchRestorans[position].image_square ,grid_picture);
                                return view;
                            }
                        };
                        gvMain.setAdapter(gridadapter);
                        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                OneRestoran oneRestoran = new OneRestoran();
                                Bundle bundle = new Bundle();
                                bundle.putInt("currentPositionRestoran", searchRestorans[position].id-1);
                                oneRestoran.setArguments(bundle);
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.replace(R.id.conteiner, oneRestoran);
                                fragmentTransaction.commit();
                            }
                        });
                        break;
                    }
                    case 2:{

                        String FROM1 = "Id, First_name, Surname, About_me, Type, Avatar_medium, Avatar_w220";
                        String query = "SELECT " + FROM1 + " FROM " + TABLE_USER_NAME +" WHERE "+ First_name +" LIKE '"+s+"%' OR " + Surname+" LIKE '"+s+"%' " ;
                        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                        Cursor cursor2 = db.rawQuery(query, null);
                        final User[] searchAvtors = new User[cursor2.getCount()];
                        int countElem = 0;
                        while (cursor2.moveToNext()) {
                            searchAvtors[countElem] = new User();
                            searchAvtors[countElem].id = cursor2.getInt(0);
                            searchAvtors[countElem].first_name = cursor2.getString(1);
                            searchAvtors[countElem].surname = cursor2.getString(2);
                            searchAvtors[countElem].about_me = cursor2.getString(3);
                            searchAvtors[countElem].type = cursor2.getString(4);
                            searchAvtors[countElem].avatar_medium = cursor2.getString(5);
                            searchAvtors[countElem].avatar_w220 = cursor2.getString(6);
                            countElem++;
                        }
                        cursor2.close();

                        ArrayAdapter<User> gridadapter = new ArrayAdapter<User>(getActivity(), R.layout.grid_item, R.id.grid_text,searchAvtors ){

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                View view = super.getView(position, convertView, parent);
                                TextView textView = (TextView) view.findViewById(R.id.grid_text);
                                textView.setText(searchAvtors[position].first_name+" "+ searchAvtors[position].surname);

                                ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
                                ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ searchAvtors[position].avatar_w220 ,grid_picture);
                                return view;
                            }
                        };
                        gvMain.setAdapter(gridadapter);
                        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                OneCoocker oneCoocker = new OneCoocker();
                                Bundle bundle = new Bundle();
                                bundle.putInt("currentPositionAvtor", searchAvtors[position].id-1);
                                oneCoocker.setArguments(bundle);
                                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.conteiner, oneCoocker);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        });
                        break;}
                    }
                }
                else {
                    gvMain = (GridView) getView().findViewById(R.id.gridView);
                    gvMain.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });


    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().findViewById(android.R.id.content).getWindowToken(), 0);
    }

    private void serverLoader(final int flag){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                application = (FoodApplication) getActivity().getApplication();
                restService = application.getRestService();
               switch (flag){
                   case 0:{
                       Recipe[] recipes = restService.getRecipe();
                       addEvent(recipes);
                       break;
                   }

                   case 1:{
                       Restaurant[] restaurants = restService.getRestoran();
                       addEvent(restaurants);
                       break;

                   }
                   case 2:{
                       User[] avtors = restService.getAvtors();
                       addEvent(avtors);
                       break;
                   }}
                return null;
            }

            @Override
            protected void onPostExecute(String content) {
                try {
       //             onNavigationItemSelected(getActivity().getActionBar().getSelectedNavigationIndex(), getActivity().getActionBar().getSelectedNavigationIndex());
                }
                catch (Exception ex){
                }
            }
        }.execute();
    }


    public void addEvent(User[] getusers) {
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        db.execSQL("CREATE TABLE " + TABLE_USER_NAME + " (" + Id
                + " INTEGER, " + First_name + " TEXT,"
                + Surname + " TEXT," + About_me + " TEXT,"
                + Type + " TEXT," + Avatar_medium+ " TEXT,"
                + Avatar_w220 +" TEXT);" );

        for (int i=0; i<getusers.length;i++){
            ContentValues values = new ContentValues();
            values.put(Id, getusers[i].id);
            values.put(First_name, getusers[i].first_name );
            values.put(Surname, getusers[i].surname );
            values.put( About_me, getusers[i].about_me);
            values.put(Type, getusers[i].type);
            values.put(Avatar_medium,getusers[i].avatar_medium );
            values.put(Avatar_w220, getusers[i].avatar_w220 );
            db.insertOrThrow(TABLE_USER_NAME, null, values);
        }
    }

    public void addEvent(Restaurant[] getrestoran) {
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTORAN_NAME);
        db.execSQL("CREATE TABLE " + TABLE_RESTORAN_NAME + " (" + Id
                + " INTEGER, " + Title + " TEXT NOT NULL,"
                + About + " TEXT," + Address + " TEXT,"
                + Slug+ " TEXT,"
                + Latitude+ " TEXT," +Longitude+ " TEXT,"
                + Contacts+" TEXT," + Image_big + " TEXT," + Image_square+ /*" TEXT ,"
                + Categories+ " TEXT," +Images +*/" TEXT);" );
        Gson gson = new Gson();

        for (int i=0; i<getrestoran.length;i++){
            ContentValues values = new ContentValues();
            values.put(Id, getrestoran[i].id);
            values.put(Title, getrestoran[i].title );
            values.put(About,getrestoran[i].about );
            values.put(Address, getrestoran[i].address );
            values.put( Latitude, getrestoran[i].latitude);
            values.put(Longitude, getrestoran[i].longitude);
            values.put(Slug,getrestoran[i].slug );
            String contacts = gson.toJson(getrestoran[i].contacts, String[].class);
            values.put(Contacts, contacts);
            values.put(Image_big,getrestoran[i].image_big );
            values.put(Image_square, getrestoran[i].image_square );

            db.insertOrThrow(TABLE_RESTORAN_NAME, null, values);
        }
    }

    public void addEvent(Recipe[] getrecipes) {
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES_NAME);
        db.execSQL("CREATE TABLE " + TABLE_RECIPES_NAME + " (" + Id
                + " INTEGER, " +Favorite +" INTEGER, " + Title + " TEXT NOT NULL,"+ Rss_title + " TEXT,"
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


}
