package sorting;

import java.util.function.Consumer;

public class BitonicSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        bitonicSort(array, 0, array.length, 1);
    }

    private void bitonicSort(int[] array, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            bitonicSort(array, low, k, 1);
            bitonicSort(array, low + k, k, 0);
            bitonicMerge(array, low, cnt, dir);
        }
    }

    private void bitonicMerge(int[] array, int low, int cnt, int dir) {
        if (cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++) {
                highlightRecorder.accept(new int[] { i, i + k });
                if ((dir == 1 && array[i] > array[i + k]) || (dir == 0 && array[i] < array[i + k])) {
                    int temp = array[i];
                    array[i] = array[i + k];
                    array[i + k] = temp;
                    recordStep(array);
                }
            }
            bitonicMerge(array, low, k, dir);
            bitonicMerge(array, low + k, k, dir);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
