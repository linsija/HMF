package com.orangesoft.handmadefood.one_object_fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.orangesoft.handmadefood.R;
import com.orangesoft.handmadefood.main_fragments.CookerFragment;

/**
 * Created by Виолетта on 22.07.2014.
 */
public class OneCoocker extends Fragment {

    TextView biography;
    TextView recepts;
    ImageView photoAvtor;
    int numberItemAvtor;
    TextView biographiText;
    TextView povarName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        numberItemAvtor = bundle.getInt("currentPositionAvtor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.one_coocker, null);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.cooker);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        biography = (TextView) v.findViewById(R.id.biographi);
        recepts = (TextView) v.findViewById(R.id.recepts_button);
        photoAvtor = (ImageView) v.findViewById(R.id.povar_photo);
        biographiText = (TextView) v.findViewById(R.id.biographi_text);
        povarName = (TextView) v.findViewById(R.id.povar_name);

        final RelativeLayout biographi_layout = (RelativeLayout) v.findViewById(R.id.biographi_layout);
        final RelativeLayout recepts_layout = (RelativeLayout) v.findViewById(R.id.recepts_layout);
        ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ CookerFragment.allAvtors[numberItemAvtor].avatar_w220 , photoAvtor);
        biographiText.setText(Html.fromHtml(CookerFragment.allAvtors[numberItemAvtor].about_me));
        povarName.setText(CookerFragment.allAvtors[numberItemAvtor].first_name + " "+ CookerFragment.allAvtors[numberItemAvtor].surname);


        biographi_layout.setVisibility(View.VISIBLE);
        recepts_layout.setVisibility(View.INVISIBLE);

        biography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biography.setBackgroundColor(Color.parseColor("#ffffff"));
                recepts.setBackgroundColor(Color.parseColor("#696969"));
                biography.setTextColor(Color.parseColor("#111111"));
                recepts.setTextColor(Color.parseColor("#8B0000"));
                biographi_layout.setVisibility(View.VISIBLE);
                recepts_layout.setVisibility(View.INVISIBLE);
            }
        });
        recepts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biography.setBackgroundColor(Color.parseColor("#696969"));
                recepts.setBackgroundColor(Color.parseColor("#ffffff"));

                biography.setTextColor(Color.parseColor("#8B0000"));
                recepts.setTextColor(Color.parseColor("#111111"));
                biographi_layout.setVisibility(View.INVISIBLE);
                recepts_layout.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }
}
