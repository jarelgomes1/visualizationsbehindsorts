package sorting;

import java.util.function.Consumer;

public class CartesianTreeSort {
    public void sort(int[] array, Consumer<int[]> stepRecorder, Consumer<int[]> highlightRecorder) {
        int n = array.length;
        if (n <= 1)
            return;

        Node root = buildCartesianTree(array, 0, n - 1);
        int[] sortedArray = new int[n];
        inOrderTraversal(root, sortedArray, 0, stepRecorder, highlightRecorder);
        System.arraycopy(sortedArray, 0, array, 0, n);
        stepRecorder.accept(array.clone());
    }

    private static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
            left = right = null;
        }
    }

    private Node buildCartesianTree(int[] array, int low, int high) {
        if (low > high)
            return null;
        int minIndex = low;
        for (int i = low + 1; i <= high; i++) {
            if (array[i] < array[minIndex])
                minIndex = i;
        }
        Node node = new Node(array[minIndex]);
        node.left = buildCartesianTree(array, low, minIndex - 1);
        node.right = buildCartesianTree(array, minIndex + 1, high);
        return node;
    }

    private int inOrderTraversal(Node node, int[] sortedArray, int index, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        if (node == null)
            return index;
        index = inOrderTraversal(node.left, sortedArray, index, stepRecorder, highlightRecorder);
        sortedArray[index++] = node.value;
        stepRecorder.accept(sortedArray.clone());
        highlightRecorder.accept(new int[] { index });
        index = inOrderTraversal(node.right, sortedArray, index, stepRecorder, highlightRecorder);
        return index;
    }
}
