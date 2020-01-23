package at.htlgkr.steamgameapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
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

    private void setUpReportSelection() {
        // Implementieren Sie diese Methode.
        // spinner-item list
        ReportTypeSpinnerItem spinnerItem = new ReportTypeSpinnerItem(ReportType.NONE, SteamGameAppConstants.SELECT_ONE_SPINNER_TEXT);
        ReportTypeSpinnerItem spinnerItem2 = new ReportTypeSpinnerItem(ReportType.SUM_GAME_PRICES, SteamGameAppConstants.SUM_GAME_PRICES_SPINNER_TEXT);
        ReportTypeSpinnerItem spinnerItem3 = new ReportTypeSpinnerItem(ReportType.AVERAGE_GAME_PRICES, SteamGameAppConstants.AVERAGE_GAME_PRICES_SPINNER_TEXT);
        ReportTypeSpinnerItem spinnerItem4 = new ReportTypeSpinnerItem(ReportType.UNIQUE_GAMES, SteamGameAppConstants.UNIQUE_GAMES_SPINNER_TEXT);
        ReportTypeSpinnerItem spinnerItem5 = new ReportTypeSpinnerItem(ReportType.MOST_EXPENSIVE_GAMES, SteamGameAppConstants.MOST_EXPENSIVE_GAMES_SPINNER_TEXT);
        List<ReportTypeSpinnerItem> spinnerItems = Arrays.asList(spinnerItem, spinnerItem2, spinnerItem3, spinnerItem4, spinnerItem5);

        // array-adapter fuer spinner
        spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerItems);

        // spinner referenzieren
        Spinner spinner = findViewById(R.id.chooseReport);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 1:
                        showReportDialog(spinnerAdapter.getItem(i).getDisplayText(), SteamGameAppConstants.ALL_PRICES_SUM + backend.sumGamePrices());
                        break;

                    case 2:
                        showReportDialog(spinnerAdapter.getItem(i).getDisplayText(), SteamGameAppConstants.ALL_PRICES_AVERAGE + backend.averageGamePrice());
                        break;

                    case 3:
                        showReportDialog(spinnerAdapter.getItem(i).getDisplayText(), SteamGameAppConstants.UNIQUE_GAMES_SPINNER_TEXT + backend.getUniqueGames().size());
                        break;

                    case 4:
                        String topNGames = formatTopNGames(backend.selectTopNGamesDependingOnPrice(3));
                        showReportDialog(spinnerAdapter.getItem(i).getDisplayText(), SteamGameAppConstants.MOST_EXPENSIVE_GAMES + topNGames);
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

    private void setUpSearchButton() {
        // TODO
        // Implementieren Sie diese Methode.
        Button searchButton = findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchDialog();
            }
        });
    }

    private void setUpAddGameButton() {
        // Implementieren Sie diese Methode.
        Button addButton = findViewById(R.id.addGame);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddGameDialog();
            }
        });
    }

    private void setUpSaveButton() {
        // Implementieren Sie diese Methode.
        Button saveButton = findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOutputStream fileOutputStream = null;
                try {
                     fileOutputStream = openFileOutput(SteamGameAppConstants.SAVE_GAMES_FILENAME, MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                backend.store(fileOutputStream);
            }
        });
    }

    private void showReportDialog(String title, String message) {
        alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();
    }

    private void showSearchDialog() {
        final View vDialog = getLayoutInflater().inflate(R.layout.search_dialog_layout, null);
        new AlertDialog.Builder(this)
                .setView(vDialog)
                .setTitle(SteamGameAppConstants.ENTER_SEARCH_TERM)
                .setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showSearchDialog();
                    }

                })
                .setNegativeButton("Abbrechen", null)
                .show();
    }

    private void showAddGameDialog() {
        final View vDialog = getLayoutInflater().inflate(R.layout.add_dialog_layout, null);
        EditText text = new EditText(this);
        text.setId(R.id.dialog_name_field);

        EditText text2 = new EditText(this);
        text.setId(R.id.dialog_date_field);

        EditText text3 = new EditText(this);
        text.setId(R.id.dialog_price_field);

        new AlertDialog.Builder(this)
                .setView(vDialog)
                .setPositiveButton("ADD GAME", (dialog, which) -> handleAddGameDialog(vDialog))
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void handleAddGameDialog(View vDialog) {
        EditText nameView = vDialog.findViewById(R.id.dialog_name_field);
        EditText dateView = vDialog.findViewById(R.id.dialog_date_field);
        EditText priceView = vDialog.findViewById(R.id.dialog_price_field);

        String name = nameView.getText().toString();
        Date date = null;
        try {
            date = Game.dateFormatter.parse(dateView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double price = Double.valueOf(priceView.getText().toString());

        Game game = new Game(name, date, price);

        backend.addGame(game);
        adapter.notifyDataSetChanged();
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
        return null;
    }
}
