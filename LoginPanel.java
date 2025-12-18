import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.util.HashMap;



public class LoginPanel extends JPanel {
    // Panel for user login interface
    public LoginPanel(MainFrame window) {
        setLayout(null); // use absolute positioning

        // Labels, text fields, and buttons for username, password, and messages
        JLabel title = new JLabel("Login");
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel msgLabel = new JLabel(""); // display error messages
        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        // Styling and positioning of labels and fields
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(250, 50, 100, 50);
        add(title);

        userLabel.setBounds(150, 150, 100, 30);
        add(userLabel);

        userField.setBounds(250, 150, 200, 30);
        add(userField);

        passLabel.setBounds(150, 200, 100, 30);
        add(passLabel);

        passField.setBounds(250, 200, 200, 30);
        add(passField);

        msgLabel.setBounds(150, 250, 300, 30);
        msgLabel.setForeground(Color.RED);
        add(msgLabel);

        // Login button logic
        loginBtn.setBounds(200, 300, 100, 40);
        loginBtn.addActionListener((ActionEvent e) -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            HashMap<String,String> users = FileManager.loadUsers();

            // Validate username and password
            if(users.containsKey(username) && users.get(username).equals(password)){
                window.switchPanel(new GamePanel(window)); // successful login → game panel
            } else {
                msgLabel.setText("Invalid username or password!");
            }
        });
        add(loginBtn);

        // Back button returns to main menu
        backBtn.setBounds(320, 300, 100, 40);
        backBtn.addActionListener((ActionEvent e) -> window.switchPanel(new MenuPanel(window)));
        add(backBtn);
    }
}
