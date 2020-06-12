import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Visualizer extends  JFrame {
    public Visualizer() {
        super("Rectangle Drawing Demo");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.WHITE);
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Visualizer().setVisible(true);
            }
        });
    }

    public void paint (Graphics g) {
        super.paint(g);
        try {
            drawRectangles(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void drawPixelBoard(Graphics g2d, ArrayList<pixelColor> pList) {
        int index = 0;
        for (pixelColor p : pList) {
            //System.out.println(p.toString());
            g2d.setColor(new Color(p.getR(), p.getG(), p.getB()));
            g2d.drawRect(index, 0, p.getWidth(), p.getHeight());
            g2d.fillRect(index, 0,p.getWidth(),p.getHeight());
            index+=p.getWidth();
        }
    }

    void drawPixelBoard(Graphics g2d, ArrayList<pixelColor> pList, int offset) {
        int index = offset;
        for (pixelColor p : pList) {
            g2d.setColor(new Color(p.getR(), p.getG(), p.getB()));
            g2d.drawRect(index, 0, p.getWidth(), p.getHeight());
            g2d.fillRect(index, 0,p.getWidth(),p.getHeight());
            index+=p.getWidth();
        }
    }

    void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void drawRectangles(Graphics g) throws InterruptedException {
        int delay = 10;
        int size = 10;
        pBoard pBoard = new pBoard(size);
        Graphics2D g2d = (Graphics2D) g;
        Collections.shuffle(pBoard.pList);
        drawPixelBoard(g2d, pBoard.pList);
        while (true) {
            int userChoice = getUserChoice();
            switch (userChoice) {
                case 0:
                    Collections.shuffle(pBoard.pList);
                    drawPixelBoard(g2d, pBoard.pList);
                    break;
                case 1:
                    int sortingAlgorithmSelection = getSortingAlgorithmChoice();
                    runSortingAlgorithm(sortingAlgorithmSelection, pBoard.pList, g2d, delay, size);
                    break;
                case 2:
                    delay = getNewDelay(delay);
                    break;
                case 3:
                    pBoard.pList.clear();
                    pBoard = new pBoard(getNewSize(size));
                    drawPixelBoard(g2d,pBoard.pList);
                default:

                    break;
            }
        }
    }

    public int getUserChoice () {
        System.out.println("Choose an Option: ");
        System.out.println("----------------------------------------------");
        System.out.println("(0): Randomize Pixels");
        System.out.println("(1): Sort Pixels");
        System.out.println("(2): Change Delay");
        System.out.println("(3): Change Pixel Size");

        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    public int getSortingAlgorithmChoice() {
        System.out.println("Which Sorting Algorithm Would you like to use?");
        System.out.println("----------------------------------------------");
        System.out.println("(0) - Selection Sort || (8) - Tim Sort");
        System.out.println("(1) - Insertion Sort || (9) - Bucket Sort");
        System.out.println("(2) - Gnome Sort     || (10) - Quick Sort");
        System.out.println("(3) - Shell Sort     || (11) - Bitonic Sort");
        System.out.println("(4) - Bubble Sort    || (12) - Tree Sort");
        System.out.println("(5) - Cocktail Sort  || (13) - Heap Sort");
        System.out.println("(6) - Comb Sort      || (14) - Smooth Sort");
        System.out.println("(7) - Merge Sort     || (15) - Pidgeonhole Sort");

        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    public int getNewDelay(int delay) {
        System.out.println("Your current delay is " + delay + " ms/per operation. What would you like the new delay to be?");
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    private int getNewSize(int size) {
        System.out.println("Your current pixel Width is " + size + " pixels. What would you like the new size to be?");
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    public void runSortingAlgorithm(int sortingAlgorithmChoice, ArrayList<pixelColor> pcArrayList, Graphics g2d, int delay, int pixelWidth) {
        switch (sortingAlgorithmChoice) {
            case 0:
                System.out.println("Running Selection Sort");
                selectionSort(pcArrayList, g2d, delay);
                System.out.println("List sorted using Selection Sort");
                break;
            case 1:
                System.out.println("Running Insertion Sort");
                insertionSort(pcArrayList,g2d,delay);
                System.out.println("List sorted using Insertion Sort");
                break;
            case 2:
                System.out.println("Running Gnome Sort");
                gnomeSort(pcArrayList,g2d,delay);
                System.out.println("List sorted using Gnome Sort");
                break;
            case 3:
                System.out.println("Running Shell Sort");
                shellSort(pcArrayList,g2d,delay);
                System.out.println("List Sorted using Sort");
                break;
            case 4:
                System.out.println("Running Bubble Sort");
                bubbleSort(pcArrayList,g2d,delay);
                System.out.println("List sorted using Bubble Sort");
                break;
            case 5:
                System.out.println("Running Cocktail Sort");
                cocktailSort(pcArrayList,g2d,delay);
                System.out.println("List Sorted using Cocktail Sort");
                break;
            case 6:
                System.out.println("Running Comb Sort");
                //radixSort(pcArrayList, pcArrayList.size(), g2d, delay);
                System.out.println("List sorted using Comb Sort");
                break;
            case 7:
                System.out.println("Running Merge Sort");
                mergeSort(pcArrayList,0,pcArrayList.size()-1,g2d,delay);
                System.out.println("List sorted using Bubble Sort");;
                break;
            case 8:
                System.out.println("Running Tim Sort");
                //bucketSort(pcArrayList, pcArrayList.size()/10, g2d, delay, pixelWidth);
                System.out.println("List sorted using Tim Sort");
                break;
            case 9:
                System.out.println("Running Bucket Sort");
                bucketSort(pcArrayList, pcArrayList.size()/10, g2d, delay, pixelWidth);
                System.out.println("List sorted using Bucket Sort");
                break;
            case 10:
                System.out.println("Running Quick Sort");
                quickSort(pcArrayList,0,pcArrayList.size()-1,g2d,delay);
                System.out.println("List sorted using Quick Sort");
            case 11:
                System.out.println("Running Bitonic Sort");
                //quickSort(pcArrayList,0,pcArrayList.size()-1,g2d,delay);
                System.out.println("List sorted using Bitonic Sort");
                break;
            case 12:
                System.out.println("Running Tree Sort");
                //heapSort(pcArrayList, g2d, delay);
                System.out.println("List sorted using Tree Sort");
                break;
            case 13:
                System.out.println("Running Heap Sort");
                heapSort(pcArrayList, g2d, delay);
                System.out.println("List sorted using Heap Sort");
            case 14:
                System.out.println("Running Smooth Sort");
                //heapSort(pcArrayList, g2d, delay);
                System.out.println("List sorted using Smooth Sort");
            case 15:
                System.out.println("Running Pidgeonhole Sort");
                heapSort(pcArrayList, g2d, delay);
                System.out.println("List sorted using Pidgeonhole Sort");
            default:
                System.out.println("Not a Valid Argument. Try Again");
        }
    }

    private void cocktailSort(ArrayList<pixelColor> whole, Graphics g2d, int delay) {

        boolean swapped = true;
        int start = 0;
        int end = whole.size();

        while (swapped) {
            swapped = false;

            for (int i = start; i < end-1; i++) {
                if (whole.get(i).getIndex() > whole.get(i+1).getIndex()) {
                    pixelColor temp = whole.get(i);
                    whole.set(i,whole.get(i+1));
                    whole.set(i+1,temp);
                    swapped = true;
                    drawPixelBoard(g2d,whole);
                    wait(delay);
                }
            }

            if (!swapped) {
                break;
            }

            swapped = false;

            end = end-1;

            for (int i = end-1; i >= start; i--) {
                if (whole.get(i).getIndex() > whole.get(i+1).getIndex()) {
                    pixelColor temp = whole.get(i);
                    whole.set(i,whole.get(i+1));
                    whole.set(i+1,temp);
                    swapped = true;
                    drawPixelBoard(g2d,whole);
                    wait(delay);
                }
            }
        }
    }

    public void selectionSort(ArrayList<pixelColor> pList, Graphics g2d, int delay) {

        int n = pList.size();
        int jLocation;
        int minLocation;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = pList.get(i).getIndex();
            minLocation = pList.get(minIndex).getIndex();
            for (int j = i + 1; j < n; j++) {
                jLocation = pList.get(j).getIndex();
                if (jLocation < minLocation) {
                    drawPixelBoard(g2d, pList);
                    minIndex = j;
                    minLocation = pList.get(minIndex).getIndex();
                }
            }
            pixelColor tempPixel = pList.get(minIndex);
            pixelColor currentBottom = pList.get(i);

            pList.set(minIndex, currentBottom);
            pList.set(i, tempPixel);

            drawPixelBoard(g2d,pList);

            wait(delay);
        }
    }

    public void insertionSort(ArrayList<pixelColor> pList, Graphics g2d, int delay) {
        int n = pList.size();
        for (int i = 1; i < n; i++) {
            pixelColor key = pList.get(i);
            int j = i - 1;

            while (j >= 0 && pList.get(j).getIndex() > key.getIndex()) {
                pixelColor currentColor = pList.get(j);
                pixelColor replaceingColor = pList.get(j + 1);
                pList.set(j + 1, pList.get(j));
                j = j - 1;
            }
            wait(delay);
            pList.set(j + 1, key);
            drawPixelBoard(g2d,pList);
        }
    }

    public void bubbleSort(ArrayList<pixelColor> pList, Graphics g2d, int delay) {
        for (int i = 0; i < pList.size() - 1; i++) {
            for (int j = 0; j < pList.size() - i - 1; j++) {
                if (pList.get(j).getIndex() > pList.get(j + 1).getIndex()) {
                    pixelColor tempColor = pList.get(j + 1);
                    pList.set(j + 1, pList.get(j));
                    pList.set(j, tempColor);
                }
            }
            drawPixelBoard(g2d,pList);
            wait(delay);
        }
    }

    public void bubbleSort(ArrayList<pixelColor> pList, Graphics g2d, int delay, int offset) {
        for (int i = 0; i < pList.size() - 1; i++) {
            for (int j = 0; j < pList.size() - i - 1; j++) {
                if (pList.get(j).getIndex() > pList.get(j + 1).getIndex()) {
                    pixelColor tempColor = pList.get(j + 1);
                    pList.set(j + 1, pList.get(j));
                    pList.set(j, tempColor);
                }
            }
            drawPixelBoard(g2d,pList, offset);
            wait(delay);
        }
    }

    public void mergeSort(ArrayList<pixelColor> whole, int l, int r, Graphics g2d, int delay) {
        if (l < r) {
            int m = (l + r) / 2;

            mergeSort(whole, l, m, g2d, delay);
            mergeSort(whole, m + 1, r, g2d, delay);

            merge(whole, l, m, r, g2d, delay);
        }
    }

    public void merge(ArrayList<pixelColor> whole, int l, int m, int r, Graphics g2d, int delay) {

        wait(10);
        int n1 = (m - l + 1);
        int n2 = (r - m);

        ArrayList<pixelColor> left = new ArrayList<>();
        ArrayList<pixelColor> right = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            left.add(whole.get(l + i));
        }
        for (int j = 0; j < n2; j++) {
            right.add(whole.get(m + 1 + j));
        }

        int i = 0;
        int j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (left.get(i).getIndex() <= right.get(j).getIndex()) {
                whole.set(k, left.get(i));
                drawPixelBoard(g2d, whole);
                i++;
            } else {
                whole.set(k, right.get(j));
                drawPixelBoard(g2d, whole);
                j++;
            }
            k++;
        }
        while (i < n1) {
            whole.set(k, left.get(i));
            drawPixelBoard(g2d, whole);
            i++;
            k++;
        }
        while (j < n2) {
            whole.set(k, right.get(j));
            drawPixelBoard(g2d, whole);
            j++;
            k++;
        }
        wait(delay);
    }

    public int partition(ArrayList<pixelColor> whole, int low, int high, Graphics g2d, int delay) {
        pixelColor pivot = whole.get(high);
        int i = (low-1);
        for (int j = low; j < high; j++) {
            if (whole.get(j).getIndex() < pivot.getIndex()) {
                i++;
                pixelColor temp = whole.get(i);
                whole.set(i, whole.get(j));
                whole.set(j, temp);
                drawPixelBoard(g2d,whole);
            }
        }

        pixelColor temp = whole.get(i+1);
        whole.set(i+1,whole.get(high));
        whole.set(high,temp);
        drawPixelBoard(g2d,whole);
        wait(delay);

        return (i+1);
    }

    public void quickSort (ArrayList<pixelColor> whole, int low, int high, Graphics g2d, int delay) {
        if (low < high) {
            int pi = partition(whole,low,high, g2d, delay);

            quickSort(whole,low,pi-1, g2d, delay);
            quickSort(whole,pi+1,high, g2d, delay);
        }
    }

    public void heapSort(ArrayList<pixelColor> whole, Graphics g2d, int delay) {
        int n = whole.size();

        for (int i = (n/2 -1); i >= 0; i--) {
            heapify(whole, n, i, g2d, delay);
        }

        for (int i = n-1; i > 0; i--) {
            pixelColor temp = whole.get(0);
            whole.set(0, whole.get(i));
            wait(delay);
            drawPixelBoard(g2d, whole);
            whole.set(i,temp);
            wait(delay);
            drawPixelBoard(g2d, whole);

            heapify(whole, i, 0, g2d, delay);
        }
    }

    public void heapify(ArrayList<pixelColor> whole, int n, int i, Graphics g2d, int delay) {
        int largest = i;
        int l = (2*i) + 1;
        int r = (2*i) + 2;

        if (l < n && whole.get(l).getIndex() > whole.get(largest).getIndex()) {
            largest = l;
        }

        if (r < n && whole.get(r).getIndex() > whole.get(largest).getIndex()) {
            largest = r;
        }

        if (largest != i) {
            pixelColor swap = whole.get(i);
            whole.set(i,whole.get(largest));
            drawPixelBoard(g2d, whole);
            wait(delay);
            whole.set(largest,swap);
            drawPixelBoard(g2d, whole);
            wait(delay);
            heapify(whole,n,largest, g2d, delay);
        }
    }

    public static pixelColor getMax(ArrayList<pixelColor> whole, int n) {

        pixelColor mx = whole.get(0);

        for(pixelColor p : whole) {
            if (p.getIndex() > mx.getIndex()) mx = p;
        }

        return mx;
    }

    public void bucketSort(ArrayList<pixelColor> whole, int numberOfBuckets, Graphics g2d, int delay, int pixelSize) {
        ArrayList[] buckets = new ArrayList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new ArrayList();
        }

        for(pixelColor p : whole) {
            buckets[hash((int)p.getIndex(),numberOfBuckets)].add(p);
        }
        combineLists(buckets, g2d,delay);
        int pixelsDrawn = 0;
        for(ArrayList<pixelColor> bucket : buckets) {
            bubbleSort(bucket,g2d,delay, pixelsDrawn);
            pixelsDrawn+=(bucket.size()*pixelSize);
        }

        int i = 0;

        for (ArrayList<pixelColor> bucket : buckets) {
            for (pixelColor p : bucket) {
                whole.set(i++,p);
            }
        }
        drawPixelBoard(g2d, whole, pixelsDrawn);
    }

    public int hash (int num, int numBuckets) {
        return (int)(num/numBuckets);
    }

    public void shellSort(ArrayList<pixelColor> whole, Graphics g2d, int delay) {
        int n = whole.size();

        for(int gap = (n/2); gap > 0; gap/=2) {
            for (int i = gap; i < n; i+=1) {
                pixelColor temp = whole.get(i);

                int j;
                for(j = i; j >= gap && whole.get(j-gap).getIndex() > temp.getIndex(); j-= gap) {
                    whole.set(j,whole.get(j-gap));
                    drawPixelBoard(g2d,whole);
                    wait(delay);
                }
                whole.set(j,temp);
            }
        }
    }

    public void combineLists(ArrayList[] lists, Graphics g2d, int delay) {
        ArrayList<pixelColor> whole = new ArrayList<>();
        for(ArrayList<pixelColor> list: lists) {
            for(pixelColor p : list) {
                whole.add(p);
                wait(delay);
                drawPixelBoard(g2d,whole);
            }
        }
    }

    void gnomeSort(ArrayList<pixelColor> whole, Graphics g2d, int delay) {

        int n = whole.size();

        int index = 0;

        while (index < n) {
            if (index == 0)
                index++;
            if (whole.get(index).getIndex() >= whole.get(index-1).getIndex())
                index++;
            else {
                pixelColor temp;
                temp = whole.get(index);
                whole.set(index, whole.get(index-1));
                whole.set(index-1, temp);
                index--;
                drawPixelBoard(g2d, whole);
                wait(delay);
            }
        }
    }
}


