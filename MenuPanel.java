import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MenuPanel extends JPanel{

    public MenuPanel(MainFrame window)
    {
        setLayout(null);

        JLabel title = new JLabel("Dodge The Falling Objects");
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JButton guestBtn = new JButton("Play as Guest");

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(100, 50, 500, 50);
        add(title);

        loginBtn.setBounds(200, 150, 200, 50);
        loginBtn.addActionListener((ActionEvent e) -> window.switchPanel(new LoginPanel(window)));
        add(loginBtn);

        registerBtn.setBounds(200, 250, 200, 50);
        registerBtn.addActionListener((ActionEvent e) -> window.switchPanel(new RegisterPanel(window)));
        add(registerBtn);

        guestBtn.setBounds(200, 350, 200, 50);
        guestBtn.addActionListener((ActionEvent e) -> window.switchPanel(new GamePanel(window)));
        add(guestBtn);


    }
}
