import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Visualizer extends  JFrame {

    public Visualizer() {

        //Set up basic parameters for the Visualizer
        super("Sorting Algorithm Visualizer");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setBackground(Color.WHITE);
        setSize(screenSize.width, screenSize.height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        //Run the visualizer
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Visualizer().setVisible(true);
            }
        });
    }

    public void paint (Graphics g) {

        //Paint the rectangles
        super.paint(g);
        try {
            drawRectangles(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Draw the pixel board
    void drawPixelBoard(Graphics graphicsComponent, ArrayList<pixelColor> pixelColorList) {

        //Start at the 0th index
        int index = 0;

        //For each pixelColor pColor in the pixelColorList
        for (pixelColor pColor : pixelColorList) {

            //Set the current color to that pColor
            graphicsComponent.setColor(new Color(pColor.getR(), pColor.getG(), pColor.getB()));

            //Draw and fill a rectangle with that color
            graphicsComponent.drawRect(index, 0, pColor.getWidth(), pColor.getHeight());
            graphicsComponent.fillRect(index, 0,pColor.getWidth(),pColor.getHeight());

            //Increase the index
            index += pColor.getWidth();

        }
    }

    //Draw the pixel board with an offset
    void drawPixelBoard(Graphics graphicsComponent, ArrayList<pixelColor> pixelColorList, int offset) {

        //Start at the offset index
        int index = offset;

        //For each pixelColor pColor in the pixelColorList
        for (pixelColor pColor : pixelColorList) {

            //Set the current color to that pColor
            graphicsComponent.setColor(new Color(pColor.getR(), pColor.getG(), pColor.getB()));

            //Draw and fill a rectangle with that color
            graphicsComponent.drawRect(index, 0, pColor.getWidth(), pColor.getHeight());
            graphicsComponent.fillRect(index, 0,pColor.getWidth(),pColor.getHeight());

            //Increase the index
            index += pColor.getWidth();

        }
    }

    //Simple wait command
    void wait(int time) {

        //Wait for time
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Draw the rectangles
    void drawRectangles(Graphics graphicsComponent) throws InterruptedException {

        //Set a default delay and size
        int delay = 10;
        int size = 10;

        //Make a pixelList with the given size and shuffle it
        pixelList visualizerPixelList = new pixelList(size);
        ArrayList<pixelColor> rawPixelList = visualizerPixelList.pList;
        Collections.shuffle(rawPixelList);

        //Create a 2D version of the Graphics Components
        Graphics2D graphicsComponent2D = (Graphics2D) graphicsComponent;
        MidiSoundPlayer soundComponent = new MidiSoundPlayer(rawPixelList.size());

        //Draw the pixelBoard
        drawPixelBoard(graphicsComponent2D, rawPixelList);

        while (true) {

            //Get the user's choice
            int userChoice = getUserChoice();

            //Do something based on the user's choice
            switch (userChoice) {

                //Randomize the Pixels
                case 0:
                    int shuffleNumber = 10;
                    for (int i = 0; i < shuffleNumber; i++) {
                        Collections.shuffle(rawPixelList);
                        soundComponent.makeSound(rawPixelList.get(0).getIndex());
                        drawPixelBoard(graphicsComponent2D, rawPixelList);
                        wait(delay);
                    }
                    break;

                //Sort the Pixels
                case 1:
                    int sortingAlgorithmSelection = getSortingAlgorithmChoice();
                    runSortingAlgorithm(sortingAlgorithmSelection, rawPixelList, graphicsComponent2D, delay, size);
                    break;

                //Change the delay
                case 2:
                    delay = getNewDelay(delay);
                    break;

                //Change the pixel size
                case 3:

                    //Get the new size
                    size = getNewSize(size);

                    //Clear the pixelList
                    rawPixelList.clear();

                    //Create a newPixelList with the new size and transfer it to the rawPixelList
                    pixelList newPixelList = new pixelList(size);
                    rawPixelList = newPixelList.pList;
                    soundComponent = new MidiSoundPlayer(rawPixelList.size());

                    //Re-draw the pixelBoard with the new size
                    drawPixelBoard(graphicsComponent2D, rawPixelList);
                    break;
                default:

                    //Print out an error and have the user try again
                    System.out.println("Please Try Again: Invalid Response");
                    System.exit(0);
                    break;
            }
        }
    }

    //Get's the user's choice
    public int getUserChoice () {

        //Print out options to the user
        System.out.println("Choose an Option: ");
        System.out.println("----------------------------------------------");
        System.out.println("(0): Randomize Pixels");
        System.out.println("(1): Sort Pixels");
        System.out.println("(2): Change Delay");
        System.out.println("(3): Change Pixel Size");

        //Return their choice
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    //Get's the selected sorting algorithm
    public int getSortingAlgorithmChoice() {

        //Print out the options to the user
        System.out.println("Which Sorting Algorithm Would you like to use?");
        System.out.println("----------------------------------------------");
        System.out.println("(0) - Selection Sort || (7) - Merge Sort  ");
        System.out.println("(1) - Insertion Sort || (8) - Bucket Sort");
        System.out.println("(2) - Gnome Sort     || (9) - Quick Sort");
        System.out.println("(3) - Shell Sort     || (10) - Bitonic Sort");
        System.out.println("(4) - Bubble Sort    || (11) - Heap Sort");
        System.out.println("(5) - Cocktail Sort  || (12) - Cycle Sort");
        System.out.println("(6) - Comb Sort      ||");

        //Return their choice
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    //Get's a new delay from the user
    public int getNewDelay(int delay) {

        //Print out the current delay and ask the user for a new one
        System.out.println("Your current delay is " + delay + " ms/per operation. What would you like the new delay to be?");

        //Return their choice
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    //Get's a new size from the user
    private int getNewSize(int size) {

        //Print out the current size and ask the user for a new one
        System.out.println("Your current pixel Width is " + size + " pixels. What would you like the new size to be?");

        //Return their choice
        Scanner userChoice = new Scanner(System.in);
        return userChoice.nextInt();
    }

    //Run's a particular sorting algorithm based on the user's selection
    public void runSortingAlgorithm(int sortingAlgorithmChoice, ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, int delay, int pixelWidth) {

        //Get the dimensions of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;

        //Set up the sound component of the visualizer
        MidiSoundPlayer soundComponent = new MidiSoundPlayer(rawPixelList.size());

        //Run a sorting algorithm based on sortingAlgorithmChoice
        switch (sortingAlgorithmChoice) {

            //SelectionSort
            case 0:

                //Run Selection sort
                System.out.println("Running Selection Sort");
                selectionSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List sorted using Selection Sort");
                break;

            //InsertionSort
            case 1:

                //Run Insertion sort
                System.out.println("Running Insertion Sort");
                insertionSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List sorted using Insertion Sort");
                break;

            //GnomeSort
            case 2:

                //Run Gnome Sort
                System.out.println("Running Gnome Sort");
                gnomeSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List sorted using Gnome Sort");
                break;

            //ShellSort
            case 3:

                //Run Shell Sort
                System.out.println("Running Shell Sort");
                shellSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List Sorted using Sort");
                break;

            //BubbleSort
            case 4:

                //Run Bubble Sort
                System.out.println("Running Bubble Sort");
                bubbleSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List sorted using Bubble Sort");
                break;

            //Cocktail-Shaker Sort
            case 5:
                System.out.println("Running Cocktail Sort");
                cocktailShakerSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List Sorted using Cocktail Sort");
                break;

            //CombSort
            case 6:

                //Run Cocktail-Shaker Sort
                System.out.println("Running Comb Sort");
                combSort(rawPixelList, graphicalComponent, delay);
                System.out.println("List sorted using Comb Sort");
                break;

            //MergeSort
            case 7:

                //Run Merge Sort
                System.out.println("Running Merge Sort");
                mergeSort(rawPixelList,0,rawPixelList.size()-1,graphicalComponent,delay);
                System.out.println("List sorted using Bubble Sort");;
                break;

            //BucketSort
            case 8:

                //Run Bucket Sort
                System.out.println("Running Bucket Sort");
                bucketSort(rawPixelList, 10, screenWidth, graphicalComponent, soundComponent, delay, pixelWidth);
                System.out.println("List sorted using Bucket Sort");
                break;

            //QuickSort
            case 9:

                //Run Quick Sort
                System.out.println("Running Quick Sort");
                quickSort(rawPixelList,0,rawPixelList.size()-1,graphicalComponent,delay);
                System.out.println("List sorted using Quick Sort");
                break;

            //BitonicSort
            case 10:

                //Run Bitonic Sort
                System.out.println("Running Bitonic Sort");
                bitonicSort(rawPixelList,0,rawPixelList.size(),0, graphicalComponent, soundComponent, delay);
                System.out.println("List sorted using Bitonic Sort");
                break;

            //HeapSort
            case 11:

                //Run Heap Sort
                System.out.println("Running Heap Sort");
                heapSort(rawPixelList, graphicalComponent, delay);
                System.out.println("List sorted using Heap Sort");
                break;

            //CycleSort
            case 12:

                //Run Cycle Sort
                System.out.println("Running Cycle Sort");
                cycleSort(rawPixelList, graphicalComponent, delay);
                System.out.println("List sorted using Cycle Sort");
                break;

            //Default
            default:
                System.out.println("Not a Valid Argument. Try Again");
                break;
        }
    }

    //Run's SelectionSort
    public void selectionSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get information about the rawPixelList
        int pixelListSize = rawPixelList.size();

        //Set up some tracker variables for later
        int minIndex;
        int minLocation;
        int jLocation;

        //Loop through the list
        for (int i = 0; i < pixelListSize - 1; i++) {

            //Set the minimumIndex and minLocation equal to the first element
            minIndex = rawPixelList.get(i).getIndex();
            minLocation = rawPixelList.get(minIndex).getIndex();

            //Loop from i + 1 to the end of the list
            for (int j = i + 1; j < pixelListSize; j++) {

                //Store the location of this pixelColor
                jLocation = rawPixelList.get(j).getIndex();

                //If the location of the j color is less than the minimum color
                if (jLocation < minLocation) {

                    //Set the minimumIndex and location equal to the j color
                    minIndex = j;
                    minLocation = rawPixelList.get(minIndex).getIndex();
                }
            }

            //Swap the currentBottom with the minimum color
            pixelColor tempPixel = rawPixelList.get(minIndex);
            pixelColor currentBottom = rawPixelList.get(i);
            rawPixelList.set(minIndex, currentBottom);
            rawPixelList.set(i, tempPixel);

            //Play a sound based on the index
            soundComponent.makeSound(tempPixel.getIndex());

            //Redraw the pixelBoard
            drawPixelBoard(graphicalComponent, rawPixelList);
            wait(delay);
        }
    }

    //Run's InsertionSort
    public void insertionSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the size of the Pixel List
        int pixelListSize = rawPixelList.size();

        //Loop through the size of the Pixel List starting from 1
        for (int i = 1; i < pixelListSize; i++) {

            //Set a keycColor equal to the Color at that index
            pixelColor keyColor = rawPixelList.get(i);

            //Start j from one index below from i
            int j = i - 1;

            //While j is greater than 0 and the Color at J is greater than the keyColor
            while (j >= 0 && rawPixelList.get(j).getIndex() > keyColor.getIndex()) {

                //Shift the Color at J up one
                rawPixelList.set(j + 1, rawPixelList.get(j));

                //Move J down one
                j = j - 1;

                //Redraw the pixelBoard, play a sound, and wait
                drawPixelBoard(graphicalComponent, rawPixelList);
                soundComponent.makeSound(rawPixelList.get(j+1).getIndex());
                wait(delay);
            }

            //After all is said and done, set the j + 1 color to the keyColor
            rawPixelList.set(j + 1, keyColor);

            //Redraw the pixelBoard, play a sound, and wait
            drawPixelBoard(graphicalComponent,rawPixelList);
            soundComponent.makeSound(keyColor.getIndex());
            wait(delay);
        }
    }

    //Run's GnomeSort
    public void gnomeSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the size of the List
        int pixelListSize = rawPixelList.size();

        //Start the currentIndex at 0
        int currentIndex = 0;

        //Loop through from currentIndex to the end of the list
        while (currentIndex < pixelListSize) {

            //If the currentIndex is 0 increase it
            if (currentIndex == 0)
                currentIndex++;

            //If the current Color is greater than the 1 before it, increase the currentIndex
            if (rawPixelList.get(currentIndex).getIndex() >= rawPixelList.get(currentIndex-1).getIndex())
                currentIndex++;

            //Else swap the two values
            else {

                //Swap the values
                pixelColor temp;
                temp = rawPixelList.get(currentIndex);
                rawPixelList.set(currentIndex, rawPixelList.get(currentIndex-1));
                rawPixelList.set(currentIndex-1, temp);

                //Draw the pixelBoard, play a sound, and wait
                drawPixelBoard(graphicalComponent, rawPixelList);
                soundComponent.makeSound(currentIndex);
                wait(delay);

                //Decrease the currentIndex
                currentIndex--;
            }
        }
    }

    //Run's shellSort
    public void shellSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the size of the List
        int pixelListSize = rawPixelList.size();

        //Loop from the midway point of the list down to 0 by a factor of 2
        for(int gap = (pixelListSize/2); gap > 0; gap/=2) {

            //Loop from the gap we just calculated to the size of the list
            for (int i = gap; i < pixelListSize; i++) {

                //Get the Color at i
                pixelColor temp = rawPixelList.get(i);

                int j;

                //While the Color at J is greater than the gap index and also the element at J-Gap is greater than the temp element
                for(j = i; j >= gap && rawPixelList.get(j-gap).getIndex() > temp.getIndex(); j-= gap) {

                    //Set the Color at J equal to the Color at the element J-Gap
                    rawPixelList.set(j,rawPixelList.get(j-gap));

                    //Redraw the board, play the sound, and then wait
                    drawPixelBoard(graphicalComponent,rawPixelList);
                    soundComponent.makeSound(rawPixelList.get(j).getIndex());
                    wait(delay);
                }

                //Set the Color at J equal to the temporary Color
                rawPixelList.set(j,temp);
            }
        }
    }

    //Run's bubbleSort
    public void bubbleSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the size of the list
        int pixelListSize = rawPixelList.size();

        //Loop through the list except the last element
        for (int i = 0; i < pixelListSize - 1; i++) {

            //Loop from the start of the list to the size of the list minus i
            for (int j = 0; j < pixelListSize - i - 1; j++) {

                //If the Color at J is greater than the one at the Color right above it
                if (rawPixelList.get(j).getIndex() > rawPixelList.get(j + 1).getIndex()) {

                    //Swap the two colors
                    pixelColor tempColor = rawPixelList.get(j + 1);
                    rawPixelList.set(j + 1, rawPixelList.get(j));
                    rawPixelList.set(j, tempColor);

                    //Play a sound, redraw the pixelBoard, and wait
                    drawPixelBoard(graphicalComponent,rawPixelList);
                    soundComponent.makeSound(rawPixelList.get(j).getIndex());
                    wait(delay);
                }
            }
        }
    }

    //Run's BubbleSort with an offset
    public void bubbleSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay, int offset) {

        //Get the size of the list
        int pixelListSize = rawPixelList.size();

        //Loop through the list except the last element
        for (int i = 0; i < pixelListSize - 1; i++) {

            //Loop from the start of the list to the size of the list minus i
            for (int j = 0; j < pixelListSize - i - 1; j++) {

                //If the Color at J is greater than the one at the Color right above it
                if (rawPixelList.get(j).getIndex() > rawPixelList.get(j + 1).getIndex()) {

                    //Swap the two colors
                    pixelColor tempColor = rawPixelList.get(j + 1);
                    rawPixelList.set(j + 1, rawPixelList.get(j));
                    rawPixelList.set(j, tempColor);

                    //Play a sound, redraw the pixelBoard, and wait
                    drawPixelBoard(graphicalComponent, rawPixelList, offset);
                    soundComponent.makeSound(rawPixelList.get(j).getIndex());
                    wait(delay);
                }
            }
        }
    }

    //Run's cocktailShakerSort
    private void cocktailShakerSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Set up variables to be used
        boolean swapped = true;
        int start = 0;
        int end = rawPixelList.size();

        //While we have swapped values
        while (swapped) {

            //Reset swapped to false
            swapped = false;

            //Loop from the start of the list to the end of the list
            for (int i = start; i < end-1; i++) {

                //If the Color at i is greater than the Color at i+1
                if (rawPixelList.get(i).getIndex() > rawPixelList.get(i+1).getIndex()) {

                    //Swap the Colors and set swapped to true
                    pixelColor temp = rawPixelList.get(i);
                    rawPixelList.set(i,rawPixelList.get(i+1));
                    rawPixelList.set(i+1,temp);
                    swapped = true;

                    //Redraw the pixelBoard, play a sound, and wait
                    drawPixelBoard(graphicalComponent,rawPixelList);
                    soundComponent.makeSound(rawPixelList.get(i).getIndex());
                    wait(delay);
                }
            }

            //If we haven't swapped any values, break out of the loop
            if (!swapped) {
                break;
            }

            //If not set swapped back to false and lower the endpoint
            swapped = false;
            end = end-1;

            //Loop from the endPoint - 1 to the start
            for (int i = end-1; i >= start; i--) {

                //If the Color at i is greater than the Color at i+1
                if (rawPixelList.get(i).getIndex() > rawPixelList.get(i+1).getIndex()) {

                    //Swap the Colors and set swapped to true
                    pixelColor temp = rawPixelList.get(i);
                    rawPixelList.set(i,rawPixelList.get(i+1));
                    rawPixelList.set(i+1,temp);
                    swapped = true;

                    //Redraw the pixelBoard, play a sound, and wait
                    drawPixelBoard(graphicalComponent, rawPixelList);
                    soundComponent.makeSound(rawPixelList.get(i).getIndex());
                    wait(delay);
                }
            }
        }
    }

    //Get's what the next gaps size should be for combSort
    public int getNextGap(int gap) {

        //Set the gap to be whatever the current is times 10, divided by 13
        gap = (gap*10)/13;

        //Return the max of gap and 1
        return Math.max(gap, 1);
    }

    public void combSort(ArrayList<pixelColor> whole, Graphics g2d, int delay) {
        int n = whole.size();

        int gap = n;

        boolean swapped = true;

        while (gap != 1 || swapped) {
            gap = getNextGap(gap);

            swapped = false;

            for (int i = 0; i < (n-gap); i++) {
                if (whole.get(i).getIndex() > whole.get(i+gap).getIndex()) {
                    pixelColor temp = whole.get(i);

                    whole.set(i,whole.get(i+gap));
                    drawPixelBoard(g2d, whole);
                    wait(delay);

                    whole.set(i+gap, temp);
                    drawPixelBoard(g2d, whole);
                    wait(delay);

                    swapped = true;
                }
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

    //BucketSort Methods

    //Combine's two lists together
    public void combineLists(ArrayList[] lists, Graphics g2d, int delay) {

        //Create an arrayList of pixelColors to store both lists
        ArrayList<pixelColor> whole = new ArrayList<>();

        //For each list that is contained within the lists ArrayList
        for(ArrayList<pixelColor> list: lists) {

            //Copy the pixelColors into the main arrayList and redraw the pixelBoard
            for(pixelColor p : list) {
                whole.add(p);
                wait(delay);
                drawPixelBoard(g2d,whole);
            }
        }
    }

    public void bucketSort(ArrayList<pixelColor> whole, int numberOfBuckets, int size, Graphics g2d, MidiSoundPlayer soundComponent, int delay, int pixelSize) {

        int hash;

        ArrayList[] buckets = new ArrayList[numberOfBuckets];
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new ArrayList();
        }

        for(pixelColor p : whole) {
            hash = hash(p.getIndex(), (size/pixelSize), numberOfBuckets);
            buckets[hash].add(p);
        }
        combineLists(buckets, g2d,delay);
        int pixelsDrawn = 0;
        for(ArrayList<pixelColor> bucket : buckets) {
            bubbleSort(bucket, g2d, soundComponent, delay, pixelsDrawn);
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

    public int hash (int num, int max, int numBuckets) {
        float value = (float)num/max;
        System.out.println(value);
        int bucketNum = (int)(value * numBuckets);
        System.out.println(bucketNum);
        return bucketNum;

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

    public void compAndSwap(ArrayList<pixelColor> whole, int i, int j, int dir, Graphics g2d, MidiSoundPlayer player, int delay) {
        boolean direction = (dir == 1);
        if (direction == (whole.get(i).getIndex() >= whole.get(j).getIndex())) {

            pixelColor temp = whole.get(i);
            whole.set(i,whole.get(j));
            whole.set(j, temp);

            drawPixelBoard(g2d, whole);
            player.makeSound(whole.get(i).index);
            wait(delay);
        }
    }

    public void bitonicMerge(ArrayList<pixelColor> whole, int low, int cnt, int dir, Graphics g2d, MidiSoundPlayer player, int delay) {
        if (cnt > 1) {
            int k = cnt/2;
            for (int i = low; i < (low+k); i++)
                compAndSwap(whole,i,i+k,dir, g2d, player, delay);
            bitonicMerge(whole,low,k,dir,g2d, player, delay);
            bitonicMerge(whole,low+k,k,dir,g2d, player, delay);
        }
    }

    void bitonicSort(ArrayList<pixelColor> whole, int low, int cnt, int dir, Graphics g2d, MidiSoundPlayer player, int delay) {
        if (cnt > 1) {
            int k = cnt/2;
            bitonicSort(whole, low, k, 1, g2d, player, delay);
            bitonicSort(whole, low+k, k, 0, g2d, player, delay);
            bitonicMerge(whole,low,cnt,dir,g2d, player, delay);
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

    public void cycleSort(ArrayList<pixelColor> whole, Graphics g2d, int delay) {
        int writes = 0;
        int n = whole.size();

        for (int cycle_Start = 0; cycle_Start <= (n-2) ; cycle_Start++) {
            pixelColor item = whole.get(cycle_Start);

            int pos = cycle_Start;

            for (int i = cycle_Start + 1; i < n; i++) {
                if (whole.get(i).getIndex() < item.getIndex()) {
                    pos++;
                }
            }

            if (pos == cycle_Start) {
                continue;
            }

            while (item.getIndex() == whole.get(pos).getIndex()) {
                pos+=1;
            }

            if (pos != cycle_Start) {
                pixelColor temp = item;
                item = whole.get(pos);
                whole.set(pos,temp);

                drawPixelBoard(g2d,whole);
                wait(delay);

                writes++;
            }

            while (pos != cycle_Start) {
                pos = cycle_Start;

                for (int i = cycle_Start+1; i < n; i++) {
                    if (whole.get(i).getIndex() < item.getIndex()) {
                        pos+=1;
                    }
                }

                while (item == whole.get(pos)) {
                    pos+=1;
                }

                if (item != whole.get(pos)) {
                    pixelColor temp = item;
                    item = whole.get(pos);
                    whole.set(pos,temp);

                    drawPixelBoard(g2d,whole);
                    wait(delay);

                    writes++;
                }
            }
        }
    }
}


