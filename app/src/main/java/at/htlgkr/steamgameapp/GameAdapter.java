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
    public View getView(int position, View givenView, ViewGroup parent) {
        //Implementieren Sie diese Methode;
        Game game = gameList.get(position);
        View listItem = (givenView == null) ? inflator.inflate(this.layoutId, null) : givenView;
        ((TextView) givenView.findViewById(R.id.title)).setText(String.valueOf(game.getPrice()));
        ((TextView) givenView.findViewById(R.id.date)).setText(game.getName());
        ((TextView) givenView.findViewById(R.id.price)).setText(String.valueOf(game.getPrice()));
        return givenView;
    }
}
