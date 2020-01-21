package at.htlgkr.steam;

import java.util.Date;

public class Game {
    public static final String DATE_FORMAT = "dd.MM.yyyy";

    private String name;
    private Date releaseDate;
    private double price;

    public Game() {
        // dieser Konstruktor muss existieren
    }

    public String getName() {
        // Implementieren Sie diese Methode
        return null;
    }

    public void setName(String name) {
        // Implementieren Sie diese Methode
    }

    public Date getReleaseDate() {
        // Implementieren Sie diese Methode
        return null;
    }

    public void setReleaseDate(Date releaseDate) {
        // Implementieren Sie diese Methode
    }

    public double getPrice() {
        // Implementieren Sie diese Methode
        return -1;
    }

    public void setPrice(double price) {
        // Implementieren Sie diese Methode
    }

    @Override
    public String toString() {
        // Implementieren Sie diese Methode
        return null;
    }

    @Override
    public boolean equals(Object o) {
        // Implementieren Sie diese Methode
        return false;
    }

    @Override
    public int hashCode() {
        // Implementieren Sie diese Methode
        return -1;
    }
}

