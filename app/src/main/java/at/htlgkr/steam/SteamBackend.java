package at.htlgkr.steam;


import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SteamBackend {
    public SteamBackend() {
        // Implementieren Sie diesen Konstruktor.
    }

    public void loadGames(InputStream inputStream) {
        // Implementieren Sie diese Methode.
        // Diese methode lädt alle Games in eine Variable welche sich im Steam Backend befinden muss.
    }

    public void store(OutputStream fileOutputStream) {
        // Implementieren Sie diese Methode.
        // Diese methode schreibt alle Games in den fileOutputStream.
    }

    public List<Game> getGames() {
        // Implementieren Sie diese Methode.
        return null;
    }

    public void setGames(List<Game> games) {
        // Implementieren Sie diese Methode.
    }

    public void addGame(Game newGame) {
        // Implementieren Sie diese Methode
    }

    public double sumGamePrices() {
        // Implementieren Sie diese Methode mit Streams.
        return -1;
    }

    public double averageGamePrice() {
        // Implementieren Sie diese Methode mit Streams.
        return -1;
    }

    public List<Game> getUniqueGames() {
        // Implementieren Sie diese Methode mit Streams.
        return null;
    }

    public List<Game> selectTopNGamesDependingOnPrice(int n) {
        // Implementieren Sie diese Methode mit Streams.
        return null;
    }
}
