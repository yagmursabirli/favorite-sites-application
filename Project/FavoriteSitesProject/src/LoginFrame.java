import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("LoginFrame");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        Color backgroundColor = new Color(242, 252, 225 ); // background color
        panel.setBackground(backgroundColor);

        Color textColor = new Color(70, 111, 70  ); //text color
        Font labelFont = new Font("Arial", Font.BOLD, 18);  //text font and size


        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(textColor);
        userLabel.setFont(labelFont);


        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(textColor);
        passLabel.setFont(labelFont);


        JTextField userText = new JTextField();
        JPasswordField passText = new JPasswordField();

        Color loginButtonColor = new Color(234, 249, 208 ); //login button color
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(loginButtonColor);
        loginButton.setFont(labelFont);
        loginButton.setForeground(textColor);

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passLabel);
        panel.add(passText);
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                if (DatabaseHelper.validateUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    new MainFrame(username).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
