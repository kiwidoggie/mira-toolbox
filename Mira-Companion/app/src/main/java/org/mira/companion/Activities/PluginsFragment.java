package org.mira.companion.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mira.companion.R;

/**
 * Created by @AstonBraham on 24/05/2018.
 */
public class PluginsFragment extends Fragment {


    public PluginsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plugins, container, false);
    }

}
