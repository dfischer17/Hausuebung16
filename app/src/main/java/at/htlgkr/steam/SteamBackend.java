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
import java.util.stream.DoubleStream;

public class SteamBackend {
    private final String fileName = "game.csv";
    private List<Game> gameList = new ArrayList<>();

    public SteamBackend() {
        // Implementieren Sie diesen Konstruktor.
    }

    public void loadGames(InputStream inputStream) {
        // Implementieren Sie diese Methode.
        // Diese methode lädt alle Games in eine Variable welche sich im Steam Backend befinden muss.
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuffer buffer = new StringBuffer();

            // Tabellenueberschriften ueberspringen
            boolean read = false;

            while ((line = in.readLine()) != null) {
                if (read == false) {
                    read = true;
                    continue;
                }
                String[] temp = line.split(";");
                String name = temp[0];
                Date date = Game.dateFormatter.parse(temp[1]);
                double price = Double.valueOf(temp[2]);

                gameList.add(new Game(name, date, price));
            }
            in.close();
        } catch (IOException exp) {
            Log.d("mastermind", exp.getStackTrace().toString());
        } catch (ParseException exp) {
            Log.d("mastermind", exp.getStackTrace().toString());
        }
    }

    public void store(OutputStream fileOutputStream) {
        // Implementieren Sie diese Methode.
        // Diese methode schreibt alle Games in den fileOutputStream.
        String output = "";
        StringBuilder outputBuilder = new StringBuilder(output);

        // Jedes Game mit toCsvString in File speichern
        for (Game curGame : gameList) {
            String csvGame = curGame.toCsvString() + "\n";
            outputBuilder.append(csvGame);
        }

        output = outputBuilder.toString();

        PrintWriter out = new PrintWriter(new OutputStreamWriter(fileOutputStream));
        out.print(output);
        out.flush();
        out.close();
    }

    public List<Game> getGames() {
        // Implementieren Sie diese Methode.
        return gameList;
    }

    public void setGames(List<Game> games) {
        // Implementieren Sie diese Methode.
        gameList.clear();
        this.gameList = games;
    }

    public void addGame(Game newGame) {
        // Implementieren Sie diese Methode
        gameList.add(newGame);
    }

    public double sumGamePrices() {
        // Implementieren Sie diese Methode mit Streams.
        return gameList.stream().mapToDouble(Game::getPrice).sum();
    }

    public double averageGamePrice() {
        // Implementieren Sie diese Methode mit Streams.
        return gameList.stream().mapToDouble(Game::getPrice).average().orElse(0);
    }

    public List<Game> getUniqueGames() {
        // Implementieren Sie diese Methode mit Streams.
        return gameList.stream().distinct().collect(Collectors.toList());
    }

    public List<Game> selectTopNGamesDependingOnPrice(int n) {
        // Implementieren Sie diese Methode mit Streams.
        return gameList.stream().sorted((game, game2) -> (int) (game2.getPrice() - game.getPrice())).limit(n).collect(Collectors.toList());
    }
}
