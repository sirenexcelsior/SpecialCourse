import java.util.Comparator;

public class QuickSort {

    public static <T> void sort(T[] array, Comparator<T> comparator) {
        quickSort(array, 0, array.length - 1, comparator);
    }

    private static <T> void quickSort(T[] array, int low, int high, Comparator<T> comparator) {
        while (low < high) {
            if (high - low < 10) { // Use insertion sort for small arrays
                insertionSort(array, low, high, comparator);
                break;
            }

            int[] pivotIndices = threeWayPartition(array, low, high, comparator);
            quickSort(array, low, pivotIndices[0] - 1, comparator); // Sort left part
            low = pivotIndices[1] + 1; // Sort right part iteratively
        }
    }

    private static <T> int[] threeWayPartition(T[] array, int low, int high, Comparator<T> comparator) {
        T pivot = medianOfThree(array, low, high, comparator);
        int i = low, j = high, k = low;

        while (k <= j) {
            int cmp = comparator.compare(array[k], pivot);
            if (cmp < 0) {
                swap(array, k++, i++);
            } else if (cmp > 0) {
                swap(array, k, j--);
            } else {
                k++;
            }
        }
        return new int[]{i, j};
    }

    private static <T> T medianOfThree(T[] array, int low, int high, Comparator<T> comparator) {
        int mid = low + (high - low) / 2;
        if (comparator.compare(array[mid], array[low]) < 0) swap(array, mid, low);
        if (comparator.compare(array[high], array[low]) < 0) swap(array, high, low);
        if (comparator.compare(array[high], array[mid]) < 0) swap(array, high, mid);
        return array[mid];
    }

    private static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static <T> void insertionSort(T[] array, int low, int high, Comparator<T> comparator) {
        for (int i = low + 1; i <= high; i++) {
            T key = array[i];
            int j = i - 1;
            while (j >= low && comparator.compare(array[j], key) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}
