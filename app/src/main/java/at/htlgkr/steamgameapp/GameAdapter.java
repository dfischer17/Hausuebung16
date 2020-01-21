package at.htlgkr.steamgameapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.htlgkr.steam.Game;

public class GameAdapter extends BaseAdapter {
    private List<Game> gameList = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflator;

    public GameAdapter(Context context, int listViewItemLayoutId, List<Game> games) {
        // Implementieren Sie diesen Konstruktor
        this.gameList = games;
        this.layoutId = listViewItemLayoutId;
        this.inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // Implementiren Sie diese Methode
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        // Implementiren Sie diese Methode
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // Implementiren Sie diese Methode
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Game game = gameList.get(i);
        View listItem = (view == null) ?
                inflator.inflate(this.layoutId, null) : view;

        ((TextView) listItem.findViewById(R.id.name)).setText(game.getName());
        ((TextView) listItem.findViewById(R.id.date)).setText(game.getFormattedReleaseDate());
        ((TextView) listItem.findViewById(R.id.price)).setText(String.valueOf(game.getPrice()));

        return listItem;
    }
}
