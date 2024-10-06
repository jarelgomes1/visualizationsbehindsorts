package sorting;

import java.util.function.Consumer;

public class OddEvenSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i <= array.length - 2; i = i + 2) {
                highlightRecorder.accept(new int[] { i, i + 1 });
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    isSorted = false;
                    recordStep(array);
                }
            }
            for (int i = 0; i <= array.length - 2; i = i + 2) {
                highlightRecorder.accept(new int[] { i, i + 1 });
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    isSorted = false;
                    recordStep(array);
                }
            }
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
