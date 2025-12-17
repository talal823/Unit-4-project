import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Dodge the Falling Objects");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setContentPane(new MenuPanel(this));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void switchPanel(JPanel panel) {
        setContentPane(panel);
        revalidate();
        repaint();
    
        panel.setFocusable(true);
        panel.requestFocus();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
