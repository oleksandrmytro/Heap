package AgendaKraj;

import abstrTable.Obec;
import enumTypy.ePorovnani;
import enumTypy.eTypProhl;
import java.util.Iterator;

public interface IAgenda {
    void importDat();
    Obec najdi(String key);
    void vloz(Obec obec);
    Obec odeber(String key);
    Obec odeberMax();
    void reorganizace();
    Obec zpristupniMax();
    Iterator<Obec> vytvorIterator(eTypProhl typ);
    Obec generuj();
}
