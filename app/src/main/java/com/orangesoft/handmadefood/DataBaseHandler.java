package com.orangesoft.handmadefood;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.orangesoft.handmadefood.Constans.About;
import static com.orangesoft.handmadefood.Constans.About_me;
import static com.orangesoft.handmadefood.Constans.Address;
import static com.orangesoft.handmadefood.Constans.Avatar_medium;
import static com.orangesoft.handmadefood.Constans.Avatar_w220;
import static com.orangesoft.handmadefood.Constans.Contacts;
import static com.orangesoft.handmadefood.Constans.Content;
import static com.orangesoft.handmadefood.Constans.Cook_time;
import static com.orangesoft.handmadefood.Constans.Count;
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
import static com.orangesoft.handmadefood.Constans.Name;
import static com.orangesoft.handmadefood.Constans.Rss_title;
import static com.orangesoft.handmadefood.Constans.Servings_number;
import static com.orangesoft.handmadefood.Constans.Slug;
import static com.orangesoft.handmadefood.Constans.Surname;
import static com.orangesoft.handmadefood.Constans.TABLE_CATEGORY_NAME;
import static com.orangesoft.handmadefood.Constans.TABLE_FAVORITE_NAME;
import static com.orangesoft.handmadefood.Constans.TABLE_RECIPES_NAME;
import static com.orangesoft.handmadefood.Constans.TABLE_RESTORAN_NAME;
import static com.orangesoft.handmadefood.Constans.TABLE_USER_NAME;
import static com.orangesoft.handmadefood.Constans.Title;
import static com.orangesoft.handmadefood.Constans.Type;

/**
 * Created by Виолетта on 23.07.2014.
 */
public class DataBaseHandler extends SQLiteOpenHelper {


    ;//SDCardPath.getExternalMounts() +"/Handmadefood.sqlite";
    private static final int DATABASE_VERSION = 1;

    Context context;

    public DataBaseHandler(Context ctx) {
        super(ctx,  ctx.getFilesDir().getPath()+"/Handmadefood.sqlite", null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_RECIPES_NAME + " (" + Id
                + " INTEGER, " + Title + " TEXT NOT NULL,"+ Rss_title + " TEXT,"
                + Content + " TEXT," + Cook_time + " TEXT,"
                + Servings_number + " TEXT,"+ Slug+ " TEXT,"
                + General_image+ " TEXT," +Ingredients+ " TEXT,"
                + Ingredient_categories+" TEXT,"+ Food_categories+ " TEXT);" );

        db.execSQL("CREATE TABLE " + TABLE_USER_NAME + " (" + Id
                + " INTEGER, " + First_name + " TEXT,"
                + Surname + " TEXT," + About_me + " TEXT,"
                + Type + " TEXT," + Avatar_medium+ " TEXT,"
                + Avatar_w220 +" TEXT);" );

        db.execSQL("CREATE TABLE " + TABLE_RESTORAN_NAME + " (" + Id
                + " INTEGER, " + Title + " TEXT NOT NULL,"
                + About + " TEXT," + Address + " TEXT,"
                + Slug+ " TEXT,"
                + Latitude+ " TEXT," +Longitude+ " TEXT,"
                + Contacts+" TEXT," + Image_big + " TEXT," + Image_square+ /*" TEXT ,"
                + Categories+ " TEXT," +Images +*/" TEXT);" );

        db.execSQL("CREATE TABLE " + TABLE_CATEGORY_NAME + " (" + Id
                + " INTEGER, " + Name + " TEXT,"
                + Count +" INTEGER);" );

        db.execSQL("CREATE TABLE " + TABLE_FAVORITE_NAME + " (" + Id
                + " INTEGER, "+Count+" INTEGER, "+ Title + " TEXT);" );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES_NAME);
      //  onCreate(db);
    }

    ///Запрос для изменения значения is_favorite в БД
    public void updateFavorite(SQLiteDatabase db, int id, int i) {
        db.execSQL("UPDATE "+ TABLE_RECIPES_NAME+ " SET " +Favorite+" = "+ i+" WHERE "+ _ID +" = " + id );

    }
}
