package AbstrHeap;

import abstrTable.AbstrLIFO;
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
        this.array = new Arr[capacity];
        this.capacity = capacity;
        this.currentHeapSize = 0;
    }


    @Override
    public void vybuduj(K[] array) {
        this.array = new Arr[array.length]; // Створення нового масиву
        for (int i = 0; i < array.length; i++) {
            this.array[i] = new Arr<>(array[i]);
        }
        this.currentHeapSize = array.length;
        for (int j = currentHeapSize / 2; j >= 0; j--) {
            heapifyDown(j, currentHeapSize);
        }
    }



    private void heapifyDown(int index, int size) {
        int leftChild = leftChild(index);
        int rightChild = rightChild(index);
        int largest = index;

        if (leftChild < size && array[leftChild].elem.compareTo(array[index].elem) > 0) {
            largest = leftChild;
        }
        if (rightChild < size && array[rightChild].elem.compareTo(array[largest].elem) > 0) {
            largest = rightChild;
        }

        if (largest != index) {
            swap(array, index, largest);
            heapifyDown(largest, size);
        }
    }



    @Override
    public void reorganizace() {
        K[] tempArray = (K[]) new Comparable[currentHeapSize];
        for (int i = 0; i < currentHeapSize; i++) {
            tempArray[i] = array[i].elem;
        }
        vybuduj(tempArray);
    }

    @Override
    public void zrus() {
        this.currentHeapSize = 0;
        // array залишається ініціалізованим, але вважається порожнім
    }


    @Override
    public boolean jePrazdny() {
        return array == null;
    }

    @Override
    public void vloz(K data) {
        if (array == null) {
            array = new Arr[10];
            capacity = 10;
        }

        if (jePrazdny()) {
            array[0] = new Arr<>(data); // Індексація починається з 0
            currentHeapSize = 1;
        } else {
            if (currentHeapSize == array.length) {
                expandHeap(); // Розширюємо купу, якщо потрібно
            }
            array[currentHeapSize] = new Arr<>(data);
            currentHeapSize++;

            // Просіювання нового елементу вгору
            int index = currentHeapSize - 1;
            while (index > 0 && array[parent(index)].elem.compareTo(array[index].elem) < 0) {
                swap(array, index, parent(index));
                index = parent(index);
            }
        }
    }

    private void expandHeap() {
        Arr<K>[] newArray = new Arr[capacity * 2]; // Подвоєння розміру масиву
        System.arraycopy(array, 0, newArray, 0, currentHeapSize); // Копіювання старого масиву в новий
        array = newArray;
        capacity = newArray.length;
    }


    @Override
    public K odeberMax() {
        if (jePrazdny()) {
            throw new NoSuchElementException("Heap je prazdny");
        }
        // Зберігаємо максимальний елемент
        K maxElement = array[0].elem;

        // Заміна кореневого елемента останнім елементом у купі
        array[0] = array[currentHeapSize - 1];
        currentHeapSize--;

        // Просіювання нового кореневого елемента вниз
        heapifyDown(0, currentHeapSize);

        return maxElement;
    }

    @Override
    public K zpristupniMax() {
        if (jePrazdny()) {
            throw new NoSuchElementException("Heap je prazdny");
        }
        return array[0].elem;
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
