package at.htlgkr.steam;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Game {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    private static final DecimalFormat priceFormatter = new DecimalFormat("0.0#");

    private String name;
    private Date releaseDate;
    private double price;

    public Game() {
        // dieser Konstruktor muss existieren
    }

    public Game(String name, Date releaseDate, double price) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Implementieren Sie diese Methode
        this.name = name;
    }

    public Date getReleaseDate() {
        // Implementieren Sie diese Methode
        return releaseDate;
    }

    public String getFormattedReleaseDate() {
        return dateFormatter.format(releaseDate);
    }

    public void setReleaseDate(Date releaseDate) {
        // Implementieren Sie diese Methode
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        // Implementieren Sie diese Methode
        return price;
    }

    public void setPrice(double price) {
        // Implementieren Sie diese Methode
        this.price = price;
    }

    @Override
    public String toString() {
        // Implementieren Sie diese Methode
        String formattedReleaseDate = dateFormatter.format(releaseDate);
        return "[" + formattedReleaseDate + "] " + name + " " + price;
    }

    public String toCsvString() {
        // Implementieren Sie diese Methode
        String formattedReleaseDate = dateFormatter.format(releaseDate);
        String formattedPrice = priceFormatter.format(price);
        return name + ";" + formattedReleaseDate + ";" + formattedPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return name.equals(game.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

