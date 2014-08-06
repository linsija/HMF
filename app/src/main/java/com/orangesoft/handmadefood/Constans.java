package com.orangesoft.handmadefood;

import android.provider.BaseColumns;

/**
 * Created by Виолетта on 23.07.2014.
 */
public interface Constans extends BaseColumns {

    public static final String TABLE_RECIPES_NAME = "recipes";
    public static final String Id = "id";
    public static final String Title = "title";
    public static final String Rss_title = "rss_title";
    public static final String Content = "content";
    public static final String Cook_time = "cook_time";
    public static final String Servings_number = "servings_number";
    public static final String Slug = "slug";
    public static final String General_image ="general_image";
    public static final String Ingredients = "ingredients";
    public static final String Ingredient_categories = "ingredient_categories";
    public static final String Food_categories = "food_categories";
    public static final String Favorite = "favorite";
    public static final String Number= "number";

    public static final String TABLE_USER_NAME ="users ";
    public static final String First_name = "first_name";
    public static final String Surname = "surname";
    public static final String About_me = "about_me";
    public static final String Type= "type";
    public static final String Avatar_medium = "avatar_medium";
    public static final String Avatar_w220 = "avatar_w220";

    public static final String TABLE_RESTORAN_NAME =  "restaurants";
    public static final String About= "about";
    public static final String Address = "address";
    public static final String Latitude = "latitude";
    public static final String Longitude = "longitude";
    public static final String Contacts= "contacts";
    public static final String Image_big = "image_big";
    public static final String Image_square ="image_square";
    public static final String Categories = "categories";
    public static final String Images  = "images";

    public static final String TABLE_CATEGORY_NAME = "categories";
    public static final String Name = "name";
    public static final String Count = "count";

    public static final String TABLE_RESTORAN_CATEGORY_NAME = "categories";
    public static final String TABLE_FAVORITE_NAME ="favorite" ;


}