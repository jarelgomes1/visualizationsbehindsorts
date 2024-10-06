package sorting;

import java.util.function.Consumer;

public class GravitySort {
    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        int max = 0;

        // Find the maximum value in the array
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }

        // Initialize the bead columns
        int[][] beads = new int[array.length][max];

        // Place beads in the columns
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i]; j++) {
                beads[i][j] = 1;
            }
        }

        // Let the beads "fall" down
        for (int j = 0; j < max; j++) {
            int sum = 0;
            for (int i = 0; i < array.length; i++) {
                sum += beads[i][j];
                beads[i][j] = 0;
            }
            for (int i = array.length - sum; i < array.length; i++) {
                beads[i][j] = 1;
            }
            recordStep(beads, stepRecorder, array);
        }

        // Read the sorted values from the beads
        for (int i = 0; i < array.length; i++) {
            int j;
            for (j = 0; j < max && beads[i][j] == 1; j++) {
                // highlight each bead falling
                highlightRecorder.accept(new int[] { i, j });
            }
            array[i] = j;
            stepRecorder.accept(array.clone());
        }
    }

    private void recordStep(int[][] beads, Consumer<int[]> stepRecorder, int[] array) {
        int[] tempArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int count = 0;
            for (int j = 0; j < beads[i].length; j++) {
                if (beads[i][j] == 1) {
                    count++;
                }
            }
            tempArray[i] = count;
        }
        stepRecorder.accept(tempArray);
    }
}
