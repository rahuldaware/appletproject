import database.DBService;
import validation.UsernameValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Main extends JApplet{

    private JFrame mainFrame;

    private JPanel homePanel;
    private JPanel newUserPanel;
    private JPanel loginUserPanel;
    private JPanel bookingPanel;

    private JButton backButton;
    private JButton newUserButton;
    private JButton loginUserButton;
    private static DBService dbService;

    private JTextField username;
    private JPasswordField password;

    public Main() {
        initFrame();
    }

    private ActionListener backButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back Button Action Clicked");
                createHomePanel();
                mainFrame.setContentPane(homePanel);
                mainFrame.invalidate();
                mainFrame.validate();

            }
        };
    }

    private ActionListener newUserButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login User Action Clicked");
                createNewUserPanel();
                mainFrame.setContentPane(newUserPanel);
                mainFrame.invalidate();
                mainFrame.validate();
            }
        };
    }

    private ActionListener loginUserButtonActionListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login User Action Clicked");
                createLoginUserPanel();
                mainFrame.setContentPane(loginUserPanel);
                mainFrame.invalidate();
                mainFrame.validate();

            }
        };
    }

    public void initPanelsAndButtons() {
        backButton = new JButton("Back");
        newUserButton = new JButton("New User");
        loginUserButton = new JButton("Login");

        backButton.addActionListener(backButtonActionListener());
        newUserButton.addActionListener(newUserButtonActionListener());
        loginUserButton.addActionListener(loginUserButtonActionListener());

        createHomePanel();
    }

    public void createHomePanel() {
        homePanel = new JPanel();
        homePanel.add(backButton);
        homePanel.add(newUserButton);
        homePanel.add(loginUserButton);
    }

    public JTextArea newLine() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10,300,10,300));
        return textArea;
    }

    public String newUserValidation(JTextField username, JPasswordField password, JPasswordField confirmPassword) {
        if(username.getText().length() < 6) {
            return "Username should be atleast 6 characters";
        }
        if(!password.getText().equals(confirmPassword.getText())) {
            return "Passwords should match";
        }
        if(dbService.isUserExist(username.getText())) {
            return "Username already exists. Please select another username";
        }
        else {
            return null;
        }
    }

    public String loginUserValidation(JTextField username, JPasswordField password) {
        if(username.getText().length() < 6) {
            return "Username should be atleast 6 characters";
        }
        if(!dbService.isUserExist(username.getText())) {
            return "Username does not exist";
        }
        if(!dbService.isUserAuthorized(username.getText(), password.getText())) {
            return "Incorrect username and/or password";
        }
        return null;
    }

    public void createBookingPanel() {
        bookingPanel = new JPanel();
        bookingPanel.add(backButton);

    }

    public void createNewUserPanel() {
        newUserPanel = new JPanel();
        newUserPanel.add(backButton);
        newUserPanel.add(newLine(), BorderLayout.CENTER);
        JLabel userNameLabel = new JLabel("Username: ", JLabel.CENTER);
        JLabel passwordLabel = new JLabel("Password: ", JLabel.CENTER);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password: ", JLabel.CENTER);
        JLabel message = new JLabel("", JLabel.CENTER);
        message.setForeground(Color.RED);

        JTextField username = new JTextField(10);
        username.setInputVerifier(new UsernameValidation());
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');
        JPasswordField confirmPassword = new JPasswordField(10);
        confirmPassword.setEchoChar('*');

        JButton signInButton = new JButton("Sign In");
        signInButton.setBackground(Color.GREEN);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String validation = newUserValidation(username, password, confirmPassword);
                if(validation != null) {
                    message.setText(validation);
                    createNewUserPanel();
                    newUserPanel.add(message);
                    mainFrame.setContentPane(newUserPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                    return;
                }
                System.out.println("Username: " + username.getText() + " Password: " + password.getText());
                String result = dbService.insertNewUser(username.getText().trim(), password.getText().trim());
                message.setText(result);
                createNewUserPanel();
                loginUserPanel.add(newLine(), BorderLayout.CENTER);
                newUserPanel.add(message);
                mainFrame.setContentPane(newUserPanel);
                mainFrame.invalidate();
                mainFrame.validate();
            }
        });

        newUserPanel.add(userNameLabel);
        newUserPanel.add(username);
        newUserPanel.add(passwordLabel);
        newUserPanel.add(password);
        newUserPanel.add(confirmPasswordLabel);
        newUserPanel.add(confirmPassword);
        newUserPanel.add(signInButton);
    }

    public void createLoginUserPanel() {
        loginUserPanel = new JPanel();
        loginUserPanel.add(backButton);
        loginUserPanel.add(newLine(), BorderLayout.CENTER);
        JLabel userNameLabel = new JLabel("User name: ", JLabel.RIGHT);
        JLabel passwordLabel = new JLabel("Password: ", JLabel.RIGHT);
        JLabel message = new JLabel("", JLabel.CENTER);
        message.setForeground(Color.RED);
        JTextField username = new JTextField(10);
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');

        JButton loginButton = new JButton("Log In");
        loginButton.setBackground(Color.GREEN);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Remove this log later!
                System.out.println("Username: " + username.getText() + " Password: " + password.getText());
                String validation = loginUserValidation(username, password);
                System.out.println(validation);
                if(validation != null) {
                    message.setText(validation);
                    createLoginUserPanel();
                    loginUserPanel.add(newLine(), BorderLayout.CENTER);
                    loginUserPanel.add(message);
                    mainFrame.setContentPane(loginUserPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                    return;
                } else {
                    message.setText("Login Successful");
                    createBookingPanel();
                    bookingPanel.add(message);
                    mainFrame.setContentPane(bookingPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                }
            }
        });

        loginUserPanel.add(userNameLabel);
        loginUserPanel.add(username);
        loginUserPanel.add(passwordLabel);
        loginUserPanel.add(password);
        loginUserPanel.add(loginButton);



    }

    public void initFrame() {
        mainFrame = new JFrame("Rucha Project");
        mainFrame.setVisible(true);
        mainFrame.setSize(800,600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initPanelsAndButtons();
        //createHomePanel();
        mainFrame.add(homePanel);
    }

    public static void main(String[] args) {
        dbService = new DBService();
        Main main = new Main();
    }

}
