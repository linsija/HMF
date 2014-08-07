package com.orangesoft.handmadefood.one_object_fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.MyMapFragment;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.main_fragments.RestoranFragment;

public class OneRestoran extends Fragment {

    TextView description;
    TextView adress;
    int numberItemRestoran;
    TextView restoranAdress;
    TextView email;
    ImageView restoranPicture;
    TextView restoranName;
    TextView restoranAbout;
    private MyMapFragment mapFragmnet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        numberItemRestoran = bundle.getInt("currentPositionRestoran");



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.one_restoran, null);

        mapFragmnet = new MyMapFragment(new Runnable() {
            @Override
            public void run() {
                mapFragmnet.getMap();
            }
        });
//        GoogleMap map = mapFragmnet.getMap();
//        map.addMarker(new MarkerOptions());

        FragmentTransaction tr = getChildFragmentManager().beginTransaction();
        tr.add(R.id.map, mapFragmnet);
        tr.commit();

        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.restoran);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        description = (TextView) v.findViewById(R.id.description_restoran);
        adress = (TextView) v.findViewById(R.id.adress_restoran);
        restoranAdress = (TextView) v.findViewById(R.id.adress);
        email = (TextView) v.findViewById(R.id.email);
        restoranName = (TextView) v.findViewById(R.id.textView);
        restoranPicture = (ImageView) v.findViewById(R.id.restoran_picture);
        restoranAbout = (TextView) v.findViewById(R.id.textView3);

        final RelativeLayout description_layout = (RelativeLayout) v.findViewById(R.id.description_layout_restoran);
        final RelativeLayout adress_layout = (RelativeLayout) v.findViewById(R.id.adress_layout);

         description_layout.setVisibility(View.VISIBLE);
        adress_layout.setVisibility(View.INVISIBLE);
        restoranAdress.setText(RestoranFragment.allRestorans[numberItemRestoran].address);
        email.setText(RestoranFragment.allRestorans[numberItemRestoran].contacts[0]);
        ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ RestoranFragment.allRestorans[numberItemRestoran].image_big , restoranPicture);
        restoranName.setText(RestoranFragment.allRestorans[numberItemRestoran].title);
        restoranAbout.setText(Html.fromHtml(RestoranFragment.allRestorans[numberItemRestoran].about));

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setBackgroundColor(Color.parseColor("#ffffff"));
                adress.setBackgroundColor(Color.parseColor("#696969"));
                description.setTextColor(Color.parseColor("#111111"));
                adress.setTextColor(Color.parseColor("#8B0000"));
                description_layout.setVisibility(View.VISIBLE);
                adress_layout.setVisibility(View.INVISIBLE);
            }
        });

        adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setBackgroundColor(Color.parseColor("#696969"));
                adress.setBackgroundColor(Color.parseColor("#ffffff"));

                description.setTextColor(Color.parseColor("#8B0000"));
                adress.setTextColor(Color.parseColor("#111111"));
                description_layout.setVisibility(View.INVISIBLE);
                adress_layout.setVisibility(View.VISIBLE);
            }
        });


        return v;
    }
}
