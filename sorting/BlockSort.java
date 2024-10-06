package sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class BlockSort {

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        int n = array.length;

        if (n <= 1)
            return;

        // Find the maximum and minimum elements in the array
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i] > max)
                max = array[i];
            if (array[i] < min)
                min = array[i];
        }

        // Initialize block count and create blocks
        int blockCount = (int) Math.sqrt(n);
        int range = (max - min + 1) / blockCount;
        int[][] blocks = new int[blockCount][0];

        // Distribute elements into blocks
        for (int i = 0; i < n; i++) {
            int index = (array[i] - min) / range;
            if (index >= blockCount)
                index = blockCount - 1; // Handle edge case
            blocks[index] = append(blocks[index], array[i]);
        }

        // Sort individual blocks and concatenate
        int index = 0;
        for (int i = 0; i < blockCount; i++) {
            if (blocks[i].length > 0) {
                Arrays.sort(blocks[i]);
                for (int value : blocks[i]) {
                    array[index++] = value;
                    stepRecorder.accept(array.clone());
                    highlightRecorder.accept(new int[] { index - 1 });
                }
            }
        }
    }

    private int[] append(int[] array, int value) {
        int[] result = new int[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = value;
        return result;
    }
}
