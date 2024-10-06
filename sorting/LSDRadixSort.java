package sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class LSDRadixSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        lsdRadixSort(array, array.length);
    }

    private void lsdRadixSort(int[] array, int n) {
        int max = Arrays.stream(array).max().orElse(Integer.MAX_VALUE);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(array, n, exp);
        }
    }

    private void countSort(int[] array, int n, int exp) {
        int[] output = new int[n];
        int[] count = new int[10];

        for (int i = 0; i < n; i++) {
            count[(array[i] / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            int index = (array[i] / exp) % 10;
            highlightRecorder.accept(new int[] { count[index] - 1 });
            output[--count[index]] = array[i];
            recordStep(output);
        }

        for (int i = 0; i < n; i++) {
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
