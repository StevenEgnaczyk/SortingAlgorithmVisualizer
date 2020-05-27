import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Visualizer extends  JFrame {
    public Visualizer() {
        super("Rectangle Drawing Demo");

        getContentPane().setBackground(Color.WHITE);
        setSize(1785, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    public void paint (Graphics g) {
        super.paint(g);
        try {
            drawRectangles(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Visualizer().setVisible(true);
            }
        });
    }

    void drawPixelBoard(Graphics g2d, ArrayList<pixelColor> pList) {
        int index = 0;
        for (pixelColor p : pList) {
            g2d.setColor(new Color(p.getR(), p.getG(), p.getB()));
            g2d.drawRect(index, 0, 1, 1000);
            index++;
        }
    }

    void drawPixelBoard(Graphics g2d, ArrayList<pixelColor> pList, int offset) {
        int index = offset;
        for (pixelColor p : pList) {
            g2d.setColor(new Color(p.getR(), p.getG(), p.getB()));
            g2d.drawRect(index, 0, 1, 1000);
            index++;
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
        pBoard pBoard = new pBoard();

        Graphics2D g2d = (Graphics2D) g;

        Collections.shuffle(pBoard.pList);
        drawPixelBoard(g2d, pBoard.pList);
        wait(2500);

        /*selectionSort(pBoard.pList, g2d, delay);
        wait(2500);

        Collections.shuffle(pBoard.pList);
        drawPixelBoard(g2d, pBoard.pList);
        wait(2500);

        bubbleSort(pBoard.pList, g2d, delay);
        drawPixelBoard(g2d, pBoard.pList);

        Collections.shuffle(pBoard.pList);
        drawPixelBoard(g2d, pBoard.pList);
        wait(2500);

        insertionSort(pBoard.pList, g2d, delay);
        wait(2500);

        Collections.shuffle(pBoard.pList);
        drawPixelBoard(g2d, pBoard.pList);
        wait(2500);

        mergeSort(pBoard.pList, 0, pBoard.pList.size() - 1, g2d, delay);
        drawPixelBoard(g2d, pBoard.pList);
        wait(2500);

         */
        //quickSort(pBoard.pList, 0, pBoard.pList.size()-1, g2d, delay);
        bucketSort(pBoard.pList, 25, g2d, delay);
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

    public void bucketSort(ArrayList<pixelColor> whole, int numberOfBuckets, Graphics g2d, int delay) {
        System.out.println(whole.size());
        ArrayList[] buckets = new ArrayList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new ArrayList();
        }

        for(pixelColor p : whole) {
            buckets[hash((int)p.getIndex())].add(p);
        }
        combineLists(buckets, g2d,delay);
        int pixelsDrawn = 0;
        for(ArrayList<pixelColor> bucket : buckets) {
            bubbleSort(bucket,g2d,delay, pixelsDrawn);
            pixelsDrawn+=bucket.size();
        }

        int i = 0;

        for (ArrayList<pixelColor> bucket : buckets) {
            for (pixelColor p : bucket) {
                whole.set(i++,p);
            }
        }
        drawPixelBoard(g2d, whole, i);
    }

    public int hash (int num) {
        return (int)(num/180);
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
}


