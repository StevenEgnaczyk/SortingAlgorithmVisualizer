import java.util.ArrayList;

public class pBoard {
    public ArrayList<pixelColor> pList = new ArrayList<>();
    public pBoard() {
        int index = 0;
        int r = 0;
        int g = 0;
        int b = 0;
        while (r < 255) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            r++;
        }
        while (g < 255) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            g++;
        }
        while (r > 0) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            r--;
        }
        while (b < 255) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            b++;
        }
        while (g > 0) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            g--;
        }
        while (r < 255) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            r++;
        }
        while (g < 255) {
            pList.add(new pixelColor(r,g,b,index));
            index++;
            g++;
        }
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
