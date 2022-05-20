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
                        wait(100);
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
                combSort(rawPixelList, graphicalComponent, soundComponent, delay);
                System.out.println("List sorted using Comb Sort");
                break;

            //MergeSort
            case 7:

                //Run Merge Sort
                System.out.println("Running Merge Sort");
                mergeSort(rawPixelList, 0, rawPixelList.size()-1, graphicalComponent, soundComponent, delay);
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
                quickSort(rawPixelList, 0, rawPixelList.size()-1, graphicalComponent, soundComponent, delay);
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
                heapSort(rawPixelList, graphicalComponent, soundComponent, delay);
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

    //CombSort methods

    //Returns what the next gaps size should be for combSort
    public int getNextGap(int gap) {

        //Set the gap to be whatever the current is times 10, divided by 13
        gap = (gap*10)/13;

        //Return the max of gap and 1
        return Math.max(gap, 1);
    }

    //Run CombSort
    public void combSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Define variables to be used in the program
        int pixelListSize = rawPixelList.size();
        int gap = pixelListSize;
        boolean swapped = true;

        //While the gap is not 1, or we've swapped Colors
        while (gap != 1 || swapped) {

            //Get the next gap and set swapped to false
            gap = getNextGap(gap);
            swapped = false;

            //Loop through the list
            for (int i = 0; i < (pixelListSize-gap); i++) {

                //If the Color at index i is greater than the Color at index i + gap
                if (rawPixelList.get(i).getIndex() > rawPixelList.get(i + gap).getIndex()) {

                    //Swap the 2 Colors and set swapped to true
                    pixelColor temp = rawPixelList.get(i);
                    rawPixelList.set(i,rawPixelList.get(i+gap));
                    rawPixelList.set(i+gap, temp);
                    swapped = true;

                    //Redraw the pixelBoard, play a sound, and wait
                    drawPixelBoard(graphicalComponent, rawPixelList);
                    soundComponent.makeSound(rawPixelList.get(i).getIndex());
                    wait(delay);
                }
            }
        }
    }

    //MergeSort methods

    //Run MergeSort
    public void mergeSort(ArrayList<pixelColor> rawPixelList, int leftMostIndex, int rightMostIndex, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //If the leftMostIndex is less than the rightMostIndex
        if (leftMostIndex < rightMostIndex) {

            //Get the middle-most index
            int middleMostIndex = (leftMostIndex + rightMostIndex) / 2;

            //Recursively mergeSort the left and right halves of the list
            mergeSort(rawPixelList, leftMostIndex, middleMostIndex, graphicalComponent, soundComponent, delay);
            mergeSort(rawPixelList, middleMostIndex + 1, rightMostIndex, graphicalComponent, soundComponent, delay);

            //Merge the two recursively sorted sublists
            merge(rawPixelList, leftMostIndex, middleMostIndex, rightMostIndex, graphicalComponent, soundComponent, delay);
        }
    }

    //Nerge Command
    public void merge(ArrayList<pixelColor> rawPixelList, int leftMostIndex, int middleMostIndex, int rightMostIndex, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the size of the left and right Color lists
        int leftSize = (middleMostIndex - leftMostIndex + 1);
        int rightSize = (rightMostIndex - middleMostIndex);

        //Create new arrayLists to store the left and right halves of the list
        ArrayList<pixelColor> left = new ArrayList<>();
        ArrayList<pixelColor> right = new ArrayList<>();

        //Copy the elements from the main pixelList into these 2 temporary arrayLists
        for (int i = 0; i < leftSize; i++) {
            left.add(rawPixelList.get(leftMostIndex + i));
        }
        for (int j = 0; j < rightSize; j++) {
            right.add(rawPixelList.get(middleMostIndex + 1 + j));
        }

        //Create indexing variables
        int leftIndexer = 0;
        int rightIndexer = 0;
        int mainIndexer = leftMostIndex;

        //Loop through until we hit the end of one of the temp lists
        while (leftIndexer < leftSize && rightIndexer < rightSize) {

            //If the Color in the left list is smaller than the color in the right list at this index
            if (left.get(leftIndexer).getIndex() <= right.get(rightIndexer).getIndex()) {

                //Add the color from the left list to the main list, play a sound, and redraw the pixelBoard
                rawPixelList.set(mainIndexer, left.get(leftIndexer));
                soundComponent.makeSound(left.get(leftIndexer).getIndex());
                drawPixelBoard(graphicalComponent, rawPixelList);

                //Increase the leftIndexer and wait
                leftIndexer++;
                wait(delay);

            } else {

                //Add the color from the right list to the main list, play a sound, and redraw the pixelBoard
                rawPixelList.set(mainIndexer, right.get(rightIndexer));
                soundComponent.makeSound(right.get(rightIndexer).getIndex());
                drawPixelBoard(graphicalComponent, rawPixelList);

                //Increase the rightIndexer and wait
                rightIndexer++;
                wait(delay);
            }

            //Increase the mainIndexer
            mainIndexer++;
        }

        //While there are still elements left in the temp left list
        while (leftIndexer < leftSize) {

            //Copy them to the main list, play a sound, and redraw the pixelBoard
            rawPixelList.set(mainIndexer, left.get(leftIndexer));
            soundComponent.makeSound(left.get(leftIndexer).getIndex());
            drawPixelBoard(graphicalComponent, rawPixelList);

            //Increase the left and main indexes and wait
            leftIndexer++;
            mainIndexer++;
            wait(delay);
        }

        //While there are still elements left in the temp right list
        while (rightIndexer < rightSize) {

            //Copy them into the main list, play a sound, and redraw the pixelBoard
            rawPixelList.set(mainIndexer, right.get(rightIndexer));
            soundComponent.makeSound(right.get(rightIndexer).getIndex());
            drawPixelBoard(graphicalComponent, rawPixelList);

            //Increase the right and main indexes and wait
            rightIndexer++;
            mainIndexer++;
            wait(delay);
        }
    }

    //BucketSort Methods

    //Combine's two lists together
    public void combineLists(ArrayList[] lists, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Create an arrayList of pixelColors to store both lists
        ArrayList<pixelColor> whole = new ArrayList<>();

        //For each list that is contained within the lists ArrayList
        for(ArrayList<pixelColor> list: lists) {

            //Copy the pixelColors into the main arrayList, redraw the pixelBoard, and play a sound
            for(pixelColor p : list) {
                whole.add(p);
                wait(delay);
                soundComponent.makeSound(p.getIndex());
                drawPixelBoard(graphicalComponent,whole);
            }
        }
    }

    //Get's an items correct hash value
    public int hash (int colorIndex, int maxIndex, int numBuckets) {

        //Calculate the relative location of the Color in relation to the max value
        float value = ((float)colorIndex/maxIndex);

        //Calculate which bucket that Color should then be placed in and return it
        return (int)(value * numBuckets);

    }

    //Run's bucketSort
    public void bucketSort(ArrayList<pixelColor> rawPixelList, int numberOfBuckets, int pixelListSize, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay, int pixelSize) {

        //Create an integer to store the hash value
        int hash;

        //Create an arrayList to store the buckets
        ArrayList[] buckets = new ArrayList[numberOfBuckets];

        //Loop through the number of buckets and make an arrayList for each
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets[i] = new ArrayList();
        }

        //Loop through the rawPixelList
        for(pixelColor color : rawPixelList) {

            //Get the hash value for each color and add it to that particular arrayList
            hash = hash(color.getIndex(), (pixelListSize/pixelSize), numberOfBuckets);
            buckets[hash].add(color);
        }

        //Combine all of the lists into one
        combineLists(buckets, graphicalComponent, soundComponent, delay);

        //Create a variable to store the number of pixels drawn so far
        int pixelsDrawn = 0;

        //For each Bucket, bubbleSort the contents of that bucket
        for(ArrayList<pixelColor> bucket : buckets) {

            //Run bubbleSort with an offset and increase the pixelsDrawn
            bubbleSort(bucket, graphicalComponent, soundComponent, delay, pixelsDrawn);
            pixelsDrawn += (bucket.size()*pixelSize);
        }

        //Set up an indexing variable
        int mainListIndexer = 0;

        //For each bucket
        for (ArrayList<pixelColor> bucket : buckets) {
            //For each pixel in that bucket
            for (pixelColor pixelColor : bucket) {

                //Re-set that Color to its correct position in the main list
                rawPixelList.set(mainListIndexer++,pixelColor);
            }
        }

        //Redraw the pixelBoard
        drawPixelBoard(graphicalComponent, rawPixelList, pixelsDrawn);
    }

    //QuickSort Methods

    //Partition
    public int partition(ArrayList<pixelColor> rawPixelList, int low, int high, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Define the pivot pixelColor
        pixelColor pivot = rawPixelList.get(high);

        //Define the lowest index location
        int lowIndex = low;
        int highIndex = high;

        while (true) {

            //Move the lowIndex up until we hit a color greater than the Pivot Color
            while (rawPixelList.get(lowIndex).getIndex() < pivot.getIndex()) {
                lowIndex++;
            }

            //Move the highIndex down until we hit a color less than the Pivot Color
            while (rawPixelList.get(highIndex).getIndex() > pivot.getIndex()) {
                highIndex--;
            }

            //If the lowIndex has crossed the highIndex, break out of the loop
            if (lowIndex >= highIndex) {
                break;
            } else {

                //Swap the Colors at the lowIndex and highIndex
                pixelColor temp = rawPixelList.get(lowIndex);
                rawPixelList.set(lowIndex, rawPixelList.get(highIndex));
                rawPixelList.set(highIndex, temp);

                //Play a sound, redraw the pixelBoard, and wait
                soundComponent.makeSound(temp.getIndex());
                drawPixelBoard(graphicalComponent,rawPixelList);
                wait(delay);
            }
        }

        //Swap the Color at the lowIndex with the Color at the Pivot
        pixelColor temp = rawPixelList.get(lowIndex);
        rawPixelList.set(lowIndex, pivot);
        rawPixelList.set(pivot.getIndex(), temp);

        //Play a sound, redraw the pixelBoard, and wait
        soundComponent.makeSound(temp.getIndex());
        drawPixelBoard(graphicalComponent,rawPixelList);
        wait(delay);

        //Return the lowIndex
        return lowIndex;

    }

    //Run's quickSort
    public void quickSort (ArrayList<pixelColor> rawPixelList, int low, int high, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //If low is less than high (base case)
        if (low < high) {

            //Partition the list into two sections separated by pi
            int pi = partition(rawPixelList,low,high, graphicalComponent, soundComponent, delay);

            //QuickSort the left and right halves of the list respectively
            quickSort(rawPixelList,low,pi-1, graphicalComponent, soundComponent, delay);
            quickSort(rawPixelList,pi+1,high, graphicalComponent, soundComponent, delay);
        }
    }

    //Comp and Swap
    public void compAndSwap(ArrayList<pixelColor> rawPixelList, int leftColor, int rightColor, int dir, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the direction boolean
        boolean direction = (dir == 1);

        //If the direction boolean disagrees with the current ordering of colors
        if (direction == (rawPixelList.get(leftColor).getIndex() >= rawPixelList.get(rightColor).getIndex())) {

            //Swap the Colors at the left and right Indexes
            pixelColor temp = rawPixelList.get(leftColor);
            rawPixelList.set(leftColor,rawPixelList.get(rightColor));
            rawPixelList.set(rightColor, temp);

            //Play a sound, redraw the board, and wait
            soundComponent.makeSound(rawPixelList.get(leftColor).index);
            drawPixelBoard(graphicalComponent, rawPixelList);
            wait(delay);
        }
    }

    //BitonicMerge
    public void bitonicMerge(ArrayList<pixelColor> rawPixelList, int low, int count, int dir, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //If the count of elements is greater than 1
        if (count > 1) {

            //Set k equal to the midpoint of elements
            int k = count/2;

            //For each element in this range from low to low + k
            for (int i = low; i < (low+k); i++)

                //Compare and swap the elements at i with i + k
                compAndSwap(rawPixelList,i,i+k,dir, graphicalComponent, soundComponent, delay);

            //Bitonically Merge the left and right halves of the list
            bitonicMerge(rawPixelList, low, k, dir, graphicalComponent, soundComponent, delay);
            bitonicMerge(rawPixelList, low+k, k, dir, graphicalComponent, soundComponent, delay);
        }
    }

    //BitonicSort
    void bitonicSort(ArrayList<pixelColor> rawPixelList, int low, int count, int dir, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //If count is greater than 1 (base case)
        if (count > 1) {

            //Set k equal to the midpoint of elements
            int k = count/2;

            //Sort the left and right halves of the list
            bitonicSort(rawPixelList, low, k, 1, graphicalComponent, soundComponent, delay);
            bitonicSort(rawPixelList, low+k, k, 0, graphicalComponent, soundComponent, delay);

            //Merge the left and right halves of the list
            bitonicMerge(rawPixelList,low,count,dir,graphicalComponent, soundComponent, delay);
        }
    }

    //HeapSort
    public void heapSort(ArrayList<pixelColor> rawPixelList, Graphics graphicalComponent, MidiSoundPlayer soundComponent, int delay) {

        //Get the size of the pixelList
        int pixelListSize = rawPixelList.size();

        //Loop from the middle of the list down
        for (int i = (pixelListSize/2 - 1); i >= 0; i--) {

            //Heapify the data contained within
            heapify(rawPixelList, pixelListSize, i, soundComponent, graphicalComponent, delay);
        }

        for (int i = pixelListSize-1; i > 0; i--) {

            pixelColor temp = rawPixelList.get(0);
            rawPixelList.set(0, rawPixelList.get(i));
            rawPixelList.set(i,temp);

            soundComponent.makeSound(temp.getIndex());
            drawPixelBoard(graphicalComponent, rawPixelList);
            wait(delay);

            heapify(rawPixelList, i, 0, soundComponent, graphicalComponent, delay);
        }
    }

    public void heapify(ArrayList<pixelColor> rawPixelList, int n, int i, MidiSoundPlayer soundComponent, Graphics graphicalComponent, int delay) {
        int largest = i;
        int l = (2*i) + 1;
        int r = (2*i) + 2;

        if (l < n && rawPixelList.get(l).getIndex() > rawPixelList.get(largest).getIndex()) {
            largest = l;
        }

        if (r < n && rawPixelList.get(r).getIndex() > rawPixelList.get(largest).getIndex()) {
            largest = r;
        }

        if (largest != i) {
            pixelColor swap = rawPixelList.get(i);
            rawPixelList.set(i,rawPixelList.get(largest));

            rawPixelList.set(largest,swap);

            soundComponent.makeSound(swap.getIndex());
            drawPixelBoard(graphicalComponent, rawPixelList);
            wait(delay);

            heapify(rawPixelList,n,largest, soundComponent, graphicalComponent, delay);
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


