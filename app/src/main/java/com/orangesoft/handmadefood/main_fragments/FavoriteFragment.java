package com.orangesoft.handmadefood.main_fragments;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.DataBaseHandler;
import com.orangesoft.handmadefood.FavoriteRecipe;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.abstraction.AboutFragment;
import com.orangesoft.handmadefood.abstraction.Recipe;
import com.orangesoft.handmadefood.one_object_fragment.OneRecept;

import static com.orangesoft.handmadefood.Constans.TABLE_FAVORITE_NAME;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FavoriteFragment extends Fragment implements
        ActionBar.OnNavigationListener {

    public static FavoriteRecipe[] favoriteRecipes;
    private static Recipe[] needElements;
    public DataBaseHandler dataBaseHandler;
    private  GridView gvMain;
    private  ArrayAdapter<Recipe> gridadapter;
    AboutFragment aboutFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        dataBaseHandler = new DataBaseHandler(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("");
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        View v = inflater.inflate(R.layout.recepts_layout,null);
        return v;
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorite_menu, menu);
    }

    @Override
    public void onStart() {
        super.onStart();
        SQLiteDatabase db = dataBaseHandler.getReadableDatabase();
        String FROM1 = "Id, Count, Title"; //
        String query = "SELECT " + FROM1 + " FROM " + TABLE_FAVORITE_NAME;
        Cursor cursor2 = db.rawQuery(query, null);
        favoriteRecipes = new FavoriteRecipe[cursor2.getCount()];
        needElements =new Recipe[cursor2.getCount()];
        int countElem = 0;
        while (cursor2.moveToNext()) {
            favoriteRecipes[countElem] = new FavoriteRecipe();
            favoriteRecipes[countElem].title= cursor2.getString(2);
            favoriteRecipes[countElem].count= cursor2.getInt(1);
            countElem++;
        }
        cursor2.close();
        int needCount= 0;
        for (int i=0; i<favoriteRecipes.length;i++){
            for (int k=0; k<ReceptFragment.allRecipes.length;k++){

                if (favoriteRecipes[i].title.equals(ReceptFragment.allRecipes[k].title)){
                    needElements[needCount]= new Recipe();
                    needElements[needCount]=ReceptFragment.allRecipes[k];
                    needCount++;
                }
            }
        }
        gvMain = (GridView) getView().findViewById(R.id.gridView);

        gridadapter = new ArrayAdapter<Recipe>(getActivity(), R.layout.grid_item, R.id.grid_text,needElements ){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.grid_text);
                textView.setText(needElements[position].title);

                ImageView grid_picture = (ImageView) view.findViewById(R.id.grid_picture);
                ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ needElements[position].general_image ,grid_picture);
                return view;
            }
        };
        gvMain.setAdapter(gridadapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OneRecept oneRecept = new OneRecept();
                Bundle bundle = new Bundle();
                bundle.putInt("currentPosition", favoriteRecipes[position].count);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret;
        if (item.getItemId() == R.id.itm1) {
            ret = true;
            aboutFragment = new AboutFragment();
            android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.conteiner, aboutFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            ret = super.onOptionsItemSelected(item);
        }
        return ret;
    }
}
