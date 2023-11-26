package AbstrHeap;

import java.util.NoSuchElementException;

public class AbstrHeap<K extends Comparable<K>> implements IAbstrHeap<K> {

    private static class Arr<K> {
        K elem;

        public Arr(K elem) {
            this.elem = elem;
        }
    }

    private Arr<K>[] arr;
    private int n;
    @Override
    public void vybuduj() {
        for (int j = n / 2; j > 0; j--) {
            heapifyDown(j, n);
        }
    }

    private void heapifyDown(int index, int size) {
        K temp = arr[index].elem;
        int child;

        while (index * 2 <= size) { // перевіряє чи існує лівий дочірний вузол`
            child = getMinChild(index, size);
            // перевіряє чи дочірний вузол менший за поточний
            if (arr[child].elem.compareTo(temp) < 0) {
                arr[index] = arr[child];
                index = child;
            } else {
                break;
            }
        }
        arr[index] = new Arr<>(temp);
    }

    private int getMinChild(int index, int size) {
        int leftChild = leftChild(index);
        int rightChild = leftChild + 1;
        /*
        перевірка на те чи правий дочірній вузол існує(rightChild <= size)
        і чи він менший за лівий arr[rightChild].ell.compareTo(arr[leftChild].ell) < 0
        якщо так то повертає правий, якщо ні то лівий
        */
        if (rightChild <= size && arr[rightChild].elem.
                compareTo(arr[leftChild].elem) < 0) {
            return rightChild;
        }
        return leftChild;
    }

    @Override
    public void prebuduj(K[] array) {
        n = array.length;
        arr = new Arr[n + 1];
        for(int i = 0; i < n; i++) {
            arr[i + 1] = new Arr<>(array[i]);
        }
        for(int i = n / 2; i > 0; i--) {
            heapifyDown(i, n);
        }
    }

    @Override
    public void zrus() {
        this.n = 0;
        this.arr = null;
    }

    @Override
    public boolean jePrazdny() {
        return arr == null;
    }

    @Override
    public void vloz(K data) {
        // перевіряємо чи існує купа, якщо ні то створюємо нову
        if (arr == null) {
            arr = new Arr[10];
            n = 0;
        }
        // перевіряємо чи достатньо місця, якщо ні то збільшуєм
        if (n + 1 == arr.length) {
            Arr<K>[] newArr = new Arr[arr.length * 2];
            System.arraycopy(arr, 0, newArr, 0, arr.length);
            arr = newArr;
        }
        // новий елемент в кінець купи
        arr[n++] = new Arr<>(data);

        fixUp(n);

    }

    private void fixUp(int curr) {
        /*
        arr[curr].ell.compareTo(arr[parent(curr)) < 0 перевіряє чи поточний елемент
        менший за елемент у батьківському вузлі. Тобто воно буде міняти місцями новий елемент
        доті до коли не знайде своє місце(батьківський буде менший за новий)
        */
        while(curr > 1 && arr[curr].elem.compareTo(arr[parent(curr)].elem) < 0) {
            Arr<K> temp = arr[curr];
            arr[curr] = arr[parent(curr)];
            arr[parent(curr)] = temp;

            curr = parent(curr);
        }
    }


    @Override
    public K odeberMax() {
        if (jePrazdny()) throw new NoSuchElementException("Heap je prazdny");
        /*
        список розділяється на дві частини де ліва це батьківські
        а права дочірні тому ми ділим список n / 2
        а + 1 це для того щоб перейти до першого дочірного елемента
        */

        int firstLeafIndex = firstLeafIndex(n);
        K maxElement = arr[firstLeafIndex].elem;
        int maxElementIndex = firstLeafIndex;

        // Проходимось по листам
        for (int i = firstLeafIndex; i <= n; i++) {
            if (arr[i].elem.compareTo(maxElement) > 0) {
                maxElement = arr[i].elem;
                maxElementIndex = i;
            }
        }

        // Видалення елемента
        Arr<K> temp = arr[maxElementIndex];
        arr[maxElementIndex] = arr[n];
        arr[n] = null;
        n--;

        if (maxElementIndex <= n) {
            heapifyDown(maxElementIndex, n);
        }
        return temp.elem;
    }

    @Override
    public K zpristupniMax() {
        if (jePrazdny()) throw new NoSuchElementException("Heap je prazdny");

        int firstLeafIndex = firstLeafIndex(n);
        K maxElement = arr[firstLeafIndex].elem;

        for (int i = firstLeafIndex; i <= n; i++) {
            if (arr[i].elem.compareTo(maxElement) > 0) {
                maxElement = arr[i].elem;
            }
        }
        return maxElement;
    }

    @Override
    public void vypis() {

    }
    private int leftChild(int i) {
        return 2 * i;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int firstLeafIndex(int n) {
        return n / 2 + 1;
    }


}
