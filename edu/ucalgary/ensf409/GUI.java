package edu.ucalgary.ensf409;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.FlowLayout;
/**
 * @author Thevin Mahawatte, Mohammad Mufasa, Zane Regel, Hamza Niaz
 * @version 1.9
 * @Since 1.0
 * 
 * In this class, we create our GUI for the Food Bank. The GUI prompts the user for their first and last name to be put into the order form.
 * It does do error checking where if the user inputs a non capital first letter or a digit or leaves the textfield empty, the GUI gives the 
 * user an error and lets them fix their mistake. Once the user has a valid input for first and last name, they can begin to make the order(s)
 * with different variations of hampers. Our GUI also has error checking for the input for the order and hampers. Once the user is done with the
 * order(s) and hamper(s), they can submit the order to print a text file with the first and last name of the user, the different order(s) with
 * their hamper(s). It also prints the number and type of clients for each hamper. Finally, it prints the item IDs and their names for each of the
 * hamper request. If an order was not able to be fulfilled, the GUI lets the user know and prints the complete order(s) that it was able to fulfil.
 * The user can cancel the order where nothing will be printed and not items will be taken out of the database.  
 */
public class GUI extends JFrame implements ActionListener, MouseListener{
    /**
     * Here was have the private variables for the textfields, jframes, jlabels and the buttons to be used later in the code.
     */
    private static String firstName;    
    private static String lastName;

    private Hamper hamper;
    
    private int adultMale;
    private int adultFemale;
    private int childrenOver8;
    private int childrenBelow8;
    private int noOfMales;
    private int noOfFemales;
    private int noOfChildrenU8;
    private int noOfChildrenO8;

    private static HashMap<Integer,Client> clientMap = new HashMap<>();
    private static HashMap<Integer,Food> foodMap = new HashMap<>();

    private static ArrayList<Client> clientList;
    private static ArrayList<House> houseList = new ArrayList<House>();
    
    private JLabel instructions;
    private JLabel fnLabel;
    private JLabel lnLabel;
    private JLabel adultMaleLabel;
    private JLabel adultFemaleLabel;
    private JLabel childrenOver8Label;
    private JLabel childrenBelow8Label;
    
    private JTextField fnInput;
    private JTextField lnInput;
    private JTextField adultMaleInput;
    private JTextField adultFemaleInput;
    private JTextField childrenOver8Input;
    private JTextField childrenBelow8Input;
    
    private static int hamperNumber;
    private static int orderNumber;
    
    JButton cancelInfo = new JButton("Cancel");
    JButton submitInfo = new JButton("Submit");
    JButton newOrderInfo = new JButton("New Order");
    JButton nextHamperInfo = new JButton("New Hamper(Same Order)");
    JButton addHamperInfo = new JButton("Start Adding Hampers");
    
    private static int[][][] clientNumbers;
        
    //This constructor makes the initial client GUI. It prompts the client for the first and last name.
    public GUI(){
        super("Food Bank");
        setupClientGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
    }
    //This constructor make the hamper GUI, where the client can make the order(s) of hamper(s) of different variations.
    //Depending on the clinet input for the button, it will either call this constructor again to promt the client for additional 
    //hampers/orders, sumbit the current order(s) or cancel the program.
    public GUI(String hamper){
        super(hamper);
        setupHamperGUI();
        setSize(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
    }
    //This method sets up the hamper GUI with the labels, textfields and buttons.
    public void setupHamperGUI(){
        
        instructions = new JLabel("Please enter hamper information.");
        adultMaleLabel = new JLabel("Number of Adult Males:");
        adultFemaleLabel = new JLabel("Number of Adule Females:");
        childrenOver8Label = new JLabel("Number of Children Over 8:");
        childrenBelow8Label = new JLabel("Number of Children below 8:");
        
        adultMaleInput = new JTextField("e.g. 4", 2);
        adultFemaleInput = new JTextField("e.g. 2", 2);
        childrenOver8Input = new JTextField("e.g. 3", 2);
        childrenBelow8Input = new JTextField("e.g. 1", 2);
        
        adultMaleInput.addMouseListener(this);
        adultFemaleInput.addMouseListener(this);
        childrenOver8Input.addMouseListener(this);
        childrenBelow8Input.addMouseListener(this);
        
        cancelInfo.addActionListener(this);
        submitInfo.addActionListener(this);
        nextHamperInfo.addActionListener(this);
        newOrderInfo.addActionListener(this);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        
        JPanel dataInputPanel = new JPanel();
        dataInputPanel.setLayout(new FlowLayout());

        JPanel buttonPannel = new JPanel();
        buttonPannel.setLayout(new FlowLayout());
        
        headerPanel.add(instructions);
        dataInputPanel.add(adultMaleLabel);
        dataInputPanel.add(adultMaleInput);
        dataInputPanel.add(adultFemaleLabel);
        dataInputPanel.add(adultFemaleInput);
        dataInputPanel.add(childrenOver8Label);
        dataInputPanel.add(childrenOver8Input);
        dataInputPanel.add(childrenBelow8Label);
        dataInputPanel.add(childrenBelow8Input);
        
        buttonPannel.add(nextHamperInfo);
        buttonPannel.add(newOrderInfo);
        buttonPannel.add(submitInfo);
        buttonPannel.add(cancelInfo);

        this.add(headerPanel, BorderLayout.NORTH);
        this.add(dataInputPanel, BorderLayout.CENTER);
        this.add(buttonPannel, BorderLayout.PAGE_END);
        
    }
    //This method sets up the client GUI with the labels, buttons and textfields.
    public void setupClientGUI(){
        
        instructions = new JLabel("Please enter your information to generate the order.");
        fnLabel = new JLabel("First Name:");
        lnLabel = new JLabel("Last Name:");
        
        fnInput = new JTextField("e.g. Dorothy", 15);
        lnInput = new JTextField("e.g. Gale", 15);
        
        fnInput.addMouseListener(this);
        lnInput.addMouseListener(this);
        
        cancelInfo.addActionListener(this);
        addHamperInfo.addActionListener(this);
        
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        
        JPanel clientPanel = new JPanel();
        clientPanel.setLayout(new FlowLayout());

        JPanel addHamperPanel = new JPanel();
        addHamperPanel.setLayout(new FlowLayout());
        
        headerPanel.add(instructions);
        clientPanel.add(fnLabel);
        clientPanel.add(fnInput);
        clientPanel.add(lnLabel);
        clientPanel.add(lnInput);
        
        addHamperPanel.add(addHamperInfo);
        addHamperPanel.add(cancelInfo);
        
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(clientPanel, BorderLayout.CENTER);
        this.add(addHamperPanel, BorderLayout.PAGE_END);
    }
    //Some getters for the first and last name
    public String getfirstName(){
        return this.firstName;
    }

    public String getlastName() {
        return this.lastName;
    }
    
    @Override
    //This method checks for the action that was performed. Based on the action, it calls different methods.
    public void actionPerformed(ActionEvent event){
        //If the action was the cancel button, the program terminates.
        if(event.getSource() == cancelInfo)
        {
            System.exit(0);
        }
        //If the action was the add hamper button, it saves the first and last name input into vairables to be used later. 
        //It calls the method validateClientInput to check if the first and last name inputs were correct or not. If not, shows a message
        //dialog. If it is correct, it calls the setupHamperGUI constructor.
        if(event.getSource() == addHamperInfo)
        {
            lastName = lnInput.getText();
            firstName = fnInput.getText();
            if(firstName.equals("") || lastName.equals("")){
                JOptionPane.showMessageDialog(this, "No input provided for first or last name");
            }else{
                if (validateClientInput()) 
            {
                clientNumbers = new int[5][10][4];
                orderNumber = 1;
                hamperNumber = 1;
                this.setVisible(false);
                EventQueue.invokeLater(() -> {
                    new GUI("Order: " + String.valueOf(orderNumber) + "Hamper: " + String.valueOf(hamperNumber)).setVisible(true);
                });
            }
            }
             
        }
        /**
         * If the action is the next hamper button, it checks the current hamper inputs. If valid, it saves those inputs and calls the same GUI again 
         * for the user to input the next hamper. If not valid inputs, shows a message dialog.
         */
        if(event.getSource() == nextHamperInfo)
        {
            if (adultMaleInput.getText().length() > 0 && adultFemaleInput.getText().length() > 0
                    && childrenBelow8Input.getText().length() > 0 && childrenOver8Input.getText().length() > 0) 
            {
                if (Character.isDigit(adultMaleInput.getText().charAt(0)) && Character.isDigit(adultFemaleInput.getText().charAt(0)) 
                && Character.isDigit(childrenBelow8Input.getText().charAt(0)) && Character.isDigit(childrenOver8Input.getText().charAt(0))) 
                {
                    adultMale = Integer.parseInt(adultMaleInput.getText());
                    adultFemale = Integer.parseInt(adultFemaleInput.getText());
                    childrenOver8 = Integer.parseInt(childrenOver8Input.getText());
                    childrenBelow8 = Integer.parseInt(childrenBelow8Input.getText());
                    if (validateHamperInput()) 
                    {
                        clientNumbers[orderNumber-1][hamperNumber - 1][0] = adultMale;
                        clientNumbers[orderNumber-1][hamperNumber - 1][1] = adultFemale;
                        clientNumbers[orderNumber-1][hamperNumber - 1][2] = childrenOver8;
                        clientNumbers[orderNumber-1][hamperNumber - 1][3] = childrenBelow8;
                    }
                    this.setVisible(false);
                    hamperNumber++;

                    EventQueue.invokeLater(() -> {
                        new GUI("Order: " + String.valueOf(orderNumber) + "Hamper: " + String.valueOf(hamperNumber)).setVisible(true);
                    });
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Please enter a digit between 0-10 for the clients");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please enter a digit between 0-10 for the clients");
            }
        }
        if(event.getSource()== newOrderInfo)
        {
            boolean c = false;
            if (adultMaleInput.getText().length() > 0 && adultFemaleInput.getText().length() > 0
                    && childrenBelow8Input.getText().length() > 0 && childrenOver8Input.getText().length() > 0) 
            {
                if (Character.isDigit(adultMaleInput.getText().charAt(0)) && Character.isDigit(adultFemaleInput.getText().charAt(0)) 
                && Character.isDigit(childrenBelow8Input.getText().charAt(0)) && Character.isDigit(childrenOver8Input.getText().charAt(0))) 
                {
                    adultMale = Integer.parseInt(adultMaleInput.getText());
                    adultFemale = Integer.parseInt(adultFemaleInput.getText());
                    childrenOver8 = Integer.parseInt(childrenOver8Input.getText());
                    childrenBelow8 = Integer.parseInt(childrenBelow8Input.getText());
                    if (validateHamperInput()) 
                    {
                        clientNumbers[orderNumber-1][hamperNumber - 1][0] = adultMale;
                        clientNumbers[orderNumber-1][hamperNumber - 1][1] = adultFemale;
                        clientNumbers[orderNumber-1][hamperNumber - 1][2] = childrenOver8;
                        clientNumbers[orderNumber-1][hamperNumber - 1][3] = childrenBelow8;
                        for(int i = 0; i < hamperNumber; i++)
                        {
                            this.noOfMales = clientNumbers[orderNumber-1][i][0];
                            this.noOfFemales = clientNumbers[orderNumber-1][i][1];
                            this.noOfChildrenO8 = clientNumbers[orderNumber-1][i][2];
                            this.noOfChildrenU8 = clientNumbers[orderNumber-1][i][3];
                            try {
                                createHouse();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            callOrder();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        } catch(ItemNotFoundException e){
                            JOptionPane.showMessageDialog(this, "Not Enough Food To Fullfill Current Order");
                            houseList.clear();
                            hamperNumber = 0;
                            clientNumbers[orderNumber - 1] = null;
                            c = true;
                        }
                        
                        if (!c) {
                            orderNumber++;
                            hamperNumber = 1;
                            this.setVisible(false);
                            EventQueue.invokeLater(() -> {
                                new GUI("Order: " + String.valueOf(orderNumber) + "Hamper: "
                                        + String.valueOf(hamperNumber)).setVisible(true);
                            });
                        }
                    }
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Please enter a digit between 0-10 for the clients");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please enter a digit between 0-10 for the clients");
            }
            
        }
        if(event.getSource()== submitInfo)
        {
            boolean c = false;
            if (adultMaleInput.getText().length() > 0 && adultFemaleInput.getText().length() > 0
                    && childrenBelow8Input.getText().length() > 0 && childrenOver8Input.getText().length() > 0) 
            {
                if (Character.isDigit(adultMaleInput.getText().charAt(0))
                        && Character.isDigit(adultFemaleInput.getText().charAt(0))
                        && Character.isDigit(childrenBelow8Input.getText().charAt(0))
                        && Character.isDigit(childrenOver8Input.getText().charAt(0))) 
                {
                    adultMale = Integer.parseInt(adultMaleInput.getText());
                    adultFemale = Integer.parseInt(adultFemaleInput.getText());
                    childrenOver8 = Integer.parseInt(childrenOver8Input.getText());
                    childrenBelow8 = Integer.parseInt(childrenBelow8Input.getText());
                    
                    if (validateHamperInput()) 
                    {
                        clientNumbers[orderNumber-1][hamperNumber - 1][0] = adultMale;
                        clientNumbers[orderNumber-1][hamperNumber - 1][1] = adultFemale;
                        clientNumbers[orderNumber-1][hamperNumber - 1][2] = childrenOver8;
                        clientNumbers[orderNumber-1][hamperNumber - 1][3] = childrenBelow8;
                        for(int i = 0; i < hamperNumber; i++)
                        {
                            this.noOfMales = clientNumbers[orderNumber-1][i][0];
                            this.noOfFemales = clientNumbers[orderNumber-1][i][1];
                            this.noOfChildrenO8 = clientNumbers[orderNumber-1][i][2];
                            this.noOfChildrenU8 = clientNumbers[orderNumber-1][i][3];
                            try {
                                createHouse();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            callOrder();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        } catch(ItemNotFoundException e){
                            JOptionPane.showMessageDialog(this, "Not Enough Food To Fullfill Current Order. Please Enter A Different Configuration and Try Again");
                            houseList.clear();
                            c = true;
                        }
                        
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Please enter a digit between 0-10 for the clients");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please enter a digit between 0-10 for the clients");
            }
            if(!c){
                System.out.println("Order was successfully printed in the working directory");
                System.exit(0);
            }   
        }
    }

    public void createHouse() throws CloneNotSupportedException{
        clientList = new ArrayList<Client>();
        for(int i = 0; i < noOfMales; i++){
            clientList.add(clientMap.get(1).clone());
        }
        for(int i = 0; i < noOfFemales; i++){
            clientList.add(clientMap.get(2).clone());
        }   
        for(int i = 0; i < noOfChildrenO8; i++){
            clientList.add(clientMap.get(3).clone());
        }
        for(int i = 0; i < noOfChildrenU8; i++){
            clientList.add(clientMap.get(4).clone());
        }
        House house = new House(clientList,noOfMales,noOfFemales,noOfChildrenO8,noOfChildrenU8);
        houseList.add(house);
    }

    public void callOrder() throws CloneNotSupportedException, ItemNotFoundException{
        Order order = new Order(houseList,firstName,lastName);
        houseList.clear();
    }
    
    public void mouseClicked(MouseEvent event){
        
        if(event.getSource().equals(fnInput))
            fnInput.setText("");

        if(event.getSource().equals(lnInput))
            lnInput.setText("");
        
        if(event.getSource().equals(adultMaleInput))
            adultMaleInput.setText("");
        
        if(event.getSource().equals(adultFemaleInput))
            adultFemaleInput.setText("");
        
        if(event.getSource().equals(childrenOver8Input))
            childrenOver8Input.setText("");
        
        if(event.getSource().equals(childrenBelow8Input))
            childrenBelow8Input.setText("");
    }
    
    public void mouseEntered(MouseEvent event){
        
    }

    public void mouseExited(MouseEvent event){
        
    }

    public void mousePressed(MouseEvent event){
        
    }

    public void mouseReleased(MouseEvent event){
        
    }
    
    public ArrayList<Client> getClientList(){
        return this.clientList;
    }

    public ArrayList<House> getHouseList(){
        return this.houseList;
    }
    
    private boolean validateClientInput(){
        
        boolean allInputValid = true;
        
        if(!Character.isUpperCase(firstName.charAt(0)) || (firstName.length()) < 2 || (firstName.length()) > 26 || firstName.equals("")){
            allInputValid = false;
            JOptionPane.showMessageDialog(this, firstName + " is an invalid first name input. The first letter needs to be capitalized \nand needs to be longer than 2 characters");
        }
        
        if(!Character.isUpperCase(lastName.charAt(0)) || lastName.length() < 2 || lastName.length() > 26 || lastName.equals("")){
            allInputValid = false;
            JOptionPane.showMessageDialog(this, lastName + " is an invalid last name input. The first letter needs to be capitalized \nand needs to be longer than 2 characters");
        }

        return allInputValid;   
    }
    
    private boolean validateHamperInput()
    {
        boolean allInputValid = true;
        
        if(adultMale < 0 || adultMale > 10)
        {
            allInputValid = false;
            JOptionPane.showMessageDialog(this, adultMale + " is an invalid number of Adult Males. Please enter a number between 0-10");
        }
        if(adultFemale < 0 || adultFemale > 10)
        {
            allInputValid = false;
            JOptionPane.showMessageDialog(this, adultFemale + " is an invalid number of Adult Females. Please enter a number between 0-10");
        }
        if(childrenOver8 < 0 || childrenOver8 > 10)
        {
            allInputValid = false;
            JOptionPane.showMessageDialog(this, childrenOver8 + " is an invalid number of Children Over 8. Please enter a number between 0-10");
        }
        if(childrenBelow8 < 0 || childrenBelow8 > 10)
        {
            allInputValid = false;
            JOptionPane.showMessageDialog(this, childrenBelow8 + " is an invalid number of Children Below 8. Please enter a number between 0-10");
        }
        
        return allInputValid;
    }
    
    public static void connectDB(){
        Inventory inven = new Inventory("jdbc:mysql://localhost/food_inventory", "student", "ensf");
        inven.initConnection();
        clientMap = inven.selectAllClients();
        foodMap = inven.selectAllFoodItems();
    }
    
    
    public static int getOrderNumber()
    {
        return orderNumber;
    }
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);        
        });
        connectDB();
    }
        
}