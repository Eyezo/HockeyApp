package com.example.eyezo.hockeyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private List<String> players;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public MatchAdapter(Context context, List<String> players)
    {
        this.players = players;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.matchTitle);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(players.indexOf((String) v.getTag()));
                }
            });

        }
    }

    @Override
    public MatchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MatchAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(players.get(position));
        holder.name.setText("Match : " + players.get(position));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
