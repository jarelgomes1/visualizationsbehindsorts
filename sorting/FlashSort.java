package sorting;

import java.util.function.Consumer;

public class FlashSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        int n = array.length;
        if (n == 0)
            return;
        int m = (int) (0.45 * n);
        int min = array[0];
        int max = 0;
        for (int i = 1; i < n; i++) {
            highlightRecorder.accept(new int[] { i });
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > array[max]) {
                max = i;
            }
        }
        if (min == array[max])
            return;
        int[] l = new int[m];
        double c1 = (double) (m - 1) / (array[max] - min);
        for (int i = 0; i < n; i++) {
            int k = (int) (c1 * (array[i] - min));
            l[k]++;
        }
        for (int i = 1; i < m; i++) {
            l[i] += l[i - 1];
        }
        int hold = array[max];
        array[max] = array[0];
        array[0] = hold;
        int move = 0, j = 0, k = m - 1;
        while (move < n - 1) {
            while (j > l[k] - 1) {
                j++;
                k = (int) (c1 * (array[j] - min));
            }
            int flash = array[j];
            while (j != l[k]) {
                highlightRecorder.accept(new int[] { j });
                k = (int) (c1 * (flash - min));
                hold = array[l[k] - 1];
                array[l[k] - 1] = flash;
                flash = hold;
                l[k]--;
                move++;
                recordStep(array);
            }
        }
        for (int i = 1; i < n; i++) {
            int tempIndex = i - 1;
            int key = array[i];
            while (tempIndex >= 0 && array[tempIndex] > key) {
                array[tempIndex + 1] = array[tempIndex];
                tempIndex--;
                highlightRecorder.accept(new int[] { tempIndex + 1 });
                recordStep(array);
            }
            array[tempIndex + 1] = key;
            highlightRecorder.accept(new int[] { tempIndex + 1 });
            recordStep(array);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
