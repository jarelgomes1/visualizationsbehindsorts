package sorting;

import java.util.function.Consumer;

public class SmoothSort {
    private Consumer<int[]> stepRecorder;
    private Consumer<int[]> highlightRecorder;

    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        this.stepRecorder = stepRecorder;
        this.highlightRecorder = highlightRecorder;
        smoothSort(array);
    }

    private void smoothSort(int[] array) {
        int n = array.length;
        int q = 1, r = 0, p = 1;
        int b = 1, c = 1;

        while (q < n) {
            if ((p & 3) == 3) {
                sift(array, r, b, c);
                p >>= 2;
                b += c + 1;
                c = b - c - 1;
                recordStep(array);
            } else if (q + c < n) {
                sift(array, r, b, c);
                r = q++;
                p <<= 1;
                while (b > 1) {
                    b -= c + 1;
                    c = b - c - 1;
                }
            } else {
                trinkle(array, q, p, r, b, c);
                p >>= 1;
                while (p > 0) {
                    b += c + 1;
                    c = b - c - 1;
                    if ((p & 1) == 1) {
                        trinkle(array, q - b, p, q - b, b, c);
                    }
                    p >>= 1;
                }
                r = q;
                q++;
                recordStep(array);
            }
        }
        trinkle(array, 0, p, r, b, c);
        recordStep(array);
    }

    private void sift(int[] array, int r, int b, int c) {
        while (b >= 3) {
            int r1 = r - c - 1;
            int r2 = r - 1;
            if (array[r2] > array[r]) {
                r1 = r2;
            }
            if (array[r1] > array[r]) {
                int temp = array[r];
                array[r] = array[r1];
                array[r1] = temp;
                r = r1;
                b -= c + 1;
                c = b - c - 1;
                highlightRecorder.accept(new int[] { r });
            } else {
                b = 1;
            }
        }
    }

    private void trinkle(int[] array, int q, int p, int r, int b, int c) {
        while (p > 0) {
            while (p % 4 == 0) {
                p >>= 2;
                b += c + 1;
                c = b - c - 1;
            }
            int r1 = r - b;
            if (p % 2 == 1 && array[r1] > array[r]) {
                int temp = array[r];
                array[r] = array[r1];
                array[r1] = temp;
                highlightRecorder.accept(new int[] { r });
            }
            p = (p >> 1) ^ 1;
            r = r1;
        }
        sift(array, r, b, c);
    }

    private void recordStep(int[] array) {
        if (stepRecorder != null) {
            stepRecorder.accept(array.clone());
        }
    }
}
