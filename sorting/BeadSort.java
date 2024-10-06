package sorting;

import java.util.function.Consumer;

public class BeadSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        // Find the maximum value in the array
        int max = 0;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }

        // Create a 2D array of beads
        int len = array.length;
        boolean[][] beads = new boolean[len][max];

        // Place the beads
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < array[i]; j++) {
                beads[i][j] = true;
            }
        }

        // Gravity step: let the beads fall
        for (int j = 0; j < max; j++) {
            // Count beads in column j
            int count = 0;
            for (int i = 0; i < len; i++) {
                if (beads[i][j]) {
                    count++;
                }
            }
            // Set the bottom count positions in column j to true, others to false
            for (int i = 0; i < len; i++) {
                beads[i][j] = i < count;
            }
        }

        // Update the array according to the beads
        for (int i = 0; i < len; i++) {
            int sum = 0;
            for (int j = 0; j < max; j++) {
                if (beads[i][j]) {
                    sum++;
                }
            }
            array[i] = sum;
            highlightRecorder.accept(new int[] { i });
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
