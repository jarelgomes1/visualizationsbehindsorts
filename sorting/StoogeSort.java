package sorting;

import java.util.function.Consumer;

public class StoogeSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        stoogesort(array, 0, array.length - 1);
    }

    private void stoogesort(int[] array, int l, int h) {
        if (l >= h)
            return;
        highlightRecorder.accept(new int[] { l, h });
        if (array[l] > array[h]) {
            int temp = array[l];
            array[l] = array[h];
            array[h] = temp;
            recordStep(array);
        }
        if (h - l + 1 > 2) {
            int t = (h - l + 1) / 3;
            stoogesort(array, l, h - t);
            stoogesort(array, l + t, h);
            stoogesort(array, l, h - t);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
