package sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MSDRadixSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        msdRadixSort(array, 0, array.length, mostSignificantDigit(array));
    }

    private void msdRadixSort(int[] array, int start, int end, int digit) {
        if (end - start <= 1 || digit < 0) {
            return;
        }

        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int i = start; i < end; i++) {
            int bucketIndex = (array[i] / (int) Math.pow(10, digit)) % 10;
            highlightRecorder.accept(new int[] { i });
            buckets.get(bucketIndex).add(array[i]);
        }

        int index = start;
        for (int i = 0; i < 10; i++) {
            for (int value : buckets.get(i)) {
                array[index++] = value;
                recordStep(array);
            }
            msdRadixSort(array, start + index - buckets.get(i).size(), start + index, digit - 1);
        }
    }

    private int mostSignificantDigit(int[] array) {
        int max = findMax(array);
        return (int) Math.log10(max);
    }

    private int findMax(int[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
