package sorting;

import java.util.Arrays;
import java.util.function.Consumer;

public class StructureSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    private static class Data implements Comparable<Data> {
        int key;
        int value;

        Data(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Data other) {
            return Integer.compare(this.key, other.key);
        }
    }

    public void sort(int[] keys, int[] values, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        Data[] dataArray = new Data[keys.length];
        for (int i = 0; i < keys.length; i++) {
            dataArray[i] = new Data(keys[i], values[i]);
        }

        Arrays.sort(dataArray);

        for (int i = 0; i < dataArray.length; i++) {
            keys[i] = dataArray[i].key;
            values[i] = dataArray[i].value;
            highlightRecorder.accept(new int[] { i });
            recordStep(keys);
            recordStep(values);
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
