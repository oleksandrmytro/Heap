package AgendaKraj;

import AbstrHeap.AbstrHeap;
import abstrTable.AbstrTable;
import abstrTable.Obec;
import enumTypy.ePorovnani;
import enumTypy.eTypProhl;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import nacteniAulozeni.UlozeniAnacteni;

public class Agenda implements IAgenda {

    private AbstrTable<Obec, Obec> at = new AbstrTable<>();
    private AbstrHeap<Obec> ah = new AbstrHeap<Obec>();
    private Random random = new Random();
    
    @Override
    public void importDat() {
        try {
            UlozeniAnacteni.nacti("obce.txt", this, eTypProhl.SIRKA);
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    @Override
    public Obec najdi(String key) {
        return at.najdi(new Obec(key));
    }

    @Override
    public void vloz(Obec obec) {
        ah.vloz(obec);
    }
    @Override
    public Obec odeber(String key) {
        return at.odeber(new Obec(key));
    }

    @Override
    public Obec odeberMax() {
        return null;
    }

    @Override
    public void reoraginace(Obec[] arr) {
        ah.reorganizace(arr);
    }


    @Override
    public Iterator<Obec> vytvorIterator(eTypProhl typ) {
        return ah.vypis(typ);
    }

    @Override
    public Obec generuj() {
        int cisloKraje = random.nextInt(1000 - 1 + 1) + 1;
        int psc = random.nextInt(99999 - 10000 + 1) + 10000;
        String nazev = "Obec" + random.nextInt(1000) + 1;
        int pocetMuzu = random.nextInt(100000 - 0 + 1);
        int pocetZen = random.nextInt(100000 - 0 + 1);
        return new Obec(cisloKraje, psc, nazev, pocetMuzu, pocetZen);
    }
    
    public void zrus() {
        at.zrus();
    }
    
}
