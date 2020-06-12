public class pixelColor {
    public int R;
    public int G;
    public int B;
    public int index;
    public int height;
    public int width;
    public pixelColor(int r, int g, int b, int index, int height, int width) {
        R = r;
        G = g;
        B = b;
        this.index = index;
        this.height = height;
        this.width = width;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getB() {
        return B;
    }

    public void setB(int b) {
        B = b;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        System.out.println("Red: " + this.getR());
        System.out.println("Green: " + this.getG());
        System.out.println("Blue: " + this.getB());
        System.out.println("Index: " + this.getIndex());
        return "Done";
    }
}
