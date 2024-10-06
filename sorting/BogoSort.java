package sorting;

import java.util.Random;
import java.util.function.Consumer;

public class BogoSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        // Continue shuffling until the array is sorted
        while (!isSorted(array)) {
            shuffle(array);
            recordStep(array);
        }
    }

    // Check if the array is sorted
    private boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            highlightRecorder.accept(new int[] { i, i + 1 });
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // Shuffle the array randomly
    private void shuffle(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndex = random.nextInt(array.length);
            highlightRecorder.accept(new int[] { i, randomIndex });
            swap(array, i, randomIndex);
            recordStep(array);
        }
    }

    // Swap two elements in the array
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Record the current step of the sorting process
    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
