import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final Timer timer;
    private final GameLogic game;
    private final MainFrame window;


    public GamePanel(MainFrame window) {
        this.window = window;
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        game = new GameLogic();
        timer = new Timer(16, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!game.gameOver) {
            game.update(leftPressed, rightPressed, getWidth(), getHeight());
        } else {
            timer.stop();
        }
        repaint();
    }

    /** DRAW EVERYTHING */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw player
        if (game.isShieldActive())
        {
            g.setColor(Color.ORANGE);
        }else{
            g.setColor(Color.CYAN);
        }
        g.fillRect(game.playerX, game.playerY, game.PLAYER_WIDTH, game.PLAYER_HEIGHT);

        // Draw falling objects
        for (int i = 0; i < game.objects.size(); i++) {
            GameLogic.GameObject obj = game.objects.get(i);

            switch(obj.type){
                case 0 -> g.setColor(Color.RED);
                case 1 -> g.setColor(Color.GREEN);
                case 2 -> g.setColor(Color.YELLOW);
            }

            g.fillRect(obj.x, obj.y, obj.width, obj.height);
        }

        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Score: " + game.score, 20, 30);
        g.drawString("High Score: " + game.highScore, 20, 60);


        if(game.isShieldActive())
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            long timeLeft = (game.shieldEndTime - System.currentTimeMillis()) / 1000;
            g.drawString("Shield Time: " + Math.max(timeLeft, 0), 400, 30);
        }


        // Game over message
        if (game.gameOver) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", 150, 300);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", getWidth()/2 - 80, getHeight()/2 + 50);
        }
    }

    /** KEY LISTENERS */
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(!game.gameOver){
            if(key==KeyEvent.VK_LEFT) leftPressed=true;
            if(key==KeyEvent.VK_RIGHT) rightPressed=true;
        }
        else if(key == KeyEvent.VK_R) {
            game.restartGame();
            timer.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key==KeyEvent.VK_LEFT) leftPressed=false;
        if(key==KeyEvent.VK_RIGHT) rightPressed=false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus(); 
    }
}
