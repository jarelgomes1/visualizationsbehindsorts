package sorting;

import java.util.function.Consumer;

public class Introsort {
    private static final int SIZE_THRESHOLD = 16;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introsort(array, 0, array.length - 1, maxDepth, stepRecorder, highlightRecorder);
    }

    private void introsort(int[] array, int start, int end, int maxDepth, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        int size = end - start + 1;
        if (size < SIZE_THRESHOLD) {
            insertionSort(array, start, end, stepRecorder, highlightRecorder);
        } else if (maxDepth == 0) {
            heapSort(array, start, end, stepRecorder, highlightRecorder);
        } else {
            int pivot = partition(array, start, end, stepRecorder, highlightRecorder);
            introsort(array, start, pivot - 1, maxDepth - 1, stepRecorder, highlightRecorder);
            introsort(array, pivot + 1, end, maxDepth - 1, stepRecorder, highlightRecorder);
        }
    }

    private void insertionSort(int[] array, int start, int end, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        for (int i = start + 1; i <= end; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= start && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                stepRecorder.accept(array.clone());
                highlightRecorder.accept(new int[] { j, j + 1 });
            }
            array[j + 1] = key;
            stepRecorder.accept(array.clone());
            highlightRecorder.accept(new int[] { j + 1 });
        }
    }

    private void heapSort(int[] array, int start, int end, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        for (int i = (end - start) / 2; i >= start; i--) {
            heapify(array, end - start + 1, i, start, stepRecorder, highlightRecorder);
        }
        for (int i = end; i > start; i--) {
            int temp = array[start];
            array[start] = array[i];
            array[i] = temp;
            heapify(array, i, start, start, stepRecorder, highlightRecorder);
        }
    }

    private void heapify(int[] array, int size, int i, int offset, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        int largest = i;
        int left = 2 * i + 1 + offset;
        int right = 2 * i + 2 + offset;
        if (left < size && array[left] > array[largest])
            largest = left;
        if (right < size && array[right] > array[largest])
            largest = right;
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            stepRecorder.accept(array.clone());
            highlightRecorder.accept(new int[] { i, largest });
            heapify(array, size, largest, offset, stepRecorder, highlightRecorder);
        }
    }

    private int partition(int[] array, int start, int end, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        int pivot = array[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                stepRecorder.accept(array.clone());
                highlightRecorder.accept(new int[] { i, j });
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = temp;
        stepRecorder.accept(array.clone());
        highlightRecorder.accept(new int[] { i + 1, end });
        return i + 1;
    }
}
