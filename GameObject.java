import java.awt.*;

public abstract class GameObject {
    // Abstract parent class for all falling objects
    int x, y, width, height;

    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    // Returns a rectangle for collision detection
    Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    // Method to define effect on player upon collision (implemented by subclasses)
    abstract void collisionAffect(GameLogic game);

    // Method to define color of the object (implemented by subclasses)
    abstract Color getColor();
}

