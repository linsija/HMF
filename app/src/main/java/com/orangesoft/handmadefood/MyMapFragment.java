package com.orangesoft.handmadefood;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;

public class MyMapFragment extends SupportMapFragment {

    private Runnable callback;

    public MyMapFragment() {
    }

    public MyMapFragment(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        callback.run();
    }
}
