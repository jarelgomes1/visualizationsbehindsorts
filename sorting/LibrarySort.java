package sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class LibrarySort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        int[] sorted = new int[n + n / 2];
        Arrays.fill(sorted, Integer.MAX_VALUE);
        int sortedCount = 0;
        for (int value : array) {
            int pos = Arrays.binarySearch(sorted, 0, sortedCount, value);
            if (pos < 0) {
                pos = -pos - 1;
            }
            System.arraycopy(sorted, pos, sorted, pos + 1, sortedCount - pos);
            sorted[pos] = value;
            sortedCount++;
            recordStep(sorted);
        }
        System.arraycopy(sorted, 0, array, 0, n);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(Arrays.copyOfRange(array, 0, array.length));
        }
    }
}
