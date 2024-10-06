package sorting;

import java.util.function.Consumer;
import java.util.concurrent.CountDownLatch;

public class SleepSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;

        int[] sorted = new int[array.length];
        CountDownLatch latch = new CountDownLatch(array.length);

        for (int i = 0; i < array.length; i++) {
            final int value = array[i];
            final int index = i;
            new Thread(() -> {
                try {
                    Thread.sleep(value * 10L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                sorted[index] = value;
                highlightRecorder.accept(new int[] { index });
                recordStep(sorted);
                latch.countDown();
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.arraycopy(sorted, 0, array, 0, array.length);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
