import java.awt.*;
import java.util.ArrayList;

public class pixelList {
    public ArrayList<pixelColor> pList = new ArrayList<>();
    public pixelList(int size) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;

        int pixelHeight = screenHeight;
        int pixelWidth = 10;
        pixelWidth = size;

        float r = 0;
        float g = 0;
        float b = 0;
        int index = 0;

        int leftOverPixels;
        int numberOfElements;
        float addValue;

        leftOverPixels = ((screenWidth/pixelWidth)%7);
        numberOfElements = ((screenWidth-leftOverPixels)/pixelWidth);
        addValue = 255/(float)(numberOfElements/7);

        while(Math.round(r) < 255) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            r+=addValue;
            index++;
        }
        while (Math.round(g) < 255) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            g+=addValue;
            index++;
        }
        while (Math.round(r) > 0) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            r-=addValue;
            index++;
        }
        while (Math.round(b) < 255) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            b+=addValue;
            index++;
        }
        while (Math.round(g) > 0) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            g-=addValue;
            index++;
        }
        while (Math.round(r) < 255) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            r+=addValue;
            index++;
        }
        while (Math.round(g) < 255) {
            pList.add(new pixelColor((int)r,(int)g,(int)b,index,pixelHeight,pixelWidth));
            g+=addValue;
            index++;
        }

        int lastPixelGreen = pList.get(pList.size()-1).getG();
        float leftOVerPixelsAddValue = ((float)(255-lastPixelGreen)/(float)leftOverPixels);

        System.out.println(lastPixelGreen);
        System.out.println(leftOVerPixelsAddValue);

        while (leftOverPixels > 1) {
            lastPixelGreen += (int)leftOVerPixelsAddValue;
            pList.add(new pixelColor(255,lastPixelGreen,255,index,pixelHeight,pixelWidth));
            leftOverPixels--;
            index++;
        }

        pList.add(new pixelColor(255,255,255,index,pixelHeight,pixelWidth));
    }

    public pixelColor addRed (pixelColor pColor) {
        pColor.setR(pColor.getR()+1);
        pColor.setIndex(pColor.getIndex()+1);
        return pColor;
    }
    public pixelColor subRed (pixelColor pColor) {
        pColor.setR(pColor.getR()-1);
        pColor.setIndex(pColor.getIndex()+1);
        return pColor;
    }
    public pixelColor addGreen (pixelColor pColor) {
        pColor.setG(pColor.getG()+1);
        pColor.setIndex(pColor.getIndex()+1);
        return pColor;
    }
    public pixelColor subGreen (pixelColor pColor) {
        pColor.setG(pColor.getG()-1);
        pColor.setIndex(pColor.getIndex()+1);
        return pColor;
    }
    public pixelColor addBlue (pixelColor pColor) {
        pColor.setB(pColor.getB()+1);
        pColor.setIndex(pColor.getIndex()+1);
        return pColor;
    }
    public pixelColor subBlue (pixelColor pColor) {
        pColor.setB(pColor.getB()-1);
        pColor.setIndex(pColor.getIndex()+1);
        return pColor;
    }

    public boolean isSorted() {
        int index = 0;
        for (pixelColor p : pList) {
            if (index == p.getIndex()) {

            } else {
                return false;
            }
            index++;
        }
        return true;
    }

    public ArrayList<pixelColor> getpList() {
        return pList;
    }

    public void setpList(ArrayList<pixelColor> pList) {
        this.pList = pList;
    }

    @Override
    public String toString() {
        for (pixelColor p : pList) {
            System.out.println(p.getR());
            System.out.println(p.getG());
            System.out.println(p.getB());
        }
        return "Complete";
    }
}
