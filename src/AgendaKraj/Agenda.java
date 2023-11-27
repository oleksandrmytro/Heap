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

    private final AbstrTable<Obec, Obec> at = new AbstrTable<>();
    private final AbstrHeap<Obec> ah = new AbstrHeap<Obec>(10);
    private final Random random = new Random();
    
    @Override
    public void importDat() {
        try {
            UlozeniAnacteni.nacti("obce.txt", this, eTypProhl.SIRKA);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
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
        return ah.odeberMax();
    }

    public Obec zpristupniMax() {
        return ah.zpristupniMax();
    }

    @Override
    public void reorganizace() {
        ah.reorganizace();
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
        int pocetMuzu = random.nextInt(100000 + 1);
        int pocetZen = random.nextInt(100000 + 1);
        return new Obec(cisloKraje, psc, nazev, pocetMuzu, pocetZen);
    }
    
    public void zrus() {
        ah.zrus();
    }
    
}
