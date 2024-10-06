package sorting;

import java.util.function.Consumer;

public class ThreeWayMergeSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        threeWayMergeSort(array, 0, array.length - 1);
    }

    private void threeWayMergeSort(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid1 = low + (high - low) / 3;
        int mid2 = low + 2 * (high - low) / 3;

        threeWayMergeSort(array, low, mid1);
        threeWayMergeSort(array, mid1 + 1, mid2);
        threeWayMergeSort(array, mid2 + 1, high);

        merge(array, low, mid1, mid2, high);
    }

    private void merge(int[] array, int low, int mid1, int mid2, int high) {
        int n1 = mid1 - low + 1;
        int n2 = mid2 - mid1;
        int n3 = high - mid2;

        int[] left = new int[n1];
        int[] middle = new int[n2];
        int[] right = new int[n3];

        for (int i = 0; i < n1; i++) {
            left[i] = array[low + i];
            highlightRecorder.accept(new int[] { low + i });
        }
        for (int i = 0; i < n2; i++) {
            middle[i] = array[mid1 + 1 + i];
            highlightRecorder.accept(new int[] { mid1 + 1 + i });
        }
        for (int i = 0; i < n3; i++) {
            right[i] = array[mid2 + 1 + i];
            highlightRecorder.accept(new int[] { mid2 + 1 + i });
        }

        int i = 0, j = 0, k = 0, l = low;

        while (i < n1 && j < n2 && k < n3) {
            if (left[i] <= middle[j] && left[i] <= right[k]) {
                array[l++] = left[i++];
            } else if (middle[j] <= left[i] && middle[j] <= right[k]) {
                array[l++] = middle[j++];
            } else {
                array[l++] = right[k++];
            }
            recordStep(array);
        }

        while (i < n1 && j < n2) {
            if (left[i] <= middle[j]) {
                array[l++] = left[i++];
            } else {
                array[l++] = middle[j++];
            }
            recordStep(array);
        }

        while (j < n2 && k < n3) {
            if (middle[j] <= right[k]) {
                array[l++] = middle[j++];
            } else {
                array[l++] = right[k++];
            }
            recordStep(array);
        }

        while (i < n1 && k < n3) {
            if (left[i] <= right[k]) {
                array[l++] = left[i++];
            } else {
                array[l++] = right[k++];
            }
            recordStep(array);
        }

        while (i < n1) {
            array[l++] = left[i++];
            recordStep(array);
        }

        while (j < n2) {
            array[l++] = middle[j++];
            recordStep(array);
        }

        while (k < n3) {
            array[l++] = right[k++];
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
