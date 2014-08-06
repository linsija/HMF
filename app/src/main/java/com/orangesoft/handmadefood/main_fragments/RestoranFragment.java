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
import android.util.Log;
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
import com.orangesoft.handmadefood.abstraction.Restaurant;
import com.orangesoft.handmadefood.net.RestService;
import com.orangesoft.handmadefood.one_object_fragment.OneRestoran;

import static com.orangesoft.handmadefood.Constans.About;
import static com.orangesoft.handmadefood.Constans.Address;
import static com.orangesoft.handmadefood.Constans.Contacts;
import static com.orangesoft.handmadefood.Constans.Id;
import static com.orangesoft.handmadefood.Constans.Image_big;
import static com.orangesoft.handmadefood.Constans.Image_square;
import static com.orangesoft.handmadefood.Constans.Latitude;
import static com.orangesoft.handmadefood.Constans.Longitude;
import static com.orangesoft.handmadefood.Constans.Slug;
import static com.orangesoft.handmadefood.Constans.TABLE_RESTORAN_NAME;
import static com.orangesoft.handmadefood.Constans.Title;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RestoranFragment extends Fragment implements
        ActionBar.OnNavigationListener  {

    private static final String LOG_TAG = "log";
    public static int number_item_restoran;
    public DataBaseHandler dataBaseHandler;
    private GridView gvMain;
    private  ArrayAdapter<Restaurant> gridadapter;
    private  OneRestoran oneRestoran;
    public static Restaurant[] allRestorans ;

    private FoodApplication application;
    private RestService restService;

    private String[] data = new String[] { "Все рестораны", "Москва", "Петербург"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        number_item_restoran =0;
        dataBaseHandler = new DataBaseHandler(getActivity().getApplicationContext());

        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplication().getApplicationContext(),
                android.R.layout.simple_spinner_item, data);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bar.setListNavigationCallbacks(adapter, this);

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
            Restaurant[] restaurants = restService.getRestoran();
            addEvent(restaurants);
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
        super.onStart();
        getActivity().getActionBar().setSelectedNavigationItem(0);
        String FROM1 = "Id, Title, About, Address, Latitude, Longitude,  Slug, Contacts, Image_big, Image_square";
        String query = "SELECT " + FROM1 + " FROM " + TABLE_RESTORAN_NAME;
        setDataInDB(query);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.restorans);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        View v = inflater.inflate(R.layout.recepts_layout,null);
        return v;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Log.d(LOG_TAG, "selected: position = " + itemPosition + ", id = "
                + itemId + ", " + data[itemPosition]);

        switch (itemPosition){
            case 0:

            {
                String FROM1 = "Id, Title, About, Address, Latitude, Longitude,  Slug, Contacts, Image_big, Image_square";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_RESTORAN_NAME;
                setDataInDB(query);
                break;
            }
            case 1:{

                String FROM1 = "Id, Title, About, Address, Latitude, Longitude,  Slug, Contacts, Image_big, Image_square";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_RESTORAN_NAME +" WHERE "+ Address +" LIKE 'г.М%' " ;
                setDataInDB(query);
                break;}
            case 2:{
                String FROM1 = "Id, Title, About, Address, Latitude, Longitude,  Slug, Contacts, Image_big, Image_square";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_RESTORAN_NAME +" WHERE "+ Address +" LIKE 'г.С%' " ;


                setDataInDB(query);
                break;}
        }
        return false;
    }

public void setDataInDB(String query){
    SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    allRestorans = new Restaurant[cursor.getCount()];
    int countElem = 0;
    while (cursor.moveToNext()) {
        allRestorans[countElem] = new Restaurant();
        allRestorans[countElem].id = cursor.getInt(0);
        allRestorans[countElem].title = cursor.getString(1);
        allRestorans[countElem].about = cursor.getString(2);
        allRestorans[countElem].address = cursor.getString(3);
        allRestorans[countElem].latitude = cursor.getFloat(4);
        allRestorans[countElem].longitude = cursor.getFloat(5);
        allRestorans[countElem].slug = cursor.getString(6);
        Gson gson1 = new Gson();
        allRestorans[countElem].contacts = gson1.fromJson(cursor.getString(7), String[].class);
        allRestorans[countElem].image_big = cursor.getString(8);
        allRestorans[countElem].image_square = cursor.getString(9);
        countElem++;
    }
    cursor.close();
    gvMain = (GridView) getView().findViewById(R.id.gridView);
    gridadapter = new ArrayAdapter<Restaurant>(getActivity(), R.layout.grid_item, R.id.grid_text, allRestorans){

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);
            TextView textView = (TextView) view.findViewById(R.id.grid_text);
            textView.setText(allRestorans[position].title);
            ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
            ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ allRestorans[position].image_square ,grid_picture);
            return view;
        }
    };
    gvMain.setAdapter(gridadapter);
    gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            oneRestoran = new OneRestoran();
            Bundle bundle = new Bundle();
            bundle.putInt("currentPositionRestoran", position);
            oneRestoran.setArguments(bundle);
            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.conteiner, oneRestoran);
            fragmentTransaction.commit();
        }
    });
}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
  //     inflater.inflate(R.menu.menu_restorans, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret;
        if (item.getItemId() == R.id.item1) {
            ret = true;
        }
        else
          if (item.getItemId() == R.id.item2) {
              ret = true;
          }
          else
            if (item.getItemId() == R.id.item3) {
                ret = true;
            }
            else{
                ret = super.onOptionsItemSelected(item);
            }

        return ret;
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
}