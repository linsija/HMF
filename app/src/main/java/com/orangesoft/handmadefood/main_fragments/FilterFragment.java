package com.orangesoft.handmadefood.main_fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.orangesoft.handmadefood.DataBaseHandler;
import com.orangesoft.handmadefood.FoodApplication;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.abstraction.Category;
import com.orangesoft.handmadefood.abstraction.CheckedList;
import com.orangesoft.handmadefood.net.RestService;

import static com.orangesoft.handmadefood.Constans.Count;
import static com.orangesoft.handmadefood.Constans.Id;
import static com.orangesoft.handmadefood.Constans.Name;
import static com.orangesoft.handmadefood.Constans.TABLE_CATEGORY_NAME;
import static com.orangesoft.handmadefood.R.layout.filter_recepts;
import static com.orangesoft.handmadefood.R.layout.simple_list_view;

/**
 * Created by Виолетта on 24.07.2014.
 */
public class FilterFragment extends Fragment {

    private FoodApplication application;
    private RestService restService;
    public DataBaseHandler dataBaseHandler;
    public static Category[] allCategories ;
    private ListView listView;
    public String[] neednames;
    public CheckedList[] checkedList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseHandler = new DataBaseHandler(getActivity().getApplicationContext());
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
                Category[] category = restService.getCategory();
                addEvent(category);
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

    public void getDataFromDB(String query){
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        allCategories = new Category[cursor.getCount()];
        int countElem = 0;
        while (cursor.moveToNext()) {
            allCategories[countElem] = new Category();
            allCategories[countElem].id = cursor.getInt(0);
            allCategories[countElem].name = cursor.getString(1);
            allCategories[countElem].count = cursor.getInt(2);
            countElem++;
        }

        cursor.close();
        checkedList = new CheckedList[allCategories.length];
        for (int i=0; i<allCategories.length; i++){
            checkedList[i]= new CheckedList();
            checkedList[i].nameCategory = allCategories[i].name;
            checkedList[i].check = true;
        }
        listView = (ListView) getView().findViewById(R.id.listview);
        ArrayAdapter<CheckedList> adapter = new ArrayAdapter<CheckedList>(getActivity().getApplicationContext(), simple_list_view, R.id.text1,checkedList ) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.text1);
                textView.setText(checkedList[position].nameCategory);
                final CheckBox checkBox = (CheckBox) view.findViewById(R.id.check);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checkedList[position].check==true){
                            checkedList[position].check=false;
                            checkBox.setChecked(false);
                        }
                        else {
                            checkedList[position].check=true;
                            checkBox.setChecked(true);
                        }
                    }
                });
                return view;
            }
        };

        listView.setAdapter(adapter);
    }

    public void addEvent(Category[] getcategory) {
        SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_NAME);
        db.execSQL("CREATE TABLE " + TABLE_CATEGORY_NAME + " (" + Id
                + " INTEGER, " + Name + " TEXT NOT NULL,"
                + Count +" INTEGER);" );

        for (int i=0; i<getcategory.length;i++){
            ContentValues values = new ContentValues();
            values.put(Id, getcategory[i].id);
            values.put(Name, getcategory[i].name );
            values.put(Count,getcategory[i].count );

            db.insertOrThrow(TABLE_CATEGORY_NAME, null, values);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(filter_recepts, null);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        String FROM1 = "Id, Name, Count";
        String query = "SELECT " + FROM1 + " FROM " + TABLE_CATEGORY_NAME;
        getDataFromDB(query);

    }

    @Override
    public void onStop() {
        super.onStop();
        int one=0;
        neednames = new String[checkedList.length];
        for (int i =0; i<checkedList.length; i++){
            if (checkedList[i].check==true){
                neednames[one]=checkedList[i].nameCategory;
                one++;
            }

        }
    }
}
