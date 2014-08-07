package com.orangesoft.handmadefood.one_object_fragment;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.MainActivity;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.main_fragments.RestoranFragment;

public class OneRestoran extends Fragment {


    int numberItemRestoran;
    TextView restoranAdress;
    TextView email;
    ImageView restoranPicture;
    TextView restoranName;
    TextView restoranAbout;


    private static GoogleMap mMap;
    private static Double latitude, longitude;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        numberItemRestoran = bundle.getInt("currentPositionRestoran");


    }


    @Override
    public void onStart() {
        super.onStart();
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.one_restoran, null);

        TabHost tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Описание");
        tabSpec.setContent(R.id.restoran_layout);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Адрес");
        tabSpec.setContent(R.id.adress_layout);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");



/*        mapFragmnet = new MyMapFragment(new Runnable() {
            @Override
            public void run() {
                mapFragmnet.getMap();
            }
        });
        GoogleMap map = mapFragmnet.getMap();
        map.addMarker(new MarkerOptions());

        FragmentTransaction tr = getChildFragmentManager().beginTransaction();
        tr.add(R.id.map, mapFragmnet);
        tr.commit(); */

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.restoran);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        restoranAdress = (TextView) v.findViewById(R.id.adress);
        email = (TextView) v.findViewById(R.id.email);
        restoranName = (TextView) v.findViewById(R.id.textView);
        restoranPicture = (ImageView) v.findViewById(R.id.restoran_picture);
        restoranAbout = (TextView) v.findViewById(R.id.textView3);
        restoranAdress.setText(RestoranFragment.allRestorans[numberItemRestoran].address);
        email.setText(RestoranFragment.allRestorans[numberItemRestoran].contacts[0]);
        ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ RestoranFragment.allRestorans[numberItemRestoran].image_big , restoranPicture);
        restoranName.setText(RestoranFragment.allRestorans[numberItemRestoran].title);
        restoranAbout.setText(Html.fromHtml(RestoranFragment.allRestorans[numberItemRestoran].about));
        latitude =(double) RestoranFragment.allRestorans[numberItemRestoran].latitude;
        longitude = (double) RestoranFragment.allRestorans[numberItemRestoran].longitude;

        setUpMapIfNeeded();

        return v;
    }

    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                longitude), 12.0f));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment)  MainActivity.fragmentManager.findFragmentById(R.id.location_map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMap != null) {
            MainActivity.fragmentManager.beginTransaction()
                    .remove(MainActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
            mMap = null;
        }
    }
}
