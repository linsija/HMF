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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.DataBaseHandler;
import com.orangesoft.handmadefood.FoodApplication;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.abstraction.User;
import com.orangesoft.handmadefood.net.RestService;
import com.orangesoft.handmadefood.one_object_fragment.OneCoocker;

import static com.orangesoft.handmadefood.Constans.About_me;
import static com.orangesoft.handmadefood.Constans.Avatar_medium;
import static com.orangesoft.handmadefood.Constans.Avatar_w220;
import static com.orangesoft.handmadefood.Constans.First_name;
import static com.orangesoft.handmadefood.Constans.Id;
import static com.orangesoft.handmadefood.Constans.Surname;
import static com.orangesoft.handmadefood.Constans.TABLE_USER_NAME;
import static com.orangesoft.handmadefood.Constans.Type;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CookerFragment  extends Fragment  implements
        ActionBar.OnNavigationListener  {

    private GridView gvMain;
    private ArrayAdapter<User> gridadapter;

    public static User[] allAvtors;
    public DataBaseHandler dataBaseHandler;
    public static int number_item_avtor;

    private FoodApplication application;
    private RestService restService;

    private String[] data = new String[] { "Все повара", "Шеф-повара", "Кулинары"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        number_item_avtor =0;
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
                User[] avtors = restService.getAvtors();
                addEvent(avtors);
                return null;
            }
            @Override
            protected void onPostExecute(String content) { try {
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
        String FROM1 = "Id, First_name, Surname, About_me, Type, Avatar_medium, Avatar_w220";
        String query = "SELECT " + FROM1 + " FROM " + TABLE_USER_NAME;
        setDataInDB(query);
    }

    public void setDataInDB(String query){
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        Cursor cursor2 = db.rawQuery(query, null);
        allAvtors = new User[cursor2.getCount()];
        int countElem = 0;
        while (cursor2.moveToNext()) {
            allAvtors[countElem] = new User();
            allAvtors[countElem].id= cursor2.getInt(0);
            allAvtors[countElem].first_name = cursor2.getString(1);
            allAvtors[countElem].surname = cursor2.getString(2);
            allAvtors[countElem].about_me = cursor2.getString(3);
            allAvtors[countElem].type = cursor2.getString(4);
            allAvtors[countElem].avatar_medium = cursor2.getString(5);
            allAvtors[countElem].avatar_w220 = cursor2.getString(6);
            countElem++;
        }
        cursor2.close();
        gvMain = (GridView) getView().findViewById(R.id.gridView);

        gridadapter = new ArrayAdapter<User>(getActivity(), R.layout.grid_item, R.id.grid_text,allAvtors ){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.grid_text);
                textView.setText(allAvtors[position].first_name+" "+ allAvtors[position].surname);

                ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
                ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ allAvtors[position].avatar_w220 ,grid_picture);
                return view;
            }
        };
        gvMain.setAdapter(gridadapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OneCoocker oneCoocker = new OneCoocker();
                Bundle bundle = new Bundle();
                bundle.putInt("currentPositionAvtor", position);
                oneCoocker.setArguments(bundle);
                android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.conteiner, oneCoocker);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.avtors);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        View v = inflater.inflate(R.layout.recepts_layout,null);
        return v;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        switch (itemPosition){
            case 0:
            {
                String FROM1 = "Id, First_name, Surname, About_me, Type, Avatar_medium, Avatar_w220";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_USER_NAME;
                setDataInDB(query);
                break;
            }
            case 1:{

                String FROM1 = "Id, First_name, Surname, About_me, Type, Avatar_medium, Avatar_w220";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_USER_NAME +" WHERE "+ Type +" LIKE 'Chie%' " ;
                setDataInDB(query);
                break;}
            case 2:{
                String FROM1 = "Id, First_name, Surname, About_me, Type, Avatar_medium, Avatar_w220";
                String query = "SELECT " + FROM1 + " FROM " + TABLE_USER_NAME +" WHERE "+ Type +" LIKE 'Coo%' " ;


                setDataInDB(query);
                break;}
        }
        return false;
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

}
