package AbstrHeap;

import abstrTable.AbstrLIFO;
import abstrTable.Obec;
import enumTypy.ePorovnani;
import enumTypy.eTypProhl;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrHeap<K extends Comparable<K>> implements IAbstrHeap<K> {

    private static class Arr<K> {
        K elem;

        public Arr(K elem) {
            this.elem = elem;
        }
    }

    private Arr<K>[] array;
    private int capacity;
    private int currentHeapSize;

    private void swap(Arr<K>[] arr, int a, int b) {
        Arr<K> temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public AbstrHeap(int capacity) {
        array = new Arr[capacity];
        this.capacity = capacity;
        this.currentHeapSize = 0;
    }

    @Override
    public void vybuduj() {
        for (int j = currentHeapSize / 2; j > 0; j--) {
            heapifyDown(j, currentHeapSize);
        }
    }

    private void heapifyDown(int index, int size) {
        int leftChild = leftChild(index);
        int rightChild = rightChild(index);
        int smallest = index;

        if (leftChild < size && array[leftChild].elem.compareTo(array[index].elem) < 0) {
            smallest = leftChild;
        }
        if (rightChild < size && array[rightChild].elem.compareTo(array[smallest].elem) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(array, index, smallest);
            heapifyDown(smallest, size);
        }
    }



    @Override
    public void reorganizace(ePorovnani porovnani) {
        Obec.setAktualniPorovnani(porovnani);
        vybuduj();
    }

    @Override
    public void zrus() {
        this.capacity = 0;
        this.array = null;
    }

    @Override
    public boolean jePrazdny() {
        return array == null;
    }

    @Override
    public void vloz(K data) {
        if (currentHeapSize == capacity) {
            // Розширити масив, якщо потрібно
            expandHeap();
        }
        // Вставка нового елемента на кінець купи
        int currentIndex = currentHeapSize++;
        array[currentIndex] = new Arr<>(data);

        // Відновлення властивостей купи
        while (currentIndex != 0 && array[currentIndex].elem.compareTo(array[parent(currentIndex)].elem) < 0) {
            swap(array, currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }

    private void expandHeap() {
        Arr[] newArray = new Arr[capacity * 2];
        System.arraycopy(array, 0, newArray, 0, capacity);
        array = newArray;
        capacity *= 2;
    }


//    private void fixUp(int curr) {
//        /*
//        arr[curr].ell.compareTo(arr[parent(curr)) < 0 перевіряє чи поточний елемент
//        менший за елемент у батьківському вузлі. Тобто воно буде міняти місцями новий елемент
//        доті до коли не знайде своє місце(батьківський буде менший за новий)
//        */
//        while(curr > 1 && array[curr].elem.compareTo(array[parent(curr)].elem) < 0) {
//            Arr<K> temp = array[curr];
//            array[curr] = array[parent(curr)];
//            array[parent(curr)] = temp;
//
//            curr = parent(curr);
//        }
//    }


    @Override
    public K odeberMax() {
        if (jePrazdny()) throw new NoSuchElementException("Heap je prazdny");
        /*
        список розділяється на дві частини де ліва це батьківські
        а права дочірні тому ми ділим список n / 2
        а + 1 це для того щоб перейти до першого дочірного елемента
        */

        int firstLeafIndex = firstLeafIndex(capacity);
        K maxElement = array[firstLeafIndex].elem;
        int maxElementIndex = firstLeafIndex;

        // Проходимось по листам
        for (int i = firstLeafIndex; i <= capacity; i++) {
            if (array[i].elem.compareTo(maxElement) > 0) {
                maxElement = array[i].elem;
                maxElementIndex = i;
            }
        }

        // Видалення елемента
        Arr<K> temp = array[maxElementIndex];
        array[maxElementIndex] = array[capacity];
        array[capacity] = null;
        capacity--;

        if (maxElementIndex <= capacity) {
            heapifyDown(maxElementIndex, capacity);
        }
        return temp.elem;
    }

    @Override
    public K zpristupniMax() {
        if (jePrazdny()) throw new NoSuchElementException("Heap je prazdny");

        int firstLeafIndex = firstLeafIndex(capacity);
        K maxElement = array[firstLeafIndex].elem;

        for (int i = firstLeafIndex; i <= capacity; i++) {
            if (array[i].elem.compareTo(maxElement) > 0) {
                maxElement = array[i].elem;
            }
        }
        return maxElement;
    }

    @Override
    public Iterator<K> vypis(eTypProhl typ) {
        switch (typ) {
            case SIRKA -> {
                return new Iterator<>() {
                    int i = 0;

                    @Override
                    public boolean hasNext() {
                        return i < currentHeapSize;
                    }

                    @Override
                    public K next() {
                        if (!hasNext()) throw new NoSuchElementException();
                        return array[i++].elem;
                    }
                };
            }


            case HLOUBKA -> {
                return new Iterator<>() {
                    final AbstrLIFO<Integer> lifo = new AbstrLIFO<>();
                    boolean initialized = false;

                    @Override
                    public boolean hasNext() {
                        if (!initialized) {
                            initialize();
                            initialized = true;
                        }
                        return !lifo.jePrazdny();
                    }

                    private void initialize() {
                        int index = 0;
                        while (index < currentHeapSize) {
                            lifo.vloz(index);
                            index = leftChild(index);
                        }
                    }

                    @Override
                    public K next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }

                        int index = lifo.odeber();
                        K result = array[index].elem;

                        int rightIndex = rightChild(index);
                        if (rightIndex < currentHeapSize) {
                            int nextIndex = rightIndex;
                            while (nextIndex < currentHeapSize) {
                                lifo.vloz(nextIndex);
                                nextIndex = leftChild(nextIndex);
                            }
                        }

                        return result;
                    }
                };
            }

        }
        return null;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int firstLeafIndex(int n) {
        return n / 2;
    }


}
