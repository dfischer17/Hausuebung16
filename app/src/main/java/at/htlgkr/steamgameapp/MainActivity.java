package at.htlgkr.steamgameapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.htlgkr.steam.Game;
import at.htlgkr.steam.ReportType;
import at.htlgkr.steam.SteamBackend;

public class MainActivity extends AppCompatActivity {
    private static final String GAMES_CSV = "games.csv";

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
    }

    private void setUpReportSelection() {
        // Implementieren Sie diese Methode.
    }

    private void setUpSearchButton() {
        // Implementieren Sie diese Methode.
    }

    private void setUpAddGameButton() {
        // Implementieren Sie diese Methode.
    }

    private void setUpSaveButton() {
        // Implementieren Sie diese Methode.
    }
}
