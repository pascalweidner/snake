import java.awt.*;

public class Head {
    private int xPosition;
    private int yPosition;

    public void draw(Graphics g, int snakeWidth) {
        g.setColor(new Color(55, 204, 51));
        g.fillRect(xPosition, yPosition, snakeWidth, snakeWidth);

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
