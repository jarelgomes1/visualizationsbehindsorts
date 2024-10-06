package sorting;

import java.util.function.Consumer;

public class InsertionSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                highlightRecorder.accept(new int[] { j, j + 1 });
                array[j + 1] = array[j];
                j = j - 1;
                recordStep(array);
            }
            array[j + 1] = key;
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
