package it.polimi.ingsw.model;

public enum TokenPoints {
    DUE(2),
    QUATTRO(4),
    SEI(6),
    OTTO(8);

    private final int valore;

    TokenPoints(int valore) {
        this.valore = valore;
    }

    public int getValore() {
        return valore;
    }
}
