package com.orangesoft.handmadefood;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orangesoft.handmadefood.net.RestService;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

public class FoodApplication extends Application {

    private static final String RETROFIT_LOG_TAG = "Retrofit: ";

    private RestService restService;

    @Override
    public void onCreate() {
        super.onCreate();

        setUpNetwork();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void setUpNetwork() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(RestService.HOST)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(RETROFIT_LOG_TAG))
                .build();

        restService = restAdapter.create(RestService.class);
    }

    public RestService getRestService() {
        return restService;
    }

}
