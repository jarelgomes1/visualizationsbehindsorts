package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public class BucketSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        if (n <= 0)
            return;

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] bucket = new ArrayList[n];
        for (int i = 0; i < n; i++)
            bucket[i] = new ArrayList<>();

        int max = array[0];
        for (int value : array)
            if (value > max)
                max = value;

        for (int value : array)
            bucket[(value * n) / (max + 1)].add(value);
        for (ArrayList<Integer> integers : bucket)
            Collections.sort(integers);

        int index = 0;
        for (ArrayList<Integer> integers : bucket) {
            for (int value : integers) {
                array[index++] = value;
                recordStep(array);
                highlightRecorder.accept(new int[] { index - 1 });
            }
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
