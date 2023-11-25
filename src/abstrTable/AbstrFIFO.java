package abstrTable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import list.AbstrDoubleList;
import list.IAbstrDoubleList;
import list.ListException;

public class AbstrFIFO<T> {
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
        if (jePrazdny()) {
            throw new NoSuchElementException("Cannot remove an element from an empty LIFO.");
        }
        try {
            return list.odeberPrvni();
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }
    
    public Iterator<T> vytvorIterator() {
        return list.iterator();
    }
}
