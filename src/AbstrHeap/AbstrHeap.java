package AbstrHeap;

public class AbstrHeap<K extends Comparable<K>> implements IAbstrHeap<K> {

    int arr[];
    int n;
    @Override
    public void vybuduj() {

    }

    @Override
    public void prebuduj(K[] arr) {

    }

    @Override
    public void zrus() {

    }

    @Override
    public boolean jePrazdny() {
        return false;
    }

    @Override
    public void vloz(int data) {
        n += 1;
        arr[n-1] = data;
        fixUp(arr, n-1);
    }

    private void fixUp(int[] arr, int i) {

    }


    @Override
    public K odeberMax() {
        return null;
    }

    @Override
    public K zpristupniMax() {
        return null;
    }

    @Override
    public void vypis() {

    }
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int parent(int i) {
        return (i - 2) / 2;
    }
}
