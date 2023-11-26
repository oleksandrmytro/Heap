package AbstrHeap;

public interface IAbstrHeap<K extends Comparable<K>> {
    void vybuduj();
    void prebuduj(K[] arr);
    void zrus();
    boolean jePrazdny();
    void vloz(K data);
    K odeberMax();
    K zpristupniMax();
    void vypis();
}
