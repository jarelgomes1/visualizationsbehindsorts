package main;

import sorting.*;
import sorting.research.QuantumSort;
import visualization.DataVisualizer;

import javax.swing.*;
import java.util.Random;
import java.util.function.Consumer;

public class SortVisualizerMain {
    public static void main(String[] args) {
        int[] data = generateRandomArray(50);

        SwingUtilities.invokeLater(
                () -> new DataVisualizer().visualize(data, "Sorting Visualization", (sortedData, method) -> {
                    int[] clonedData = data.clone();
                    Consumer<int[]> stepRecorder = array -> {
                        int[] snapshot = array.clone();
                        sortedData.accept(snapshot);
                    };

                    Consumer<int[]> highlightRecorder = indices -> sortedData.accept(clonedData);

                    switch (method) {
                        case "AmericanFlagSort":
                            new AmericanFlagSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BeadSort":
                            new BeadSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BingoSort":
                            new BingoSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BinaryInsertionSort":
                            new BinaryInsertionSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BitonicSort":
                            new BitonicSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BogoSort/PermutationSort":
                            new BogoSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BozoSort":
                            new BozoSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BubbleSort":
                            new BubbleSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BucketSort":
                            new BucketSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "BurstSort":
                            new BurstSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "CartesianTreeSort":
                            new CartesianTreeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "CocktailShakerSort":
                            new CocktailShakerSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "CombSort":
                            new CombSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "CountingSort":
                            new CountingSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "CycleSort":
                            new CycleSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "DutchNationalFlagSort":
                            new DutchNationalFlagSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "FlashSort":
                            new FlashSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "GnomeSort":
                            new GnomeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "GrailSort":
                            new GrailSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "GravitySort":
                            new GravitySort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "HeapSort":
                            new HeapSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "InsertionSort":
                            new InsertionSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "Introsort":
                            new Introsort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "LSDRadixSort":
                            new LSDRadixSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "LibrarySort":
                            new LibrarySort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "MSDRadixSort":
                            new MSDRadixSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "MergeSort":
                            new MergeSort().sort(clonedData, 0, clonedData.length - 1, stepRecorder, highlightRecorder);
                            break;
                        case "OddEvenSort/BrickSort":
                            new OddEvenSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "PancakeSort":
                            new PancakeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "PatienceSort":
                            new PatienceSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "PigeonholeSort":
                            new PigeonholeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "PostmanSort":
                            new PostmanSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "ProxmapSort":
                            new ProxmapSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "QuantumSort (custom research)":
                            new QuantumSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "QuickSort":
                            new QuickSort().sort(clonedData, 0, clonedData.length - 1, stepRecorder, highlightRecorder);
                            break;
                        case "RadixSort":
                            new RadixSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "SelectionSort":
                            new SelectionSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "ShellSort":
                            new ShellSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "SleepSort":
                            new SleepSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "SlowSort":
                            new SlowSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "SmoothSort":
                            new SmoothSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "SpreadSort":
                            new SpreadSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "StoogeSort":
                            new StoogeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "StrandSort":
                            new StrandSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "StructureSort":
                            new StructureSort().sort(clonedData, clonedData.clone(), stepRecorder, highlightRecorder);
                            break;
                        case "TagSort":
                            new TagSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "ThreeWayMergeSort":
                            new ThreeWayMergeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "TimSort":
                            new TimSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        case "TreeSort":
                            new TreeSort().sort(clonedData, stepRecorder, highlightRecorder);
                            break;
                        default:
                            break;
                    }
                }));
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(100);
        }
        return array;
    }
}
