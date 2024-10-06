package sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AmericanFlagSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        americanFlagSort(array, array.length);
    }

    private void americanFlagSort(int[] array, int n) {
        int[] count = new int[10];
        int[] offset = new int[10];

        int max = findMax(array);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(array, n, exp, count, offset);
        }
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void countSort(int[] array, int n, int exp, int[] count, int[] offset) {
        int[] output = new int[n];
        for (int i = 0; i < n; i++) {
            count[(array[i] / exp) % 10]++;
        }
        offset[0] = 0;
        for (int i = 1; i < 10; i++) {
            offset[i] = offset[i - 1] + count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[offset[digit] + count[digit] - 1] = array[i];
            count[digit]--;
        }
        for (int i = 0; i < n; i++) {
            highlightRecorder.accept(new int[] { i });
            array[i] = output[i];
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
