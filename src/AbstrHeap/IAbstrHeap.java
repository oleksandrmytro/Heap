package AbstrHeap;

import enumTypy.eTypProhl;

import java.util.Iterator;

public interface IAbstrHeap<K extends Comparable<K>> {
    void vybuduj();
    void reorganizace(K[] arr);
    void zrus();
    boolean jePrazdny();
    void vloz(K data);
    K odeberMax();
    K zpristupniMax();
    Iterator vypis(eTypProhl typ);
}
