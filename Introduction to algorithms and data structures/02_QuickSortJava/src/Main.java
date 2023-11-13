import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        testSortWithRandomArray(5);   // Test sorting with an array of 5 random numbers
        testSortWithRandomArray(50);  // Test sorting with an array of 50 random numbers
    }

    private static void testSortWithRandomArray(int arraySize) {
        Integer[] originalArray = new Integer[arraySize];
        Random random = new Random();
        for (int i = 0; i < originalArray.length; i++) {
            originalArray[i] = random.nextInt(100); // Random integers between 0 and 99
        }

        // Sorting both arrays
        Integer[] arrayForCustomSort = Arrays.copyOf(originalArray, originalArray.length);
        Integer[] arrayForStandardSort = Arrays.copyOf(originalArray, originalArray.length);

        CountingComparator<Integer> countingComparator = new CountingComparator<Integer>(Comparator.naturalOrder());

        // Custom QuickSort
        QuickSort.sort(arrayForCustomSort, countingComparator);
        System.out.println("Array size: " + arraySize);
        System.out.println("Custom QuickSort comparison count: " + countingComparator.getCount());
        System.out.println("Custom QuickSort sorted array: " + Arrays.toString(arrayForCustomSort));

        // Resetting the counter
        countingComparator.resetCount();

        // Standard sort
        Arrays.sort(arrayForStandardSort, countingComparator);
        System.out.println("Standard sort comparison count: " + countingComparator.getCount());
        System.out.println("Standard sort sorted array: " + Arrays.toString(arrayForStandardSort));

        // Verify if the sorting results are consistent
        System.out.println("Are sorting results consistent: " + Arrays.equals(arrayForCustomSort, arrayForStandardSort));
        System.out.println("-------------------------------------");
    }
}
