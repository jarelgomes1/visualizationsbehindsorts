package visualization;

import javax.swing.*;
import sorting.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DataVisualizer {
    private int[] originalData;
    private Timer timer;
    private List<int[]> sortingSteps;
    private int currentStep;
    private VisualizationPanel sortedPanel;
    private JTextArea textArea;
    private int[] currentHighlight;

    public void visualize(int[] data, String title, BiConsumer<Consumer<int[]>, String> onSort) {
        originalData = data.clone();
        sortingSteps = new ArrayList<>();
        currentStep = 0;

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.append("Unsorted Data: " + arrayToString(data) + "\n\n");
        textArea.append("Processing Data: \n\n");
        textArea.append("Sorted Data: \n\n");

        JPanel controlPanel = new JPanel();
        String[] sortingMethods = {
                "AmericanFlagSort", "BeadSort", "BingoSort", "BinaryInsertionSort", "BitonicSort",
                "BlockSort", "BogoSort/PermutationSort", "BozoSort",
                "BubbleSort", "BucketSort", "BurstSort", "CartesianTreeSort", "CocktailShakerSort", "CombSort",
                "CountingSort", "CycleSort", "DutchNationalFlagSort", "FlashSort", "GnomeSort", "GrailSort",
                "GravitySort", "HeapSort", "InsertionSort", "Introsort", "LibrarySort", "LSDRadixSort", "MergeSort",
                "MSDRadixSort", "OddEvenSort/BrickSort", "PancakeSort", "PatienceSort", "PigeonholeSort", "PostmanSort",
                "ProxmapSort", "QuantumSort (custom research)", "QuickSort", "RadixSort", "SelectionSort", "ShellSort",
                "SleepSort", "SlowSort",
                "SmoothSort", "SpreadSort", "StoogeSort", "StrandSort", "StructureSort", "TagSort", "ThreeWayMergeSort",
                "TimSort", "TreeSort"
        };

        JComboBox<String> comboBox = new JComboBox<>(sortingMethods);
        JButton sortButton = new JButton("Sort");
        JButton resetButton = new JButton("Reset");

        controlPanel.add(new JLabel("Choose Sorting Method:"));
        controlPanel.add(comboBox);
        controlPanel.add(sortButton);
        controlPanel.add(resetButton);

        JPanel visualizationPanel = new JPanel();
        visualizationPanel.setLayout(new GridLayout(1, 2));
        VisualizationPanel unsortedPanel = new VisualizationPanel(data, "Unsorted Data");
        visualizationPanel.add(unsortedPanel);

        sortedPanel = new VisualizationPanel(new int[0], "Processing Data");
        visualizationPanel.add(sortedPanel);

        JTextArea complexityArea = new JTextArea();
        complexityArea.setEditable(false);
        complexityArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        sortButton.addActionListener(e -> {
            String method = (String) comboBox.getSelectedItem();
            sortingSteps.clear();
            currentHighlight = new int[0];

            Consumer<int[]> stepRecorder = array -> {
                int[] snapshot = array.clone();
                sortingSteps.add(snapshot);
            };

            Consumer<int[]> highlightRecorder = indices -> currentHighlight = indices;

            sortWithMethod(originalData.clone(), method, stepRecorder, highlightRecorder);

            showComplexityInfo(method, complexityArea);

            startAnimation();
        });

        resetButton.addActionListener(e -> {
            sortedPanel.setData(originalData);
            sortedPanel.setLabel("Unsorted Data");
            sortedPanel.repaint();
            textArea.setText("Unsorted Data: " + arrayToString(originalData) + "\n\n");
            textArea.append("Processing Data: \n\n");
            textArea.append("Sorted Data: \n\n");
            complexityArea.setText("");
            timer.stop();
            currentStep = 0;
        });

        frame.add(new JScrollPane(textArea), BorderLayout.NORTH);
        frame.add(visualizationPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(complexityArea), BorderLayout.EAST);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentStep < sortingSteps.size() - 1) {
                    currentStep++;
                    sortedPanel.setData(sortingSteps.get(currentStep));
                    sortedPanel.setHighlightedIndices(currentHighlight);
                    sortedPanel.repaint();
                    updateTextArea(currentStep);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentStep > 0) {
                    currentStep--;
                    sortedPanel.setData(sortingSteps.get(currentStep));
                    sortedPanel.setHighlightedIndices(currentHighlight);
                    sortedPanel.repaint();
                    updateTextArea(currentStep);
                }
            }
        });

        frame.setFocusable(true);
        frame.setVisible(true);
    }

    private void sortWithMethod(int[] data, String method, Consumer<int[]> stepRecorder,
            Consumer<int[]> highlightRecorder) {
        switch (method) {
            case "AmericanFlagSort":
                new AmericanFlagSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BeadSort":
                new BeadSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BingoSort":
                new BingoSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BinaryInsertionSort":
                new BinaryInsertionSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BitonicSort":
                new BitonicSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BlockSort":
                new BlockSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BogoSort/PermutationSort":
                new BogoSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BozoSort":
                new BozoSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BubbleSort":
                new BubbleSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BucketSort":
                new BucketSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "BurstSort":
                new BurstSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "CartesianTreeSort":
                new CartesianTreeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "CocktailShakerSort":
                new CocktailShakerSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "CombSort":
                new CombSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "CountingSort":
                new CountingSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "CycleSort":
                new CycleSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "DutchNationalFlagSort":
                new DutchNationalFlagSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "FlashSort":
                new FlashSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "GnomeSort":
                new GnomeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "GrailSort":
                new GrailSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "GravitySort":
                new GravitySort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "HeapSort":
                new HeapSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "InsertionSort":
                new InsertionSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "Introsort":
                new Introsort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "LibrarySort":
                new LibrarySort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "LSDRadixSort":
                new LSDRadixSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "MergeSort":
                new MergeSort().sort(data, 0, data.length - 1, stepRecorder, highlightRecorder);
                break;
            case "MSDRadixSort":
                new MSDRadixSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "OddEvenSort/BrickSort":
                new OddEvenSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "PancakeSort":
                new PancakeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "PatienceSort":
                new PatienceSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "PigeonholeSort":
                new PigeonholeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "PostmanSort":
                new PostmanSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "ProxmapSort":
                new ProxmapSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "QuickSort":
                new QuickSort().sort(data, 0, data.length - 1, stepRecorder, highlightRecorder);
                break;
            case "RadixSort":
                new RadixSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "SelectionSort":
                new SelectionSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "ShellSort":
                new ShellSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "SleepSort":
                new SleepSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "SlowSort":
                new SlowSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "SmoothSort":
                new SmoothSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "SpreadSort":
                new SpreadSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "StoogeSort":
                new StoogeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "StrandSort":
                new StrandSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "StructureSort":
                new StructureSort().sort(data, data.clone(), stepRecorder, highlightRecorder); // Simulating Structure
                                                                                               // Sorting
                break;
            case "TagSort":
                new TagSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "ThreeWayMergeSort":
                new ThreeWayMergeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "TimSort":
                new TimSort().sort(data, stepRecorder, highlightRecorder);
                break;
            case "TreeSort":
                new TreeSort().sort(data, stepRecorder, highlightRecorder);
                break;
            default:
                break;
        }
    }

    private void showComplexityInfo(String method, JTextArea complexityArea) {
        String complexityInfo = getComplexityInfo(method);
        complexityArea.setText(complexityInfo);
    }

    private String getComplexityInfo(String method) {
        switch (method) {
            case "AmericanFlagSort":
                return "American Flag Sort:\nBest: O(n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BeadSort":
                return "Bead Sort:\nBest: O(n)\nAverage: O(n)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BingoSort":
                return "Bingo Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BinaryInsertionSort":
                return "Binary Insertion Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Insertion\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BitonicSort":
                return "Bitonic Sort:\nBest: O(n log^2 n)\nAverage: O(n log^2 n)\nWorst: O(n log^2 n)\nAuxiliary Space: O(n)\nStability: No\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BlockSort":
                return "Block Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BogoSort/PermutationSort":
                return "Bogo Sort/Permutation Sort:\nBest: O(n)\nAverage: O((n+1)!)\nWorst: O(∞)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Generate and Test\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BozoSort":
                return "Bozo Sort:\nBest: O(n)\nAverage: O((n+1)!)\nWorst: O(∞)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Generate and Test\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BubbleSort":
                return "Bubble Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Exchange\nSerial: Yes\nAdaptive: Yes\nOnline: No";
            case "BucketSort":
                return "Bucket Sort:\nBest: O(n + k)\nAverage: O(n + k)\nWorst: O(n^2)\nAuxiliary Space: O(n + k)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "BurstSort":
                return "Burst Sort:\nBest: O(n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: No\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "CartesianTreeSort":
                return "Cartesian Tree Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "CocktailShakerSort":
                return "Cocktail Shaker Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Exchange\nSerial: Yes\nAdaptive: Yes\nOnline: No";
            case "CombSort":
                return "Comb Sort:\nBest: O(n log n)\nAverage: O(n^2/2^p)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Exchange\nSerial: Yes\nAdaptive: Yes\nOnline: No";
            case "CountingSort":
                return "Counting Sort:\nBest: O(n + k)\nAverage: O(n + k)\nWorst: O(n + k)\nAuxiliary Space: O(k)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "CycleSort":
                return "Cycle Sort:\nBest: O(n^2)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "DutchNationalFlagSort":
                return "Dutch National Flag Sort:\nBest: O(n)\nAverage: O(n)\nWorst: O(n)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Partitioning\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "FlashSort":
                return "Flash Sort:\nBest: O(n)\nAverage: O(n + k)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "GnomeSort":
                return "Gnome Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Exchange\nSerial: Yes\nAdaptive: Yes\nOnline: Yes";
            case "GrailSort":
                return "Grail Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "GravitySort":
                return "Gravity Sort:\nBest: O(n)\nAverage: O(n)\nWorst: O(n)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "HeapSort":
                return "Heap Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: Yes\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "InsertionSort":
                return "Insertion Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Insertion\nSerial: Yes\nAdaptive: Yes\nOnline: Yes";
            case "Introsort":
                return "Introsort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(log n)\nStability: No\nIn-place: Yes\nRecursive: Yes\nComparison: Yes\nMethod: Hybrid\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "LibrarySort":
                return "Library Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n^2)\nAuxiliary Space: O(n log n)\nStability: No\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Insertion\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "LSDRadixSort":
                return "LSD Radix Sort:\nBest: O(nk)\nAverage: O(nk)\nWorst: O(nk)\nAuxiliary Space: O(n + k)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "MergeSort":
                return "Merge Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "MSDRadixSort":
                return "MSD Radix Sort:\nBest: O(nk)\nAverage: O(nk)\nWorst: O(nk)\nAuxiliary Space: O(n + k)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "OddEvenSort/BrickSort":
                return "Odd-Even Sort/Brick Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: Yes\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Exchange\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "PancakeSort":
                return "Pancake Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: Yes\nComparison: Yes\nMethod: Exchanging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "PatienceSort":
                return "Patience Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "PigeonholeSort":
                return "Pigeonhole Sort:\nBest: O(n + k)\nAverage: O(n + k)\nWorst: O(n + k)\nAuxiliary Space: O(n + k)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "PostmanSort":
                return "Postman Sort:\nBest: O(n)\nAverage: O(n)\nWorst: O(n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "ProxmapSort":
                return "Proxmap Sort:\nBest: O(n)\nAverage: O(n)\nWorst: O(n^2)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "QuickSort":
                return "Quick Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n^2)\nAuxiliary Space: O(log n)\nStability: No\nIn-place: Yes\nRecursive: Yes\nComparison: Yes\nMethod: Partitioning\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "RadixSort":
                return "Radix Sort:\nBest: O(nk)\nAverage: O(nk)\nWorst: O(nk)\nAuxiliary Space: O(n + k)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "SelectionSort":
                return "Selection Sort:\nBest: O(n^2)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "ShellSort":
                return "Shell Sort:\nBest: O(n log n)\nAverage: O((n log n)^2)\nWorst: O(n log^2 n)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: No\nComparison: Yes\nMethod: Insertion\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "SleepSort":
                return "Sleep Sort:\nBest: O(n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: No\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "SlowSort":
                return "Slow Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "SmoothSort":
                return "Smooth Sort:\nBest: O(n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(1)\nStability: No\nIn-place: Yes\nRecursive: Yes\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "SpreadSort":
                return "Spread Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: No\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Distribution\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "StoogeSort":
                return "Stooge Sort:\nBest: O(n^2.7)\nAverage: O(n^2.7)\nWorst: O(n^2.7)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: Yes\nRecursive: Yes\nComparison: Yes\nMethod: Exchanging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "StrandSort":
                return "Strand Sort:\nBest: O(n)\nAverage: O(n^2)\nWorst: O(n^2)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "StructureSort":
                return "Structure Sort (Simulated):\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "TagSort":
                return "Tag Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: No\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "ThreeWayMergeSort":
                return "3-Way Merge Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "TimSort":
                return "Tim Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Merging\nSerial: Yes\nAdaptive: No\nOnline: No";
            case "TreeSort":
                return "Tree Sort:\nBest: O(n log n)\nAverage: O(n log n)\nWorst: O(n log n)\nAuxiliary Space: O(n)\nStability: Yes\nIn-place: No\nRecursive: Yes\nComparison: Yes\nMethod: Selection\nSerial: Yes\nAdaptive: No\nOnline: No";
            default:
                return "Unknown Algorithm";
        }
    }

    private void startAnimation() {
        currentStep = 0;
        timer = new Timer(50, new ActionListener() { // Adjust the delay value here
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep < sortingSteps.size()) {
                    sortedPanel.setData(sortingSteps.get(currentStep));
                    sortedPanel.setHighlightedIndices(currentHighlight);
                    sortedPanel.repaint();
                    updateTextArea(currentStep); // Update the text area with the current step
                    currentStep++;
                } else {
                    timer.stop();
                    sortedPanel.setLabel("Sorted Data");
                }
            }
        });
        timer.start();
    }

    private void updateTextArea(int step) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unsorted Data: ").append(arrayToString(originalData)).append("\n\n");
        sb.append("Processing Data: ").append(arrayToStringWithHighlights(sortingSteps.get(step), currentHighlight))
                .append("\n\n");
        sb.append("Sorted Data: ").append(arrayToString(sortingSteps.get(sortingSteps.size() - 1))).append("\n");
        textArea.setText(sb.toString());
    }

    private String arrayToStringWithHighlights(int[] array, int[] highlights) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            if (isHighlighted(i, highlights)) {
                sb.append("*").append(array[i]).append("*");
            } else {
                sb.append(array[i]);
            }
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean isHighlighted(int index, int[] highlights) {
        for (int highlight : highlights) {
            if (highlight == index) {
                return true;
            }
        }
        return false;
    }

    private static class VisualizationPanel extends JPanel {
        private int[] data;
        private String label;
        private int[] highlightedIndices = new int[0];

        public VisualizationPanel(int[] data, String label) {
            this.data = data;
            this.label = label;
        }

        public void setData(int[] data) {
            this.data = data;
        }

        public void setHighlightedIndices(int[] highlightedIndices) {
            this.highlightedIndices = highlightedIndices;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / data.length;

            int max = data.length > 0 ? data[0] : 1;
            for (int value : data) {
                if (value > max) {
                    max = value;
                }
            }

            g.setColor(Color.BLACK);
            g.drawString(label, 10, 20);

            for (int i = 0; i < data.length; i++) {
                int barHeight = (int) (((double) data[i] / max) * height);
                int x = i * barWidth;
                int y = height - barHeight;
                if (isHighlighted(i)) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRect(x, y, barWidth - 2, barHeight);
            }
        }

        private boolean isHighlighted(int index) {
            for (int highlightedIndex : highlightedIndices) {
                if (highlightedIndex == index) {
                    return true;
                }
            }
            return false;
        }
    }

    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
