package sorting;

import java.util.function.Consumer;

public class PigeonholeSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int min = array[0];
        int max = array[0];
        for (int value : array) {
            if (value > max)
                max = value;
            if (value < min)
                min = value;
        }
        int range = max - min + 1;
        int[] holes = new int[range];
        for (int value : array)
            holes[value - min]++;
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (holes[i]-- > 0) {
                array[index++] = i + min;
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
