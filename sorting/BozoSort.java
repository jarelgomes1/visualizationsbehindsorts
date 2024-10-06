package sorting;

import java.util.Random;
import java.util.function.Consumer;

public class BozoSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        Random random = new Random();

        while (!isSorted(array)) {
            int i = random.nextInt(array.length);
            int j = random.nextInt(array.length);
            if (i != j) { // Ensure i and j are different to avoid redundant swaps
                highlightRecorder.accept(new int[] { i, j });
                swap(array, i, j);
                recordStep(array);
            }
        }
    }

    private boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            highlightRecorder.accept(new int[] { i, i + 1 });
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
