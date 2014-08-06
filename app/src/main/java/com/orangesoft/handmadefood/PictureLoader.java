package com.orangesoft.handmadefood;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PictureLoader extends Application {

    @Override
    public void onCreate() {
        super.onCreate();





       // ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }
}