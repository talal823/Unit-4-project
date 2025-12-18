import javax.swing.*;

public class MainFrame extends JFrame {
    // Main game window
    public MainFrame() {
        setTitle("Dodge the Falling Objects");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(new MenuPanel(this)); // start with main menu
        setLocationRelativeTo(null); // center window
        setVisible(true);
    }

    // Method to switch between panels (menu, login, register, game)
    public void switchPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();

        panel.setFocusable(true); 
        panel.requestFocus(); // ensure new panel receives key events
    }

    public static void main(String[] args) {
        new MainFrame(); // launch the game
    }
}
