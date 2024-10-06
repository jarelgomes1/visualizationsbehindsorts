package sorting;

import java.util.function.Consumer;

public class ProxmapSort {
    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        int max = array[0];
        int min = array[0];
        for (int value : array) {
            if (value > max)
                max = value;
            if (value < min)
                min = value;
        }
        int range = max - min + 1;

        int[] count = new int[range];
        for (int value : array) {
            count[value - min]++;
        }

        int[] map = new int[array.length];
        int j = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i]-- > 0) {
                map[j++] = i + min;
                stepRecorder.accept(map.clone());
                highlightRecorder.accept(new int[] { j - 1 });
            }
        }

        System.arraycopy(map, 0, array, 0, array.length);
    }
}
