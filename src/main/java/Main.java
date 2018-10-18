import database.DBService;
import validation.UsernameValidation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import net.miginfocom.swing.MigLayout;

public class Main extends JApplet{

    private JLabel spacer = new JLabel(" ");
    private JFrame mainFrame;

    private JPanel homePanel;
    private JPanel newUserPanel;
    private JPanel loginUserPanel;
    private JPanel activityPanel;
    private JPanel bookTicketPanel;
    private JPanel buyTicketPanel;
    private JPanel confirmationPanel;
    private JPanel cancelTicketPanel;

    private JButton backButton;
    private JButton newUserButton;
    private JButton loginUserButton;
    private static DBService dbService;

    private JTextField username;
    private JPasswordField password;

    private ArrayList<String> airports = new ArrayList<>();



    private String[] passengers = {"1","2","3","4","5","6"};

    private int[] fare = {2000, 1800, 3000, 2200, 2500, 2600, 2700, 2300, 1900, 1600};
    public Main() {
        String[] cities = {"Mumbai", "Delhi", "Hyderabad", "Kolkata", "Chennai", "Bengaluru", "Ahmedabad", "Pune", "Jaipur", "Lucknow"};
        airports.addAll(Arrays.asList(cities));
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

    public void createHomePanel() {
        homePanel = new JPanel();
        // homePanel.setLayout(new MigLayout());
        homePanel.add(backButton);
        homePanel.add(newUserButton);
        homePanel.add(loginUserButton);
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
        bookTicketPanel.setLayout(new MigLayout());
        JLabel fromLabel = new JLabel("From: ");
        JLabel toLabel = new JLabel("To: ");

        bookTicketPanel.add(fromLabel);

        DefaultComboBoxModel sourceList = new DefaultComboBoxModel();
        for(String airport: airports) {
            sourceList.addElement(airport);
        }
        JComboBox sourceListComboBox = new JComboBox(sourceList);
        sourceListComboBox.setSelectedIndex(0);
        JScrollPane sourceListScrollPane = new JScrollPane(sourceListComboBox);
        bookTicketPanel.add(sourceListScrollPane, "wrap");

        bookTicketPanel.add(toLabel);

        DefaultComboBoxModel destinationList = new DefaultComboBoxModel();
        for(String airport: airports) {
            destinationList.addElement(airport);
        }
        JComboBox destinationListComboBox = new JComboBox(destinationList);
        destinationListComboBox.setSelectedIndex(0);
        JScrollPane destinationListScrollPane = new JScrollPane(destinationListComboBox);
        bookTicketPanel.add(destinationListScrollPane, "wrap");

        bookTicketPanel.add(spacer, "span, grow");


        JLabel departDateLabel = new JLabel("Depart On (DD/MM/YYYY):", JLabel.LEFT);

        JTextField departDateField = new JTextField(7);
        departDateField.setBackground(Color.LIGHT_GRAY);

        JLabel returnDateLabel = new JLabel("Return On (DD/MM/YYYY):", JLabel.LEFT);

        JTextField returnDateField = new JTextField(7);
        returnDateField.setBackground(Color.LIGHT_GRAY);

        bookTicketPanel.add(departDateLabel);
        bookTicketPanel.add(departDateField);
        bookTicketPanel.add(returnDateLabel);
        bookTicketPanel.add(returnDateField, "wrap");

        bookTicketPanel.add(spacer, "span, grow");

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

        bookTicketPanel.add(spacer, "span, grow");
        bookTicketPanel.add(spacer, "span, grow");

        JTextField message = new JTextField("");
        message.setForeground(Color.RED);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.GREEN);
        submitButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Delete Later
        destinationListComboBox.setSelectedIndex(2);
        departDateField.setText("20/10/2018");
        returnDateField.setText("22/10/2018");
        passengerListComboBox.setSelectedIndex(3);
        businessRadioButton.setSelected(true);
        //Delete until here

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String source = airports.get(sourceListComboBox.getSelectedIndex());
                String destination = airports.get(destinationListComboBox.getSelectedIndex());
                String passengerCount = passengers[passengerListComboBox.getSelectedIndex()];
                String departDate = departDateField.getText();
                String returnDate = returnDateField.getText();
                String validation = dateValidator(departDate, returnDate);
                if(source.equals(destination)) {
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
                    String flyClass = businessRadioButton.isSelected() ? "Business" : "Economy";
                    createBuyTicketPanel(departDate, returnDate, source, destination, Integer.parseInt(passengerCount), flyClass);
                    mainFrame.setContentPane(buyTicketPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                }

            }
        });
        bookTicketPanel.add(submitButton);

        mainFrame.add(bookTicketPanel);
        mainFrame.setVisible(true);


    }

    public void createBuyTicketPanel(String departDate, String returnDate, String source, String destination, int passengerCount, String flyClass) {
        buyTicketPanel = new JPanel();
        buyTicketPanel.setLayout(new MigLayout());
        buyTicketPanel.add(backButton, "wrap");

        JLabel departDateLabel = new JLabel(departDate);
        JLabel returnDateLabel = new JLabel(returnDate);
        JLabel sourceLabel = new JLabel(source);
        JLabel destinationLabel = new JLabel(destination);

        ArrayList<JTextField> passengerFirstName = new ArrayList<>();
        ArrayList<JTextField> passengerLastName = new ArrayList<>();
        ArrayList<JTextField> passengerAge = new ArrayList<>();
        ArrayList<JTextField> passengerGender = new ArrayList<>();

        for(int i = 0; i<passengerCount;i++) {
            JTextField firstName = new JTextField(10);
            JTextField lastName = new JTextField(10);
            JTextField age = new JTextField(2);
            JTextField gender = new JTextField(2);

            passengerFirstName.add(firstName);
            passengerLastName.add(lastName);
            passengerAge.add(age);
            passengerGender.add(gender);
        }


        buyTicketPanel.add(new JLabel("Depart On: " + departDateLabel.getText()));
        buyTicketPanel.add(new JLabel("Return On: " + returnDateLabel.getText()),"wrap");
        buyTicketPanel.add(new JLabel("From: " + sourceLabel.getText()));
        buyTicketPanel.add(new JLabel("To: " + destinationLabel.getText()));
        buyTicketPanel.add(new JLabel("Class: " + flyClass), "wrap");
        buyTicketPanel.add(new JLabel("Passenger Details"), "wrap");
        for(int i=0; i<passengerCount;i++) {
            buyTicketPanel.add(new JLabel("First Name: ", JLabel.LEFT));
            buyTicketPanel.add(passengerFirstName.get(i));

            buyTicketPanel.add(new JLabel("Last Name: "));
            buyTicketPanel.add(passengerLastName.get(i));

            buyTicketPanel.add(new JLabel("Age: "));
            buyTicketPanel.add(passengerAge.get(i));

            buyTicketPanel.add(new JLabel("Gender(M/F): "));
            buyTicketPanel.add(passengerGender.get(i), "wrap");

            buyTicketPanel.add(spacer, "wrap");
        }

        JLabel paymentDetails = new JLabel("Payment Details");
        float baseFare = (fare[airports.indexOf(source)] + fare[airports.indexOf(destination)]) * passengerCount;
        float taxRate = flyClass.equals("Business") ? Float.parseFloat("12") : Float.parseFloat("5");
        float multiplier = flyClass.equals("Business") ? Float.parseFloat("4") : Float.parseFloat("1");
        baseFare = multiplier * baseFare;
        float tax = (float) (taxRate * baseFare/100);
        float totalFare = (float) baseFare + tax;
        buyTicketPanel.add(new JLabel("Total Base Fare: " + baseFare));
        buyTicketPanel.add(new JLabel("GST ("+taxRate+"%): "  + tax));
        buyTicketPanel.add(new JLabel("Total Fare: " + totalFare), "wrap");

        JLabel creditCardLabel = new JLabel("Credit Card No (16 digits): ");
        JTextField creditCardNo = new JTextField(10);
        JLabel creditCardExpiryLabel = new JLabel("Expiry Date (MM/YYYY): ");
        JTextField creditCardExpiry = new JTextField(4);
        JLabel creditCardCvcLabel = new JLabel("CVC No: ");
        JTextField creditCardCvc = new JTextField(2);


        buyTicketPanel.add(paymentDetails, "wrap");
        buyTicketPanel.add(creditCardLabel);
        buyTicketPanel.add(creditCardNo, "wrap");
        buyTicketPanel.add(creditCardExpiryLabel);
        buyTicketPanel.add(creditCardExpiry);
        buyTicketPanel.add(creditCardCvcLabel);
        buyTicketPanel.add(creditCardCvc, "wrap");

        JButton submitButton = new JButton("Buy Tickets");
        buyTicketPanel.add(submitButton);
        JLabel message = new JLabel();
        message.setForeground(Color.RED);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = genderValidation(passengerGender);
                if(msg != null) {
                    message.setText(msg);
                    createBuyTicketPanel(departDate, returnDate, source, destination, passengerCount, flyClass);
                    buyTicketPanel.add(message);
                    mainFrame.setContentPane(buyTicketPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                    return;
                }
                msg = creditCardValidation(creditCardNo, creditCardExpiry, creditCardCvc);
                if(msg != null) {
                    message.setText(msg);
                    createBuyTicketPanel(departDate, returnDate, source, destination, passengerCount, flyClass);
                    buyTicketPanel.add(message);
                    mainFrame.setContentPane(buyTicketPanel);
                    mainFrame.invalidate();
                    mainFrame.validate();
                    return;
                }
                // TODO: Everything is good. Transaction is ready to be processed
                ArrayList<String> firstNames = new ArrayList<>();
                ArrayList<String> lastNames = new ArrayList<>();
                ArrayList<String> ages = new ArrayList<>();
                ArrayList<String> genders = new ArrayList<>();
                for(int i = 0;i<passengerFirstName.size(); i++) {
                    firstNames.add(passengerFirstName.get(i).getText().trim());
                    lastNames.add(passengerLastName.get(i).getText().trim());
                    ages.add(passengerAge.get(i).getText().trim());
                    genders.add(passengerGender.get(i).getText().trim());
                }
                String transactionID = UUID.randomUUID().toString();
                System.out.println("Transaction ID: " + transactionID);
                createConfirmationPanel(transactionID, firstNames, lastNames);
            }
        });
    }

    public void createConfirmationPanel(String transactionID, ArrayList<String> firstNames, ArrayList<String> lastNames) {
        confirmationPanel = new JPanel();
        confirmationPanel.setLayout(new MigLayout());
        confirmationPanel.add(backButton, "wrap");
        JLabel confirmationLabel = new JLabel("Your booking is confirmed. Booking ID: " + transactionID);
        confirmationPanel.add(confirmationLabel, "wrap");
        confirmationPanel.add(spacer, "wrap");
        confirmationPanel.add(new JLabel("Confirmed Passengers:"), "wrap");
        for(int i=0; i<firstNames.size();i++) {
            confirmationPanel.add(new JLabel(firstNames.get(i) + " " + lastNames.get(i)), "wrap");
        }
        mainFrame.setContentPane(confirmationPanel);
        mainFrame.invalidate();
        mainFrame.validate();
    }

    public String creditCardValidation(JTextField creditCardNo, JTextField creditCardExpiry, JTextField creditCardCvc) {
        if(creditCardNo.getText().trim().length() != 16) {
            return "Credit Card number should be exact 16 digits";
        }
        if(creditCardCvc.getText().trim().length() != 3) {
            return "CVC number should be exact 3 digits";
        }
        return expiryDateValidator(creditCardExpiry.getText().trim());
    }

    public String expiryDateValidator(String expiryDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
        YearMonth date;
        try {
            date = YearMonth.parse(expiryDate, dtf);
        } catch(Exception e) {
            return "Expiry Date should be of format MM/YYYY";
        }
        if(date.isBefore(YearMonth.now())) {
            return "Credit card expired";
        }
        return null;
    }
    public String genderValidation(ArrayList<JTextField> genderList) {
        for(JTextField gender: genderList) {
            if(!(gender.getText().trim().equals("M") || gender.getText().trim().equals("F"))) {
                return "Gender value needs to be M or F";
            }
        }
        return null;
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
        activityPanel.setLayout(new MigLayout());
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
        newUserPanel.setLayout(new MigLayout());
        newUserPanel.add(backButton);
        newUserPanel.add(spacer, "span, grow");
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
                newUserPanel.add(spacer, "span, grow");
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
        loginUserPanel.setLayout(new MigLayout());
        loginUserPanel.add(backButton);
        loginUserPanel.add(spacer, "span, grow");
        JLabel userNameLabel = new JLabel("User name: ", JLabel.RIGHT);
        JLabel passwordLabel = new JLabel("Password: ", JLabel.RIGHT);
        JLabel message = new JLabel("", JLabel.CENTER);
        message.setForeground(Color.RED);
        JTextField username = new JTextField(10);
        JPasswordField password = new JPasswordField(10);
        password.setEchoChar('*');

        JButton loginButton = new JButton("Log In");

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
                    loginUserPanel.add(spacer, "span, grow");
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

    public void initPanelsAndButtons() {
        backButton = new JButton("Back");
        newUserButton = new JButton("New User");
        loginUserButton = new JButton("Login");

        backButton.addActionListener(backButtonActionListener());
        newUserButton.addActionListener(newUserButtonActionListener());
        loginUserButton.addActionListener(loginUserButtonActionListener());

        createBookTicketPanel();
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
