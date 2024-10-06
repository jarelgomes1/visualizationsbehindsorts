package sorting;

import java.util.function.Consumer;

public class GnomeSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        int index = 0;
        while (index < n) {
            if (index == 0)
                index++;
            highlightRecorder.accept(new int[] { index, index - 1 });
            if (array[index] >= array[index - 1])
                index++;
            else {
                int temp = array[index];
                array[index] = array[index - 1];
                array[index - 1] = temp;
                recordStep(array);
                index--;
            }
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null)
            stepRecorder.accept(array.clone());
    }
}
