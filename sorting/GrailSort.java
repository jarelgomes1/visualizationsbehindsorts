package sorting;

import java.util.function.Consumer;

public class GrailSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        grailSort(array, 0, array.length);
    }

    private void grailSort(int[] array, int start, int length) {
        if (length <= 1)
            return;
        int mid = length / 2;
        grailSort(array, start, mid);
        grailSort(array, start + mid, length - mid);
        merge(array, start, mid, length - mid);
    }

    private void merge(int[] array, int start, int left, int right) {
        int[] temp = new int[left + right];
        int i = 0, j = 0, k = 0;

        while (i < left && j < right) {
            if (array[start + i] <= array[start + left + j]) {
                temp[k++] = array[start + i++];
            } else {
                temp[k++] = array[start + left + j++];
            }
            highlightRecorder.accept(new int[] { start + i, start + left + j });
        }

        while (i < left) {
            temp[k++] = array[start + i++];
            highlightRecorder.accept(new int[] { start + i });
        }

        while (j < right) {
            temp[k++] = array[start + left + j++];
            highlightRecorder.accept(new int[] { start + left + j });
        }

        System.arraycopy(temp, 0, array, start, temp.length);
        recordStep(array);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
