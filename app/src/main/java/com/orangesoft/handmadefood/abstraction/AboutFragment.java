package com.orangesoft.handmadefood.abstraction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangesoft.handmadefood.R;

/**
 * Created by Виолетта on 24.07.2014.
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_layout, null);
        return v;
    }
}
