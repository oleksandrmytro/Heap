package abstrTable;

public class Obec implements Comparable<Obec>{

    private int cisloKraje;
    private int psc;
    private String nazevObce;
    private int pocetMuzu;
    private int pocetZen;
    private int celkem;

    public Obec(int cisloKraje, int psc, String nazevObce, int pocetMuzu, int pocetZen) {
        this.cisloKraje = cisloKraje;
        this.psc = psc;
        this.nazevObce = nazevObce;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = pocetMuzu + pocetZen;
    }

    public Obec(String nazevObce) {
        this.nazevObce = nazevObce;
    }
    
    public int getCisloKraje() {
        return cisloKraje;
    }

    public void setCisloKraje(int cisloKraje) {
        this.cisloKraje = cisloKraje;
    }

    public int getPsc() {
        return psc;
    }

    public void setPsc(int psc) {
        this.psc = psc;
    }

    public String getNazevObce() {
        return nazevObce;
    }

    public void setNazevObce(String nazevObce) {
        this.nazevObce = nazevObce;
    }

    public int getPocetMuzu() {
        return pocetMuzu;
    }

    public void setPocetMuzu(int pocetMuzu) {
        this.pocetMuzu = pocetMuzu;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public void setPocetZen(int pocetZen) {
        this.pocetZen = pocetZen;
    }

    public int getCelkem() {
        return celkem;
    }

    public void setCelkem(int celkem) {
        this.celkem = celkem;
    }

    @Override
    public int compareTo(Obec o) {
        return nazevObce.compareTo(o.nazevObce); // nazevObce
    }

    @Override
    public String toString() {
        return nazevObce + ", cislo: " + cisloKraje + ", psc: " + psc + ", muzi: " + pocetMuzu + ", zeny: " + pocetZen + ", celkem: " + celkem;
    }
    
    
}
