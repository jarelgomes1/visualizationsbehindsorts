package sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class TagSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        int n = array.length;
        Integer[] tags = new Integer[n];
        for (int i = 0; i < n; i++) {
            tags[i] = i;
        }

        Arrays.sort(tags, (a, b) -> Integer.compare(array[a], array[b]));

        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[i] = array[tags[i]];
            highlightRecorder.accept(new int[] { tags[i] });
            recordStep(sortedArray);
        }

        System.arraycopy(sortedArray, 0, array, 0, n);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
