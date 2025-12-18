import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    //timer for updating the game loop
    private final Timer timer;

    //game logic object containing player, falling objects and scores
    private final GameLogic game;

    //storing the main jframe window here
    private final MainFrame window;

    //player movement flags
    private boolean leftPressed = false;
    private boolean rightPressed = false;


    //constructor 
    public GamePanel(MainFrame window, String username) {
        this.window = window;

        //panel setup
        setBackground(Color.BLACK);
        setFocusable(true); // Required to receive key events
        addKeyListener(this);

        //creating game logic object
        game = new GameLogic(username);

        //initializeing and starting timer(16 mls equates to about 60 fps)
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!game.gameOver) {

            //update player inputs 
            game.update(leftPressed, rightPressed, getWidth(), getHeight());
        } else {


            //stopping timer if game is over
            timer.stop();
        }
        // Redraw everything
        repaint();
    }

    /** DRAW EVERYTHING */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //drawing player rectangle
        //orange color for shield is active, cyan for normal instance
        if (game.isShieldActive()) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.CYAN);
        }
        g.fillRect(game.playerX, game.playerY, game.PLAYER_WIDTH, game.PLAYER_HEIGHT);

        //drawing all of the falling objects
        for (GameObject obj : game.objects) {
            g.setColor(obj.getColor()); // Each object defines its own color
            g.fillRect(obj.x, obj.y, obj.width, obj.height);
        }

        //drawing scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Score: " + game.score, 20, 30);
        g.drawString("High Score: " + game.highScore, 20, 60);

        //drawing shield timer if shield is active
        if (game.isShieldActive()) {

            //timeleft is calculated by adding the current runtime with the shield end time to find the runtime at which shield ends
            long timeLeft = (game.shieldEndTime - System.currentTimeMillis()) / 1000;
            g.drawString("Shield Time: " + Math.max(timeLeft, 0), 400, 30);
        }

        //game over screen 
        if (game.gameOver) {

            //drawing gameover
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", 150, 300);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", getWidth() / 2 - 80, getHeight() / 2 + 50);
        }
    }

    /** KEY LISTENERS */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //player movement
        if (!game.gameOver) {
            if (key == KeyEvent.VK_LEFT) leftPressed = true;
            if (key == KeyEvent.VK_RIGHT) rightPressed = true;
        }
        //restarting game if r is pressed
        else if (key == KeyEvent.VK_R) {
            game.restartGame();
            timer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //stoping all movemnts if key is not pressed
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) leftPressed = false;
        if (key == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    //forces the panel to 
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus(); 
    }
}

