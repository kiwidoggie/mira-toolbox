package org.mira.companion.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mira.companion.R;

import butterknife.ButterKnife;

/**
 * Created by @AstonBraham on 24/05/2018.
 */
public class StoreFragment extends Fragment {


    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_store, container, false);
        ButterKnife.bind(this, v);
        // For debuggin purpose
        ButterKnife.setDebug(true);
        return v;
    }

}
