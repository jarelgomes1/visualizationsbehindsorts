package sorting.research;

import java.util.function.Consumer;

public class QuantumSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        // Custom sorting logic (hypothetical quantum sort)
        quantumSort(array, 0, array.length - 1);
    }

    private void quantumSort(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = (left + right) / 2;
            int pivotValue = array[pivotIndex];
            highlightRecorder.accept(new int[] { pivotIndex });

            swap(array, pivotIndex, right);
            int storeIndex = left;

            for (int i = left; i < right; i++) {
                if (array[i] < pivotValue) {
                    swap(array, i, storeIndex);
                    storeIndex++;
                }
                highlightRecorder.accept(new int[] { i, storeIndex });
                recordStep(array);
            }

            swap(array, storeIndex, right);
            recordStep(array);

            quantumSort(array, left, storeIndex - 1);
            quantumSort(array, storeIndex + 1, right);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        highlightRecorder.accept(new int[] { i, j });
        recordStep(array);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
