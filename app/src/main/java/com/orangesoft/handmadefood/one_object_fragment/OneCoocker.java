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
    public void onStart() {
        super.onStart();
        ActionBar bar = getActivity().getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.one_coocker, null);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.cooker);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        TabHost tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Биография");
        tabSpec.setContent(R.id.biography_layout);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Рецепты");
        tabSpec.setContent(R.id.recepts_layout);
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");


        photoAvtor = (ImageView) v.findViewById(R.id.povar_photo);
        biographiText = (TextView) v.findViewById(R.id.biographi_text);
        povarName = (TextView) v.findViewById(R.id.povar_name);

        ImageLoader.getInstance().displayImage("http://handmadefood.ru/"+ CookerFragment.allAvtors[numberItemAvtor].avatar_w220 , photoAvtor);
        biographiText.setText(Html.fromHtml(CookerFragment.allAvtors[numberItemAvtor].about_me));
        povarName.setText(CookerFragment.allAvtors[numberItemAvtor].first_name + " "+ CookerFragment.allAvtors[numberItemAvtor].surname);
        return v;
    }
}
