package sorting;

import java.util.function.Consumer;

public class TimSort {
    private static final int RUN = 32;
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        for (int i = 0; i < n; i += RUN) {
            insertionSort(array, i, Math.min((i + 31), (n - 1)));
            recordStep(array);
        }
        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));
                merge(array, left, mid, right);
                recordStep(array);
            }
        }
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int temp = array[i];
            int j = i - 1;
            while (j >= left && array[j] > temp) {
                highlightRecorder.accept(new int[] { j, j + 1 });
                array[j + 1] = array[j];
                j--;
                recordStep(array);
            }
            array[j + 1] = temp;
            recordStep(array);
        }
    }

    private void merge(int[] array, int l, int m, int r) {
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        for (int x = 0; x < len1; x++)
            left[x] = array[l + x];
        for (int x = 0; x < len2; x++)
            right[x] = array[m + 1 + x];
        int i = 0, j = 0, k = l;
        while (i < len1 && j < len2) {
            highlightRecorder.accept(new int[] { l + i, m + 1 + j });
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
            k++;
            recordStep(array);
        }
        while (i < len1)
            array[k++] = left[i++];
        while (j < len2)
            array[k++] = right[j++];
        recordStep(array);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
