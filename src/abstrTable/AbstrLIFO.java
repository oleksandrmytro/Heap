package abstrTable;

import java.util.Iterator;
import list.AbstrDoubleList;
import list.IAbstrDoubleList;
import list.ListException;

public class AbstrLIFO<T> {

    IAbstrDoubleList<T> list = new AbstrDoubleList<>();

    public void zrus() {
        list.zrus();
    }

    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    public void vloz(T data) {
        list.vlozPosledni(data);
    }

    public T odeber() {
        try {
            return list.odeberPosledni();
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    public Iterator<T> vytvorIterator() {
        return list.iterator();
    }
}
