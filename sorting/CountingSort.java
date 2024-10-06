package sorting;

import java.util.function.Consumer;

public class CountingSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int max = getMax(array);
        int[] count = new int[max + 1];
        for (int i : array)
            count[i]++;
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                array[index++] = i;
                recordStep(array);
                highlightRecorder.accept(new int[] { index - 1 });
            }
        }
    }

    private int getMax(int[] array) {
        int max = array[0];
        for (int i : array)
            if (i > max)
                max = i;
        return max;
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
