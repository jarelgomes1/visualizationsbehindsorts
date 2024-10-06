package sorting;

import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class PostmanSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        int max = findMax(array);
        int[][] bins = new int[max + 1][array.length];
        int[] count = new int[max + 1];

        for (int i = 0; i < array.length; i++) {
            bins[array[i]][count[array[i]]++] = array[i];
            highlightRecorder.accept(new int[] { i });
        }

        int index = 0;
        for (int i = 0; i <= max; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[index++] = bins[i][j];
                recordStep(array);
            }
        }
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int num : array) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
