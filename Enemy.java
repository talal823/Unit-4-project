import java.awt.*;

public class Enemy extends GameObject {
    // Red object that ends the game on collision (unless shield is active)
    Enemy(int x, int y) {
        super(x, y, 20, 20);
    }

    @Override
    void collisionAffect(GameLogic game) {
        if (!game.shieldActive) {
            game.gameOver = true; // game ends if shield is not active
        }
    }

    @Override
    Color getColor() {
        return Color.RED; // color of the enemy
    }
}

