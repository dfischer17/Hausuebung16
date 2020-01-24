package at.htlgkr.steamgameapp;


import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.htlgkr.steam.Game;
import at.htlgkr.steam.SteamBackend;

public class GameAdapter extends BaseAdapter implements Filterable {
    private List<Game> gameList = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflator;
    private Filter gameFilter;
    private Context context;

    public GameAdapter(Context context, int listViewItemLayoutId, List<Game> games) {
        // Implementieren Sie diesen Konstruktor
        this.gameList = games;
        this.layoutId = listViewItemLayoutId;
        this.inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
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

    @Override
    public Filter getFilter() {
        if (gameFilter == null) gameFilter = new GameFilter(context);
        return gameFilter;
    }

    private class GameFilter extends Filter {
        private Context context;
        private static final String GAMES_CSV = "games.csv";
        SteamBackend backend = new SteamBackend();

        public GameFilter(Context context) {
            this.context = context;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            // Filtern
            if (charSequence == null || charSequence.length() == 0 || charSequence.equals("")) {
                // Kein Filter implementiert alles zurueckgeben
                List<Game> loadedGames = loadGames();
                results.values = loadedGames;
                results.count = loadedGames.size();
            }
            else {
                // Filter
                List<Game> nGameList = new ArrayList<>();

                for (Game curGame : gameList) {
                    if (curGame.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        nGameList.add(curGame);
                    }
                }

                results.values = nGameList;
                results.count = nGameList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            // Ergebnis ist leer
            if (filterResults.count == 0) {
                gameList.clear();
                notifyDataSetChanged();
            }
            else {
                // Ergebnis des Filters in ListView schreiben
                gameList = (List<Game>) filterResults.values;
                notifyDataSetChanged();
            }
        }

        private List<Game> loadGames() {
            // Implementieren Sie diese Methode.
            AssetManager assets = context.getAssets();

            // Games aus File laden
            InputStream inputStream = null;
            try {
                inputStream = assets.open(GAMES_CSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
            backend.loadGames(inputStream);

            // Liste der geladenen Games
            List<Game> gameList = backend.getGames();

           return gameList;
        }
    }
}
