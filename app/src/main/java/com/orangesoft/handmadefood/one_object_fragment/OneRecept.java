package com.orangesoft.handmadefood.one_object_fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

    TextView description;
    TextView make;
    TextView textViewName;
    TextView countPortion;
    TextView countTime;
    int numberItemRecept;
    ImageView receptPicture;
    TextView contains;
    TextView category;
    TextView generalIngridient;
    TextView receptText;
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
            favoriteRecipes[countElem].count=cursor2.getInt(1);
            favoriteRecipes[countElem].title= cursor2.getString(2);
            countElem++;
        }
        needRecept= new Recipe();

        for (int i=0; i<ReceptFragment.allRecipes.length;i++){
            if (ReceptFragment.allRecipes[i].id==numberItemRecept) {
                needRecept=ReceptFragment.allRecipes[i];
                break;
            }
        }
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.one_recept, null);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.recept);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        description = (TextView) v.findViewById(R.id.description);
        contains = (TextView) v.findViewById(R.id.contains);
        make=  (TextView) v.findViewById(R.id.make);
        textViewName = (TextView) v.findViewById(R.id.textView);
        countPortion = (TextView) v.findViewById(R.id.count_portion);
        countTime = (TextView) v.findViewById(R.id.count_time);
        receptPicture = (ImageView) v.findViewById(R.id.recept_picture);
        generalIngridient = (TextView) v.findViewById(R.id.general_ingridient);
        category = (TextView) v.findViewById(R.id.category);
        receptText = (TextView) v.findViewById(R.id.make_text);

        textViewName.setText(needRecept.title);
        countPortion.setText(needRecept.servings_number +" порций");
        countTime.setText(needRecept.cook_time+" минут");
        ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ needRecept.general_image , receptPicture);
//        generalIngridient.setText(ReceptFragment.allRecipes[numberItemRecept].ingridient_categories.toString());
//        contains.setText(ReceptFragment.allRecipes[numberItemRecept].ingridients.toString());
        category.setText(needRecept.food_categories[0]);
        receptText.setText(Html.fromHtml(needRecept.content));



        final LinearLayout description_layout = (LinearLayout) v.findViewById(R.id.description_layout);
        final RelativeLayout make_layout = (RelativeLayout) v.findViewById(R.id.make_layout);
        final RelativeLayout ingridient_layout = (RelativeLayout) v.findViewById(R.id.content_layout);
        description_layout.setVisibility(View.VISIBLE);
        make_layout.setVisibility(View.INVISIBLE);
        ingridient_layout.setVisibility(View.INVISIBLE);
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setBackgroundColor(Color.parseColor("#ffffff"));
                contains.setBackgroundColor(Color.parseColor("#696969"));
                make.setBackgroundColor(Color.parseColor("#696969"));
                description.setTextColor(Color.parseColor("#111111"));
                contains.setTextColor(Color.parseColor("#8B0000"));
                make.setTextColor(Color.parseColor("#8B0000"));
                description_layout.setVisibility(View.VISIBLE);
                make_layout.setVisibility(View.INVISIBLE);
                ingridient_layout.setVisibility(View.INVISIBLE);
            }
        });

        contains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setBackgroundColor(Color.parseColor("#696969"));
                contains.setBackgroundColor(Color.parseColor("#ffffff"));
                make.setBackgroundColor(Color.parseColor("#696969"));

                description.setTextColor(Color.parseColor("#8B0000"));
                contains.setTextColor(Color.parseColor("#111111"));
                make.setTextColor(Color.parseColor("#8B0000"));
                description_layout.setVisibility(View.INVISIBLE);
                make_layout.setVisibility(View.INVISIBLE);
                ingridient_layout.setVisibility(View.VISIBLE);
            }
        });

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setBackgroundColor(Color.parseColor("#696969"));
                contains.setBackgroundColor(Color.parseColor("#696969"));
                make.setBackgroundColor(Color.parseColor("#ffffff"));

                description.setTextColor(Color.parseColor("#8B0000"));
                contains.setTextColor(Color.parseColor("#8B0000"));
                make.setTextColor(Color.parseColor("#111111"));
                description_layout.setVisibility(View.INVISIBLE);
                make_layout.setVisibility(View.VISIBLE);
                ingridient_layout.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.one_recept, menu);
        this.menu = menu;
        flag= false;
        MenuItem star = menu.findItem(R.id.favorites);
        for (int i=0; i<favoriteRecipes.length;i++){
            if (textViewName.getText().equals(favoriteRecipes[i].title)){
                star.setIcon(R.drawable.starfull);
                flag=true;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret;
        if (item.getItemId() == R.id.favorites) {
            MenuItem star = menu.findItem(R.id.favorites);
            if (flag){
                star.setIcon(R.drawable.star);
                db.execSQL("DELETE FROM "+TABLE_FAVORITE_NAME + " WHERE "+ Count + " = "+ numberItemRecept );
                flag= false;
            }
            else  {
                star.setIcon(R.drawable.starfull);
                flag=true;
                ContentValues values = new ContentValues();
                values.put(Count, needRecept.id);
                values.put(Title, needRecept.title );
                db.insertOrThrow(TABLE_FAVORITE_NAME, null, values);
            }

            ret = true;

        } else if (item.getItemId()==R.id.send){
            sendData();
            ret=true;
        }
        else
            ret = super.onOptionsItemSelected(item);

        return ret;
    }

    private void sendData() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpg");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,needRecept.title );
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://handmadefood.ru//" + needRecept.general_image+ ".jpg"));
        shareIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(needRecept.content).toString());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.hand_made_food)));
    }
}
