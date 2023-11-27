package AbstrHeap;

import enumTypy.ePorovnani;
import enumTypy.eTypProhl;

import java.util.Iterator;

public interface IAbstrHeap<K extends Comparable<K>> {
    void vybuduj(K[] array);
    void reorganizace();
    void zrus();
    boolean jePrazdny();
    void vloz(K data);
    K odeberMax();
    K zpristupniMax();
    Iterator vypis(eTypProhl typ);
}
