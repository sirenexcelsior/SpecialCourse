// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {

        // О примерах использования целых чисел

        Integer[] numbers = {1, 3, 5, 7, 9, 11, 13, 15};

        Comparator<Integer> integerComparator = Comparator.naturalOrder();

        int index = BinarySearch.find(numbers, integerComparator, 1);

        System.out.println("Index of 9: " + index);

        // О примерах использования строк.

        String[] words = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"};

        Comparator<String> stringComparator = Comparator.naturalOrder();

        int indexStr = BinarySearch.find(words, stringComparator, "date");

        System.out.println("Index of 'date': " + indexStr);
    }
}
