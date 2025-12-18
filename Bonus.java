import java.awt.*;

public class Bonus extends GameObject {
    // Small green object that increases the score when collected
    Bonus(int x, int y) {
        super(x, y, 15, 15);
    }

    @Override
    void collisionAffect(GameLogic game) {
        game.score += 500; // Increase score by 500
    }

    @Override
    Color getColor() {
        return Color.GREEN; // color of the bonus object
    }
}

