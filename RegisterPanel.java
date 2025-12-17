import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegisterPanel extends JPanel{

    public RegisterPanel(MainFrame window)
    {
        setLayout(null);

        JLabel title = new JLabel("Register");
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JLabel msgLabel = new JLabel("");
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(230, 50, 200, 50);
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

        registerBtn.setBounds(200, 300, 100, 40);
        registerBtn.addActionListener((ActionEvent e) -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            HashMap<String,String> users = FileManager.loadUsers();
            if(users.containsKey(username)){
                msgLabel.setText("Username already exists!");
            } else {
                FileManager.saveUser(username,password);
                msgLabel.setForeground(Color.GREEN);
                msgLabel.setText("Registered successfully!");
            }
        });
        add(registerBtn);

        backBtn.setBounds(320, 300, 100, 40);
        backBtn.addActionListener((ActionEvent e) -> window.switchPanel(new MenuPanel(window)));
        add(backBtn);
    }
}
