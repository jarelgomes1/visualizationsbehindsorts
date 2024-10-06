package sorting;

import java.util.function.Consumer;

public class SelectionSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                highlightRecorder.accept(new int[] { minIndex, j });
                if (array[j] < array[minIndex])
                    minIndex = j;
                recordStep(array);
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
