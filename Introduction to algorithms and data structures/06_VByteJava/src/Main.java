import java.util.ArrayList;
import java.util.List;

class VByteCompression {

    public static int[] encode(int[] array) {
        List<Integer> encodedList = new ArrayList<>();
        for (int number : array) {
            while (number >= 128) {
                encodedList.add(number & 0x7F);
                number >>= 7;
            }
            encodedList.add(number | 0x80);
        }
        return encodedList.stream().mapToInt(i -> i).toArray();
    }

    public static int[] decode(int[] array) {
        List<Integer> decodedList = new ArrayList<>();
        int currentNumber = 0;
        int shift = 0;

        for (int encoded : array) {
            if ((encoded & 0x80) != 0) {
                currentNumber |= (encoded & 0x7F) << shift;
                decodedList.add(currentNumber);
                currentNumber = 0;
                shift = 0;
            } else {
                currentNumber |= (encoded & 0x7F) << shift;
                shift += 7;
            }
        }

        return decodedList.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] original = new int[]{120, 150, 305, 5000};
        int[] encoded = encode(original);
        int[] decoded = decode(encoded);

        System.out.println("Original: ");
        for (int num : original) {
            System.out.print(num + " ");
        }

        System.out.println("\nEncoded: ");
        for (int num : encoded) {
            System.out.print(num + " ");
        }

        System.out.println("\nDecoded: ");
        for (int num : decoded) {
            System.out.print(num + " ");
        }
    }
}
