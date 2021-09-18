import java.awt.*;

public class Tail {
    private int xPosition;
    private int yPosition;

    public void draw(Graphics g, int snakeWidth) {
        g.setColor(Color.GREEN);
        g.fillRect(xPosition, yPosition, snakeWidth, snakeWidth);

    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
