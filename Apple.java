import java.awt.*;

public class Apple {
    private int xPosition;
    private int yPosition;

    public void draw(Graphics g, int appleWidth) {
        g.setColor(Color.RED);
        g.fillRect(xPosition, yPosition, appleWidth, appleWidth);

    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }
}
