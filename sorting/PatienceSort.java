package sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public class PatienceSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        List<Stack<Integer>> piles = new ArrayList<>();
        for (int i : array) {
            int pile = Collections.binarySearch(piles, null, (a, b) -> {
                if (b == null || b.isEmpty()) {
                    return -1;
                }
                return Integer.compare(i, b.peek());
            });
            if (pile < 0)
                pile = ~pile;
            if (pile == piles.size())
                piles.add(new Stack<>());
            piles.get(pile).push(i);
            recordStep(array);
        }
        int i = 0;
        for (Stack<Integer> pile : piles) {
            while (!pile.isEmpty()) {
                highlightRecorder.accept(new int[] { i });
                array[i++] = pile.pop();
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
