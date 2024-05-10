import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameLauncher extends JFrame implements ActionListener {
    private UserAuthGUI userAuth;
    public GameLauncher() {
        setTitle("Game Launcher");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(1, 2));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        panel.add(registerButton);

        add(panel);
        setVisible(true);

        userAuth = new UserAuthGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            userAuth.setVisible(true);
        } else if (e.getActionCommand().equals("Register")) {
            // Implement registration logic here
            JOptionPane.showMessageDialog(this, "Registration feature not implemented yet.");
        }
    }

    public void launchGame() {
        new SpaceInvadersGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameLauncher());
    }
}
