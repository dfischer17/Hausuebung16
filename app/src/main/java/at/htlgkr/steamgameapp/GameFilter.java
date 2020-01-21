package at.htlgkr.steamgameapp;

import android.widget.Adapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import at.htlgkr.steam.Game;

public class GameFilter extends Filter {
    private List<Game> gameList;
    private GameAdapter adapter;

    public GameFilter(List<Game> gameList, GameAdapter adapter) {
        this.gameList = gameList;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        charSequence = charSequence.toString().toLowerCase();
        FilterResults result = new FilterResults();
        List<Game> filteredGames;

        if (charSequence != null && charSequence.toString().length() > 0) {
            filteredGames = new ArrayList<>();

            for (Game curGame : gameList) {
                if (curGame.toString().toLowerCase().contains(charSequence)) filteredGames.add(curGame);
            }

            result.values = filteredGames;
            result.count = filteredGames.size();
        }

        else {
            synchronized (this) {
                result.values = gameList;
                result.count = gameList.size();
            }
        }
        return result;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        gameList = (ArrayList<Game>)filterResults.values;
        adapter.notifyDataSetChanged();
        gameList.clear();

    }
}
