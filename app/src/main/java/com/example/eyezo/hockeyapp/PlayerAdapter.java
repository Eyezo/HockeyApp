package com.example.eyezo.hockeyapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<AddPlayer> players;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public PlayerAdapter(Context context, List<AddPlayer> players)
    {
        this.players = players;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, team;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.txtNameAdapterPlayer);
            team = itemView.findViewById(R.id.teamAdapterPlayer);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(players.indexOf((AddPlayer) v.getTag()));
                }
            });

        }
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PlayerAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(players.get(position));
        holder.name.setText(players.get(position).getName() + " " + players.get(position).getSurname());
        holder.team.setText(players.get(position).getTeamName());
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
