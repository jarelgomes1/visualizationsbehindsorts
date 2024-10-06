package sorting;

import java.util.function.Consumer;

public class SpreadSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        int max = findMax(array);
        int min = findMin(array);
        spreadSort(array, 0, array.length - 1, min, max);
    }

    private void spreadSort(int[] array, int low, int high, int min, int max) {
        if (low < high) {
            int mid = partition(array, low, high, min, max);
            recordStep(array);
            spreadSort(array, low, mid - 1, min, max);
            spreadSort(array, mid + 1, high, min, max);
        }
    }

    private int partition(int[] array, int low, int high, int min, int max) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                highlightRecorder.accept(new int[] { i, j });
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
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

    private int findMin(int[] array) {
        int min = array[0];
        for (int num : array) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
