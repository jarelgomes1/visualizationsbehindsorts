package sorting;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class StrandSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        List<Integer> sortedList = new LinkedList<>();
        List<Integer> inputList = new LinkedList<>();
        for (int value : array)
            inputList.add(value);
        while (!inputList.isEmpty()) {
            List<Integer> sublist = new LinkedList<>();
            sublist.add(inputList.remove(0));
            for (int i = 0; i < inputList.size(); i++) {
                highlightRecorder.accept(new int[] { i });
                if (inputList.get(i) > sublist.get(sublist.size() - 1)) {
                    sublist.add(inputList.remove(i));
                    i--;
                }
            }
            merge(sortedList, sublist);
            recordStep(array);
        }
        for (int i = 0; i < sortedList.size(); i++)
            array[i] = sortedList.get(i);
    }

    private void merge(List<Integer> sortedList, List<Integer> sublist) {
        int i = 0, j = 0;
        while (i < sortedList.size() && j < sublist.size()) {
            highlightRecorder.accept(new int[] { i, j });
            if (sortedList.get(i) <= sublist.get(j)) {
                i++;
            } else {
                sortedList.add(i, sublist.get(j));
                j++;
                i++;
                recordStep(sortedList.stream().mapToInt(Integer::intValue).toArray());
            }
        }
        while (j < sublist.size()) {
            sortedList.add(sublist.get(j++));
            recordStep(sortedList.stream().mapToInt(Integer::intValue).toArray());
        }
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
