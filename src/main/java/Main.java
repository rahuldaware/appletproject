import database.DBService;
import validation.UsernameValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main extends JApplet{

    private JFrame mainFrame;

    private JPanel homePanel;
    private JPanel newUserPanel;
    private JPanel loginUserPanel;
    private JPanel activityPanel;
    private JPanel bookTicketPanel;
    private JPanel buyTicketPanel;
    private JPanel cancelTicketPanel;

    private JButton backButton;
    private JButton newUserButton;
    private JButton loginUserButton;
    private static DBService dbService;

    private JTextField username;
    private JPasswordField password;

    private String[] airports =
            {"Mumbai", "Delhi", "Hyderabad", "Kolkata", "Chennai", "Bengaluru", "Ahmedabad", "Pune", "Jaipur", "Lucknow"};

    private String[] passengers = {"1","2","3","4","5","6"};
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

        createBookTicketPanel();
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

    public void createBookTicketPanel() {
        bookTicketPanel = new JPanel();
        bookTicketPanel.setLayout(new FlowLayout());
        JLabel fromLabel = new JLabel("From: ",JLabel.LEFT);
        JLabel toLabel = new JLabel("To: ",JLabel.LEFT);

        bookTicketPanel.add(fromLabel);

        DefaultComboBoxModel sourceList = new DefaultComboBoxModel();
        for(String airport: airports) {
            sourceList.addElement(airport);
        }
        JComboBox sourceListComboBox = new JComboBox(sourceList);
        sourceListComboBox.setSelectedIndex(0);
        JScrollPane sourceListScrollPane = new JScrollPane(sourceListComboBox);
        bookTicketPanel.add(sourceListScrollPane);

        bookTicketPanel.add(toLabel);

        DefaultComboBoxModel destinationList = new DefaultComboBoxModel();
        for(String airport: airports) {
            destinationList.addElement(airport);
        }
        JComboBox destinationListComboBox = new JComboBox(destinationList);
        destinationListComboBox.setSelectedIndex(0);
        JScrollPane destinationListScrollPane = new JScrollPane(destinationListComboBox);
        bookTicketPanel.add(destinationListScrollPane);

        bookTicketPanel.add(newLine(), BorderLayout.CENTER);


        JLabel departDateLabel = new JLabel("Depart On (DD/MM/YYYY):", JLabel.LEFT);

        JTextField departDateField = new JTextField(6);
        departDateField.setBackground(Color.LIGHT_GRAY);

        JLabel returnDateLabel = new JLabel("Return On (DD/MM/YYYY):", JLabel.LEFT);

        JTextField returnDateField = new JTextField(6);
        returnDateField.setBackground(Color.LIGHT_GRAY);

        bookTicketPanel.add(departDateLabel);
        bookTicketPanel.add(departDateField);
        bookTicketPanel.add(returnDateLabel);
        bookTicketPanel.add(returnDateField);

        bookTicketPanel.add(newLine(), BorderLayout.CENTER);

        JLabel passengerLabel = new JLabel("Passengers: ");
        bookTicketPanel.add(passengerLabel);

        DefaultComboBoxModel passengerList = new DefaultComboBoxModel();
        for(String passenger: passengers) {
            passengerList.addElement(passenger);
        }
        JComboBox passengerListComboBox = new JComboBox(passengerList);
        passengerListComboBox.setSelectedIndex(0);
        JScrollPane passengerListScrollPane = new JScrollPane(passengerListComboBox);
        bookTicketPanel.add(passengerListScrollPane);

        JRadioButton businessRadioButton = new JRadioButton("Business Class");
        JRadioButton economyRadioButton = new JRadioButton("Economy Class");
        businessRadioButton.setBounds(75,50,100,30);
        economyRadioButton.setBounds(75,100,100,30);
        ButtonGroup bg=new ButtonGroup();
        bg.add(businessRadioButton);
        bg.add(economyRadioButton);
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(businessRadioButton);
        verticalBox.add(economyRadioButton);
        bookTicketPanel.add(verticalBox);

        bookTicketPanel.add(newLine());
        bookTicketPanel.add(newLine());

        JTextField message = new JTextField("");
        message.setForeground(Color.RED);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String departDate = departDateField.getText();
                String returnDate = returnDateField.getText();
                String validation = dateValidator(departDate, returnDate);
                if(sourceListComboBox.getSelectedIndex() == destinationListComboBox.getSelectedIndex()) {
                    validation = "Source and Destination airports cannot be same";
                }
                if(!businessRadioButton.isSelected() && !economyRadioButton.isSelected()) {
                    validation = "Class not selected";
                }
                if(validation != null) {
                    System.out.println(validation);
                    message.setText(validation);
                    createBookTicketPanel();
                    bookTicketPanel.add(message);
                    mainFrame.setContentPane(bookTicketPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                    return;
                } else {
                    System.out.println("Form filled successfully");
                }

            }
        });
        bookTicketPanel.add(submitButton);

        mainFrame.add(bookTicketPanel);
        mainFrame.setVisible(true);


    }

    public String dateValidator(String departDate, String returnDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate departLocalDate, returnLocalDate;
        try{
            departLocalDate = LocalDate.parse(departDate, dtf);
            returnLocalDate = LocalDate.parse(returnDate, dtf);
        } catch (Exception e) {
            return "Date is not in correct format";
        }
        if(departLocalDate.isBefore(LocalDate.now()) || returnLocalDate.isBefore(LocalDate.now())) {
            return "Departure and return date cannot be in past";
        }
        if(returnLocalDate.isBefore(departLocalDate)) {
            return "Return Date is before Depart Date";
        }
        return null;
    }
    public void createActivityPanel() {
        activityPanel = new JPanel();
        activityPanel.add(backButton);

        JRadioButton bookTicket = new JRadioButton("Book Ticket");
        JRadioButton cancelTicket = new JRadioButton("Cancel Ticket");
        JRadioButton viewHistory = new JRadioButton("View History");
        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);

        bookTicket.setBounds(75,50,100,30);
        cancelTicket.setBounds(75,100,100,30);
        viewHistory.setBounds(75,150,100,30);

        ButtonGroup bg=new ButtonGroup();
        bg.add(bookTicket);
        bg.add(cancelTicket);
        bg.add(viewHistory);

        activityPanel.add(bookTicket);
        activityPanel.add(cancelTicket);
        activityPanel.add(viewHistory);
        activityPanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(bookTicket.isSelected()) {
                    System.out.println("Book Ticket Selected");
                    createBookTicketPanel();
                    mainFrame.setContentPane(bookTicketPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                    return;
                }
                else if(cancelTicket.isSelected()) {
                    System.out.println("Cancel Ticket Selected");
                }
                else if(viewHistory.isSelected()){
                    System.out.println("View History Selected");
                } else {
                    createActivityPanel();
                    mainFrame.setContentPane(activityPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                }
            }
        });
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
                newUserPanel.add(newLine(), BorderLayout.CENTER);
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
                    createActivityPanel();
                    mainFrame.setContentPane(activityPanel);
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
        mainFrame.add(bookTicketPanel);
    }

    public static void main(String[] args) {
        dbService = new DBService();
        Main main = new Main();
    }

}
