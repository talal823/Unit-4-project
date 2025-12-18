import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameLogic {

    //player object dimenions
    final int PLAYER_WIDTH = 50;
    final int PLAYER_HEIGHT = 20;

    //player object specifications
    int playerX = 275;//this is the x value at which the player object spawns in.
    int playerY = 600;//this is the y value at which the player object spawns in.
    int playerSpeed = 12;//payer speed

    //gameobject which is used to create the objects that fall from the sky
    ArrayList<GameObject> objects = new ArrayList<>();
    Random random = new Random();

    //this gets infintely incremented , used to spawn objects only after some time has passed
    int spawnTimer = 0;

    //scores
    int score = 0;
    int highScore;

    boolean gameOver = false;//gameover boolean


    //shield parameters
    boolean shieldActive = false;
    long shieldEndTime = 0;//used to signal the end of shield
    final int GOLD_SHIELD_DURATION = 5000;

    String currentUser; // use to check who is the current user and will be null if the user is playing as a guest

    //consrtuctor 
    public GameLogic(String username) {
        this.currentUser = username;
    
        if (username != null) {
            highScore = FileManager.loadUserHighScore(username);
        } else {
            highScore = 0;
        }
    }


    //update method which gets run infinitly, which in turn calls all of the rest of teh methods in this class
    public void update(boolean leftPressed, boolean rightPressed,int panelWidth, int panelHeight) 
    {
        //checking if game is over, if not then calling evrything to update them
        if(gameOver) return;
        updatePlayer(leftPressed, rightPressed, panelWidth);
        updateObjects(panelHeight);
        spawnObjects(panelWidth);
        checkCollisions();
        score++;

        //saving the highscore to the highScoreFile.txt
        if (currentUser != null && score > highScore) {
            highScore = score;
            FileManager.saveUserHighScore(currentUser, score);
        }

        //turning shield off if shield end time gets exceeded
        if(shieldActive && System.currentTimeMillis() > shieldEndTime)
        {
            shieldActive = false;
        }

    }

    //player object movement
    private void updatePlayer(boolean leftPressed, boolean rightPressed, int width) {
        if (leftPressed && playerX > 0) playerX -= playerSpeed;
        if (rightPressed && playerX < width - PLAYER_WIDTH) playerX += playerSpeed;
    }



    //this is where i spawn objects
    private void spawnObjects(int width) {

        //this gets incremented everytime update is called(which is as long as the program runs)
        spawnTimer++;

        //only spawning when timer has exceeded 25
        if (spawnTimer > 25) {

            //creating variable to hold ش random value used for spawn x value
            int x = random.nextInt(width - 20);

            //this variable holds the random value which decides which object it to be spawned 
            int chance = random.nextInt(100);
            
            //there is a 70 percent chance of red enemy object spawning
            if (chance < 70) {
                objects.add(new Enemy(x, 0));

            //there is a 20(90-70)percent chance of a bonus object spawning
            } else if (chance < 90) {
                objects.add(new Bonus(x, 0));

            //there is a 10(100-70-20)percent chance of a sheild spawning
            } else {
                objects.add(new Shield(x, 0));
            }
            
            //resetting the spawntimer so that i can use the check at the start of this method again and again 
            spawnTimer = 0;
        }
    }
    


    //this method updates the falling objects, includes speed and the removal of objects after their y value exceeds the screen.
    private void updateObjects(int height) {

        //using iterator to loop through the gameobject arraylist
        Iterator<GameObject> it = objects.iterator();

        //looping as long as there are gameobject objects in the iterator object
        while (it.hasNext()) {

            //updating the y value of the object
            GameObject obj = it.next();
            obj.y += 10;

            //removing the objects if they have excceded the y boundary 
            if (obj.y > height) {
                it.remove();
            }
        }
    }

    //this method checks for collisions
    private void checkCollisions() {

        //creating the user rectangle which the user can control
        Rectangle playerRect = new Rectangle(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);


        //using iterator object
        Iterator<GameObject> it = objects.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            
            //main collision logic , intersects checks for collision, returns true if there is a collision
            if (obj.getRect().intersects(playerRect)) {
                obj.collisionAffect(this);

                //removing if there is a collision
                it.remove();
                return;
            }
        }
    }
    

    //returns wether sheild is active or not
    public boolean isShieldActive() 
    {
        return shieldActive;
    }


    //returns if game is over or not 
    public boolean isGameOver()
    {
        return gameOver;
    }


    //restarts the game by resetting all of the values
    public void restartGame() {
        objects.clear();
        score = 0;
        playerX = 275;
        gameOver = false;
        shieldActive = false;
        shieldEndTime = 0;
    }
}




