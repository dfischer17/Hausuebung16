package at.htlgkr.steamgameapp;

import at.htlgkr.steam.ReportType;

public class ReportTypeSpinnerItem {
    private ReportType type;
    private String displayText;

    public ReportTypeSpinnerItem(ReportType type, String displayText) {
        // Implementieren Sie diesen Konstruktor
        this.type = type;
        this.displayText = displayText;
    }

    public ReportType getType(){
        // Implementieren Sie diese Methode.
        return type;
    }

    public String getDisplayText() {
        // Implementieren Sie diese Methode.
        return displayText;
    }

    @Override
    public String toString() {
        // Implementieren Sie diese Methode.
        return displayText;
    }
}