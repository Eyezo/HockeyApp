package com.example.eyezo.hockeyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OppListFrag extends Fragment {


    public OppListFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opp_list, container,false);
        ListView lstTeams = view.findViewById(R.id.lstOpps);
        // Inflate the layout for this fragment
        return view;
    }
}
