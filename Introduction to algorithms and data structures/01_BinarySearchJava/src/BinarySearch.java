import java.util.Comparator;

public class BinarySearch {
    public static <T> int find(T[] array, Comparator<T> comparator, T element) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            T midVal = array[mid];
            int cmp = comparator.compare(midVal, element);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // element found
            }
        }
        return -1; // element not found
    }
}
