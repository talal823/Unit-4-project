import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameLogic {

    final int PLAYER_WIDTH = 50;
    final int PLAYER_HEIGHT = 20;

    int playerX = 275;
    int playerY = 600;
    int playerSpeed = 12;

    ArrayList<GameObject> objects = new ArrayList<>();
    Random random = new Random();
    int spawnTimer = 0;

    int score = 0;
    boolean gameOver = false;

    public boolean shieldActive = false;
    public long shieldEndTime = 0;
    public final int GOLD_SHIELD_DURATION = 5000;

    int highScore;





    public void update(boolean leftPressed, boolean rightPressed,int panelWidth, int panelHeight) 
    {

        if(gameOver) return;
        updatePlayer(leftPressed, rightPressed, panelWidth);
        updateObjects(panelHeight);
        spawnObjects(panelWidth);
        checkCollisions();
        score++;
        if(score > loadHighScore())
        {
            highScore = score;
            FileManager.saveHighScore(highScore);
        }

        if(shieldActive && System.currentTimeMillis() > shieldEndTime)
        {
            shieldActive = false;
        }

    }

    private void updatePlayer(boolean leftPressed, boolean rightPressed, int width) {
        if (leftPressed && playerX > 0) playerX -= playerSpeed;
        if (rightPressed && playerX < width - PLAYER_WIDTH) playerX += playerSpeed;
    }

    private void spawnObjects(int width) {
        spawnTimer++;
        if (spawnTimer > 35) {
            int x = random.nextInt(width - 20);
            int chance = random.nextInt(100);

            if(chance < 75)
            {
                objects.add(new GameObject(x, 0, 20, 20, 0, Color.RED)); // red object
            }
            else if (chance < 90)
            {
                objects.add(new GameObject(x, 0, 20, 20, 1, Color.GREEN));//green object
            }
            else 
            {
                objects.add(new GameObject(x, 0, 20, 20, 2, Color.YELLOW));//yellow object
            }
            spawnTimer = 0;
        }
    }

    private void updateObjects(int height) {
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            obj.y += 10;

            if (obj.y > height) {
                it.remove();
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerRect =
            new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
            Iterator<GameObject> it = objects.iterator();

            while (it.hasNext()) {
                GameObject obj = it.next();
                if (obj.getRect().intersects(playerRect)) {
                    switch (obj.type) {
                        case 0 -> {
                            if(!shieldActive) {
                                gameOver = true;
                            }
                            it.remove();
                            break;
                        }
                        case 1 -> {
                            score += 500;
                            it.remove();
                        }
                        case 2 -> {
                            shieldActive = true;
                            shieldEndTime = System.currentTimeMillis() + GOLD_SHIELD_DURATION;
                            it.remove();
                        }
                        default -> {
                        }
                    }
                }
        }
    }

    public static class GameObject 
    {
        int x, y, width, height;
        int type;

        Color color;

        GameObject(int x, int y, int w, int h, int type, Color color) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
            this.type = type;
            this.color = color;
        }

        Rectangle getRect() {
            return new Rectangle(x, y, width, height);
        }
    }

    public boolean isShieldActive() 
    {
        return shieldActive;
    }

    public boolean isGameOver()
    {
        return gameOver;
    }
    public int loadHighScore()
    {
        highScore = FileManager.loadHighScore();
        return highScore;
    }

    public void restartGame() {
        objects.clear();
        score = 0;
        playerX = 275;
        gameOver = false;
        shieldActive = false;
        shieldEndTime = 0;
    }
}
