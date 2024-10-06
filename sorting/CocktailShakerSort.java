package sorting;

import java.util.function.Consumer;

public class CocktailShakerSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        boolean swapped = true;
        int start = 0;
        int end = array.length;
        while (swapped) {
            swapped = false;
            for (int i = start; i < end - 1; i++) {
                highlightRecorder.accept(new int[] { i, i + 1 });
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                    recordStep(array);
                }
            }
            if (!swapped)
                break;
            swapped = false;
            end--;
            for (int i = end - 1; i >= start; i--) {
                highlightRecorder.accept(new int[] { i, i + 1 });
                if (array[i] > array[i + 1]) {
                    int temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                    recordStep(array);
                }
            }
            start++;
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
