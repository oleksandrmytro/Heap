package list;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private class Prvek implements Serializable {

        Prvek predchozi;
        Prvek dalsi;
        T data;

        public Prvek(Prvek predchozi, Prvek dalsi, T data) {
            this.predchozi = predchozi;
            this.dalsi = dalsi;
            this.data = data;
        }

        public Prvek(T data) {
            this.data = data;
        }
    }

    private Prvek aktualniPrvek = null;
    private Prvek prvniPrvek = null;
    private Prvek posledniPrvek = null;
    private int size = 0;

    private class IteratorImpl implements Iterator<T> {

        private Prvek prvek;

        public IteratorImpl() {
            prvek = prvniPrvek;
        }

        @Override
        public boolean hasNext() {
            return prvek != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = prvek.data;
                prvek = prvek.dalsi;
                return data;
            }
            throw new NoSuchElementException("Seznam is empty!");
        }
    }

    @Override
    public void zrus() {
        prvniPrvek = null;
        aktualniPrvek = null;
        posledniPrvek = null;
        size = 0;
    }

    @Override
    public boolean jePrazdny() {
        return size == 0;
    }

    @Override
    public void vlozPrvni(T data) {
        DataException(data);
        Prvek prvek = new Prvek(data);
        if (jePrazdny()) {
            prvniPrvek = posledniPrvek = prvek;
        } else {
            prvek.dalsi = prvniPrvek;
            prvniPrvek.predchozi = prvek;
            prvniPrvek = prvek;
            prvniPrvek.predchozi = null;
        }
        size++;
    }

    @Override
    public void vlozPosledni(T data) {
        DataException(data);
        Prvek prvek = new Prvek(data);
        if (jePrazdny()) {
            prvniPrvek = posledniPrvek = prvek;
        } else {
            posledniPrvek.dalsi = prvek;
            prvek.predchozi = posledniPrvek;
            posledniPrvek = prvek;
            posledniPrvek.dalsi = null;
        }
        size++;
    }

    @Override
    public void vlozNaslednika(T data) throws ListException {
        DataException(data);
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        if (aktualniPrvek == posledniPrvek) {
            vlozPosledni(data);
        } else {
            Prvek prvek = new Prvek(data);
            prvek.dalsi = aktualniPrvek.dalsi;
            aktualniPrvek.dalsi = prvek;
            prvek.predchozi = aktualniPrvek;
            size++;
        }
    }

    @Override
    public void vlozPredchudce(T data) throws ListException {
        DataException(data);
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        if (aktualniPrvek == prvniPrvek) {
            vlozPrvni(data);
        } else {
            Prvek prvek = new Prvek(data);
            prvek.predchozi = aktualniPrvek.predchozi;
            aktualniPrvek.predchozi = prvek;
            prvek.dalsi = aktualniPrvek;
            size++;
        }
    }

    @Override
    public T zpristupniAktualni() throws ListException {
        if (aktualniPrvek != null) {
            return aktualniPrvek.data;
        }
        throw new ListException("Aktualni prvek neni nastaven");
    }

    @Override
    public T zpristupniPrvni() throws ListException {
        if (!jePrazdny()) {
            aktualniPrvek = prvniPrvek;
            return aktualniPrvek.data;
        }
        throw new ListException("Seznam is empty");
    }

    @Override
    public T zpristupniPosledni() throws ListException {
        if (!jePrazdny()) {
            aktualniPrvek = posledniPrvek;
            return aktualniPrvek.data;
        }
        throw new ListException("Seznam is empty");
    }

    @Override
    public T zpristupniNaslednika() throws ListException {
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        aktualniPrvek = aktualniPrvek.dalsi;
        return aktualniPrvek.data;
    }

    @Override
    public T zpristupniPredchudce() throws ListException {
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        aktualniPrvek = aktualniPrvek.predchozi;
        return aktualniPrvek.data;
    }

    @Override
    public T odeberAktualni() throws ListException {
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        Prvek aktualniData = aktualniPrvek;
        if (prvniPrvek == posledniPrvek) {
            zrus();
            return aktualniData.data;
        } else if (aktualniPrvek == prvniPrvek) {
            odeberPrvni();
            aktualniPrvek = prvniPrvek;
        } else if (aktualniPrvek == posledniPrvek) {
            odeberPosledni();
            aktualniPrvek = prvniPrvek;
        } else {
            aktualniPrvek.predchozi.dalsi = aktualniPrvek.dalsi;
            aktualniPrvek.dalsi.predchozi = aktualniPrvek.predchozi;
            size--;
        }
        return aktualniData.data;
    }

    @Override
    public T odeberPrvni() throws ListException {
        PrazdnyException();
        Prvek prvniData = prvniPrvek;
        if (prvniPrvek == posledniPrvek) {
            zrus();
        } else {
            prvniPrvek = prvniPrvek.dalsi;
            prvniPrvek.predchozi = null;
            size--;
        }
        return prvniData.data;
    }

    @Override
    public T odeberPosledni() throws ListException {
        PrazdnyException();
        if (posledniPrvek != null) {
            Prvek posledniData = posledniPrvek;
            if (prvniPrvek == posledniPrvek) {
                zrus();
            } else {
                posledniPrvek = posledniPrvek.predchozi;
                posledniPrvek.dalsi = null;
                size--;
            }
            return posledniData.data;
        }
        throw new ListException("Posledni prvek neni nastaven");
    }

    @Override
    public T odeberNaslednika() throws ListException {
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        if (aktualniPrvek.dalsi == null) {
            throw new ListException("Aktualni prvek nema naslednika");
        }
        Prvek naslednikData = aktualniPrvek.dalsi;
        aktualniPrvek.dalsi = naslednikData.dalsi;
        naslednikData.dalsi.predchozi = aktualniPrvek;
        size--;
        return naslednikData.data;
    }

    @Override
    public T odeberPredchudce() throws ListException {
        PrazdnyException();
        if (aktualniPrvek == null) {
            throw new ListException("Aktualni prvek neni nastaven");
        }
        if (aktualniPrvek.predchozi == null) {
            throw new ListException("Aktualni prvek nema predchudce");
        }
        Prvek predchudceData = aktualniPrvek.predchozi;
        aktualniPrvek.predchozi = predchudceData.predchozi;
        predchudceData.predchozi.dalsi = aktualniPrvek;
        size--;
        return predchudceData.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new IteratorImpl();
    }

    private static <T> void DataException(T data) {
        if (data == null) {
            throw new NullPointerException("Vlozeni prvek je null");
        }
    }

    private void PrazdnyException() throws ListException {
        if (jePrazdny()) {
            throw new ListException("Seznam is empty");
        }
    }
}
