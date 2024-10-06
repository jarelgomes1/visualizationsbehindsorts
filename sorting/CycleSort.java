package sorting;

import java.util.function.Consumer;

public class CycleSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;

        for (int cycleStart = 0; cycleStart <= n - 2; cycleStart++) {
            int item = array[cycleStart];
            int pos = cycleStart;
            for (int i = cycleStart + 1; i < n; i++) {
                highlightRecorder.accept(new int[] { i });
                if (array[i] < item) {
                    pos++;
                }
            }
            if (pos == cycleStart)
                continue;
            while (item == array[pos]) {
                pos++;
            }
            int temp = array[pos];
            array[pos] = item;
            item = temp;
            recordStep(array);
            while (pos != cycleStart) {
                pos = cycleStart;
                for (int i = cycleStart + 1; i < n; i++) {
                    highlightRecorder.accept(new int[] { i });
                    if (array[i] < item) {
                        pos++;
                    }
                }
                while (item == array[pos]) {
                    pos++;
                }
                temp = array[pos];
                array[pos] = item;
                item = temp;
                recordStep(array);
            }
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
