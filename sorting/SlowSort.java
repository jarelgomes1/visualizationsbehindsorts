package sorting;

import java.util.function.Consumer;

public class SlowSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        slowSort(array, 0, array.length - 1);
    }

    private void slowSort(int[] array, int i, int j) {
        if (i >= j)
            return;
        int m = (i + j) / 2;
        slowSort(array, i, m);
        slowSort(array, m + 1, j);
        if (array[m] > array[j]) {
            highlightRecorder.accept(new int[] { m, j });
            int temp = array[m];
            array[m] = array[j];
            array[j] = temp;
            recordStep(array);
        }
        slowSort(array, i, j - 1);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
