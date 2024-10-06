package sorting;

import java.util.function.Consumer;

public class BinaryInsertionSort {
    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int insertedIndex = binarySearch(array, key, 0, i - 1);
            System.arraycopy(array, insertedIndex, array, insertedIndex + 1, i - insertedIndex);
            array[insertedIndex] = key;
            stepRecorder.accept(array.clone());
            highlightRecorder.accept(new int[] { insertedIndex, i });
        }
    }

    private int binarySearch(int[] array, int key, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (array[mid] == key)
                return mid;
            if (array[mid] < key)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return low;
    }
}
