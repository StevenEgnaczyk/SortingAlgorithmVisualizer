public class pixelColor {
    public int R;
    public int G;
    public int B;
    public int index;

    public pixelColor(int r, int g, int b, int index) {
        R = r;
        G = g;
        B = b;
        this.index = index;
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
}
