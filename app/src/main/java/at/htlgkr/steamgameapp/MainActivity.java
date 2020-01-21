package at.htlgkr.steamgameapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import at.htlgkr.steam.Game;
import at.htlgkr.steam.ReportType;
import at.htlgkr.steam.SteamBackend;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "game";
    private static final String GAMES_CSV = "games.csv";
    private SteamBackend backend = new SteamBackend();
    private GameAdapter adapter;
    private ArrayAdapter<ReportTypeSpinnerItem> spinnerAdapter;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadGamesIntoListView();
        setUpReportSelection();
        setUpSearchButton();
        setUpAddGameButton();
        setUpSaveButton();
    }

    private void loadGamesIntoListView() {
        // Implementieren Sie diese Methode.
        AssetManager assets = getAssets();

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

        // Adapter erstellen
        adapter = new GameAdapter(this, R.layout.game_item_layout, gameList);

        // ListView referenzieren
        ListView listView = findViewById(R.id.gamesList);

        // Adapter mit ListView verknuepfen
        listView.setAdapter(adapter);

        // Ermoeglicht das suchen von Spielen
        listView.setTextFilterEnabled(true);
    }

        private void setUpReportSelection () {
            // Implementieren Sie diese Methode.
            // spinner-item list
            ReportTypeSpinnerItem spinnerItem = new ReportTypeSpinnerItem(ReportType.NONE, SteamGameAppConstants.SELECT_ONE_SPINNER_TEXT);
            ReportTypeSpinnerItem spinnerItem2 = new ReportTypeSpinnerItem(ReportType.SUM_GAME_PRICES, SteamGameAppConstants.SUM_GAME_PRICES_SPINNER_TEXT);
            ReportTypeSpinnerItem spinnerItem3 = new ReportTypeSpinnerItem(ReportType.AVERAGE_GAME_PRICES, SteamGameAppConstants.AVERAGE_GAME_PRICES_SPINNER_TEXT);
            ReportTypeSpinnerItem spinnerItem4 = new ReportTypeSpinnerItem(ReportType.UNIQUE_GAMES, SteamGameAppConstants.UNIQUE_GAMES_SPINNER_TEXT);
            ReportTypeSpinnerItem spinnerItem5 = new ReportTypeSpinnerItem(ReportType.MOST_EXPENSIVE_GAMES, SteamGameAppConstants.MOST_EXPENSIVE_GAMES_SPINNER_TEXT);
            List<ReportTypeSpinnerItem> spinnerItems = Arrays.asList(spinnerItem, spinnerItem2, spinnerItem3, spinnerItem4, spinnerItem5);

            // array-adapter fuer spinner
            spinnerAdapter = new ArrayAdapter<ReportTypeSpinnerItem>(this, R.layout.support_simple_spinner_dropdown_item, spinnerItems);

            // spinner referenzieren
            Spinner spinner = findViewById(R.id.chooseReport);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i) {
                        case 1:
                            showDialog(SteamGameAppConstants.ALL_PRICES_SUM + backend.sumGamePrices());
                            break;

                        case 2:
                            showDialog(SteamGameAppConstants.ALL_PRICES_AVERAGE + backend.averageGamePrice());
                            break;

                        case 3:
                            showDialog(SteamGameAppConstants.UNIQUE_GAMES_SPINNER_TEXT + backend.getUniqueGames().size());
                            break;

                        case 4:
                            String topNGames = formatTopNGames(backend.selectTopNGamesDependingOnPrice(3));
                            showDialog(SteamGameAppConstants.MOST_EXPENSIVE_GAMES + topNGames);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            // spinnern und adapter verknuepfen
            spinner.setAdapter(spinnerAdapter);
        }

        private void setUpSearchButton () {
            // Implementieren Sie diese Methode.
            Button saveButton = findViewById(R.id.save);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View viewDialog = getLayoutInflater().inflate(R.layout.save_dialog_layout, null);
                    GameFilter filter = new GameFilter(backend.getGames(), adapter);
                    new AlertDialog.Builder(this, )
                            .setView(viewDialog)
                            .setTitle(SteamGameAppConstants.ENTER_SEARCH_TERM)
                            .setPositiveButton("SEARCH", )
                            .setNegativeButton("Abbrechen", null)
                            .show();

                }
            });
        }

        private void setUpAddGameButton () {
            // Implementieren Sie diese Methode.
        }

        private void setUpSaveButton () {
            // Implementieren Sie diese Methode.
        }

        private void showDialog(String text) {
            alert = new AlertDialog.Builder(this);
            alert.setMessage(text);
            alert.show();
        }

        private void showSaveDialog() {
            alert = new AlertDialog.Builder(this);
            alert.setTitle(SteamGameAppConstants.ENTER_SEARCH_TERM);
            alert.setMessage();
            alert.show();
        }

        private String formatTopNGames(List<Game> mostExpensiveGames) {
            String output = "";
            StringBuilder builder = new StringBuilder(output);

            for (Game curGame : mostExpensiveGames) {
                builder.append(curGame.getName());
                builder.append("\n");
            }

            // TODO STIMMT UMGANG MIT BUILDER?
            return builder.toString();
        }

        private Filter search(String searched) {
            GameFilter filter = new GameFilter(backend.getGames(), adapter);
            return filter.performFiltering(searched);
        }
    }
