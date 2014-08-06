package com.orangesoft.handmadefood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orangesoft.handmadefood.main_fragments.ReceptFragment;

/**
 * Created by Виолетта on 24.07.2014.
 */
public class FilterFragment extends Fragment {

    String[] categoryNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i=0; i< ReceptFragment.allRecipes.length; i++){

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_recepts, null);
        return v;
    }
}
