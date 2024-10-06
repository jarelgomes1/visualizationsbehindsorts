package sorting;

import java.util.TreeSet;
import java.util.function.Consumer;

public class TreeSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        TreeSet<Integer> tree = new TreeSet<>();
        for (int value : array) {
            tree.add(value);
            recordStep(array);
        }
        int i = 0;
        for (int value : tree) {
            highlightRecorder.accept(new int[] { i });
            array[i++] = value;
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
