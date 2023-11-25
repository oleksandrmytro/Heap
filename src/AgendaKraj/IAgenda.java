package AgendaKraj;

import abstrTable.Obec;
import abstrTable.eTypProhl;
import java.util.Iterator;

public interface IAgenda {
    void importDat();
    Obec najdi(String key);
    void vloz(Obec obec);
    Obec odeber(String key);
    Iterator<Obec> vytvorIterator(eTypProhl typ);
    Obec generuj();
}
