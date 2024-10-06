package sorting;

import java.util.function.Consumer;

public class DutchNationalFlagSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        int low = 0, mid = 0, high = array.length - 1;
        int pivot = array[high]; // Assume the pivot is the last element

        while (mid <= high) {
            highlightRecorder.accept(new int[] { low, mid, high });
            if (array[mid] < pivot) {
                swap(array, low++, mid++);
            } else if (array[mid] > pivot) {
                swap(array, mid, high--);
            } else {
                mid++;
            }
            recordStep(array);
        }
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
