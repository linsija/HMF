package com.orangesoft.handmadefood.net;

import com.orangesoft.handmadefood.abstraction.Category;
import com.orangesoft.handmadefood.abstraction.Recipe;
import com.orangesoft.handmadefood.abstraction.Restaurant;
import com.orangesoft.handmadefood.abstraction.User;

import retrofit.http.GET;

public interface RestService {

    static final String HOST = "http://handmadefood.ru/api";

//    @GET("/recipes.json")
 //   void getRecipe(Callback<Recipe[]> callback);

    @GET("/recipes.json")
    Recipe[] getRecipe();

    @GET("/restaurants.json")
    Restaurant[] getRestoran();
    //void getRestoran(Callback<Restaurant[]> callback);


    @GET("/users.json")
    User[] getAvtors();


    @GET("/categories/dishes.json")
    Category[] getCategory();
}
