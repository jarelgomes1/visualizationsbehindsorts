package sorting;

import java.util.function.Consumer;

public class RadixSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int max = getMax(array);
        for (int exp = 1; max / exp > 0; exp *= 10)
            countSort(array, exp);
    }

    private void countSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];
        for (int i = 0; i < n; i++)
            count[(array[i] / exp) % 10]++;
        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];
        for (int i = n - 1; i >= 0; i--) {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            highlightRecorder.accept(new int[] { i });
            count[(array[i] / exp) % 10]--;
        }
        System.arraycopy(output, 0, array, 0, n);
        recordStep(array);
    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int i : array)
            if (i > max)
                max = i;
        return max;
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
