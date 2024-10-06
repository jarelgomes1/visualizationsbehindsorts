package sorting;

import java.util.function.Consumer;

public class BingoSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        int bingo = max;
        do {
            int nextBingo = Integer.MIN_VALUE;
            for (int i = 0; i < array.length; i++) {
                if (array[i] < bingo && array[i] > nextBingo) {
                    nextBingo = array[i];
                }
            }
            for (int i = 0; i < array.length; i++) {
                highlightRecorder.accept(new int[] { i });
                if (array[i] == bingo) {
                    for (int j = i; j > 0 && array[j - 1] > bingo; j--) {
                        int temp = array[j];
                        array[j] = array[j - 1];
                        array[j - 1] = temp;
                        highlightRecorder.accept(new int[] { j - 1 });
                        recordStep(array);
                    }
                }
            }
            bingo = nextBingo;
        } while (bingo != Integer.MIN_VALUE);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
