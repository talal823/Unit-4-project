import java.awt.*;

public class Shield extends GameObject {
    // Yellow object that activates a temporary shield on collision
    Shield(int x, int y) {
        super(x, y, 25, 25);
    }

    @Override
    void collisionAffect(GameLogic game) {
        game.shieldActive = true;
        game.shieldEndTime = System.currentTimeMillis() + game.GOLD_SHIELD_DURATION; // activate shield for duration
    }

    @Override
    Color getColor() {
        return Color.YELLOW; // color of the shield
    }
}

