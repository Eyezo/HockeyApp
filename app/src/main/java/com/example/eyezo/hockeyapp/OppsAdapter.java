package com.example.eyezo.hockeyapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OppsAdapter extends ArrayAdapter<TeamClass> {
    private List<TeamClass> opponents;
    private Context context;


    public OppsAdapter(Context context, List<TeamClass> opponents)
    {
        super(context,R.layout.opponent_row,opponents);

        this.opponents = opponents;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.opponent_row,parent,false);

        TextView name = view.findViewById(R.id.txtNameAdapterOpponent);
        if(opponents.get(position).getTeamOrOpp().equals("Opponent"))
            name.setText("Opponent Team: " + opponents.get(position).getTeamName());
        else
            name.setText("Team: " + opponents.get(position).getTeamName());

        return view;
    }
}
