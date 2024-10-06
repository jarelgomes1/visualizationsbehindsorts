package sorting;

import java.util.function.Consumer;

public class PancakeSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        for (int curr_size = n; curr_size > 1; --curr_size) {
            int mi = findMax(array, curr_size);
            if (mi != curr_size - 1) {
                flip(array, mi);
                flip(array, curr_size - 1);
            }
        }
    }

    private int findMax(int[] array, int n) {
        int mi = 0;
        for (int i = 0; i < n; i++) {
            if (array[i] > array[mi]) {
                mi = i;
                highlightRecorder.accept(new int[] { mi });
                recordStep(array);
            }
        }
        return mi;
    }

    private void flip(int[] array, int i) {
        int start = 0;
        while (start < i) {
            highlightRecorder.accept(new int[] { start, i });
            int temp = array[start];
            array[start] = array[i];
            array[i] = temp;
            start++;
            i--;
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
