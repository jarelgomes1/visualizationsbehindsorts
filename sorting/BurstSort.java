package sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class BurstSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        burstSort(array, array.length);
    }

    private void burstSort(int[] array, int n) {
        int max = Arrays.stream(array).max().orElse(Integer.MAX_VALUE);
        int[] counts = new int[max + 1];

        for (int i = 0; i < n; i++) {
            counts[array[i]]++;
        }

        int index = 0;
        for (int i = 0; i <= max; i++) {
            while (counts[i] > 0) {
                highlightRecorder.accept(new int[] { index });
                array[index++] = i;
                counts[i]--;
                recordStep(array);
            }
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
