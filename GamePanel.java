import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private final int GAME_WIDTH = 600;
    private final int GAME_HEIGHT = 600;


    private Head head;
    private ArrayList<Tail> body;
    private int tailAmount = 3;
    private int snakeWidth = 30;
    //0 = up 1 = right 2 = down 3 = left
    private int direction = 3;

    private Apple apple;
    private int applesEaten;

    public GamePanel() {
        createHead();
        createTails();
        createApple();
        spawnApple();

        setFocusable(true);
        addKeyListener(new Listener());
        Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(SCREEN_SIZE);

        Thread thread = new Thread(this);
        thread.start();
    }

    private void createTails() {
        body = new ArrayList<>();
        for(int i = 0; i < tailAmount; i++) {
            body.add(new Tail());
            body.get(i).setXPosition(GAME_WIDTH / 2 - snakeWidth * (i+1));
            body.get(i).setYPosition(GAME_HEIGHT / 2 );
        }
    }

    private void createHead() {
       head = new Head();
       head.setXPosition(GAME_WIDTH / 2);
       head.setYPosition(GAME_HEIGHT / 2);
    }

    private void createApple() {
        apple = new Apple();
    }

    private void spawnApple() {
        int random = (int) (Math.random() * (GAME_WIDTH / snakeWidth));
        apple.setXPosition(random * snakeWidth);
        random = (int) (Math.random() * (GAME_HEIGHT / snakeWidth));
        apple.setYPosition(random * snakeWidth);
    }

    @Override
    public void paint(Graphics g) {
        Image image = createImage(getWidth(), getHeight());
        Graphics graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    private void draw(Graphics g) {
        g.setColor(new Color(39, 39, 46));
        for(int i = 0; i < GAME_HEIGHT / snakeWidth; i++) {
            g.drawLine(i*snakeWidth, 0, i*snakeWidth, GAME_HEIGHT);
            g.drawLine(0, i*snakeWidth, GAME_WIDTH, i*snakeWidth);
        }

        for(int i = 0; i < tailAmount; i++) {
            body.get(i).draw(g, snakeWidth);
        }

        apple.draw(g, snakeWidth);

        head.draw(g, snakeWidth);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Sagoe UI", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (GAME_WIDTH - metrics.stringWidth("Score: " + applesEaten)), g.getFont().getSize());
    }

    private void checkApple() {
        if(head.getXPosition() == apple.getXPosition() && head.getYPosition() == apple.getYPosition()) {
            tailAmount += 1;
            body.add(new Tail());
            body.get(tailAmount-1).setXPosition(body.get(tailAmount-2).getXPosition());
            body.get(tailAmount-1).setYPosition(body.get(tailAmount-2).getYPosition());

            applesEaten += 1;

            spawnApple();
        }
    }

    private boolean checkDead() {
        for(int i = 0; i < tailAmount; i++) {
            if(head.getXPosition() == body.get(i).getXPosition() && head.getYPosition() == body.get(i).getYPosition()) {
                return true;
            }
        }

        System.out.println(true);

        if(head.getYPosition() <= -1) {
            return true;
        }
        else if(head.getYPosition() >= GAME_HEIGHT+1) {
            return true;
        }
        else if(head.getXPosition() <= -1) {
            return true;
        }
        else return head.getXPosition() >= GAME_WIDTH+1;
    }



    private void move() {
        for(int i = tailAmount - 1; i > 0; i--) {
            body.get(i).setXPosition(body.get(i-1).getXPosition());
            body.get(i).setYPosition(body.get(i-1).getYPosition());
        }
        body.get(0).setXPosition(head.getXPosition());
        body.get(0).setYPosition(head.getYPosition());

        switch (direction) {
            case 0:
                head.setYPosition(head.getYPosition() - snakeWidth);
                break;
            case 1:
                head.setXPosition(head.getXPosition() - snakeWidth);
                break;
            case 2:
                head.setYPosition(head.getYPosition() + snakeWidth);
                break;
            case 3:
                head.setXPosition(head.getXPosition() + snakeWidth);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 5;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                if(checkDead()) {
                    break;
                }
                move();
                checkApple();
                repaint();
                delta--;
            }
        }
    }

    private class Listener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keyId = e.getKeyCode();

            // 0 = up; 1 = left; 2 = down; 3 = right
            if(keyId == KeyEvent.VK_UP && !(direction == 2)) {
                direction = 0;
            }
            else if(keyId == KeyEvent.VK_RIGHT && !(direction == 1)) {
                direction = 3;
            }
            else if(keyId == KeyEvent.VK_DOWN && !(direction == 0)) {
                direction = 2;
            }
            else if(keyId == KeyEvent.VK_LEFT && !(direction == 3)) {
                direction = 1;
            }
        }
    }
}
