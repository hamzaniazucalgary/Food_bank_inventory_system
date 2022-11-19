package edu.ucalgary.ensf409;
import java.util.*;

import java.io.*;
import java.time.LocalDate;
/**
 * @author Thevin Mahawatte, Mohammad Mufasa, Zane Regel, Hamza Niaz
 * @version 1.4
 * @since 1.0
 * 
 * This class is responsible for printing the orders into a txt file. Once everything is sorted out, the printTxtFile method gets a hamperList as an arg
 * and it goes though the hamper list to print one a time for each order. Our print method also prints the orders seperately.
 */
public class Order implements FormattedOutput{
    private ArrayList<House> houses = new ArrayList<House>();
    private Hamper hamper;
    private String first;
    private String last;
    private GUI gui;
    private int orderNumber;
    private ArrayList<Hamper> hamperList = new ArrayList<Hamper>();
    private Set<Integer> itemIDSet = new HashSet<Integer>();
    private HashMap<Integer,Food> foodMap = new HashMap<Integer,Food>();
    //This accepts an array of houses and creates a hamper per house in the array. This also accepts the first and last name of the orderer
    //After a house is given a successful hamper and the array has been fully sifted through then an output .txt file is printed
    public Order(ArrayList<House> houses, String f, String l) throws ItemNotFoundException{
        this.houses = houses;
        this.first = f;
        this.last = l;
        this.orderNumber = gui.getOrderNumber();
        addNewHamper();
        if(orderNumber == 1)
        {
            printTxtFile();
        }
        printTxtFile(hamperList);
    }
    //Based on the houses individual needs a hamper is created and then added to the order
    public void addNewHamper() throws ItemNotFoundException{
        for(int i = 0; i < houses.size(); i++){
            hamper = new Hamper(houses.get(i));
            hamperList.add(hamper);
        }
    }
    //This writes the first portion of the .txt file:
    //The name of the orderer and todays date
    @Override
    public void printTxtFile()
    {

        try {
            FileWriter myWriter = new FileWriter("Order Form.txt");
            myWriter.write("");
            myWriter.write("Name: " + first + " " + last + "\n");
            myWriter.write("Date: " + LocalDate.now() + "\n\n");
            myWriter.write("Original Hamper Request\n");
            myWriter.close();
            
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }
    //This writes into the .txt how many of each type of person is in each hamper 
    //and then writes what is contained in each hamper in ascending order of ItemID
    @Override
    public void printTxtFile(ArrayList<Hamper> hampers){
        //Write how many of each type is in each Hamper
        try {
            FileWriter writer = new FileWriter("Order Form.txt", true);
            writer.write("Order Number: " + orderNumber + "\n");
            for (int z = 0; z < houses.size(); z++) {
                StringBuilder str = new StringBuilder();
                writer.write("Hamper " + (z + 1) + ": ");
                if (houses.get(z).getMALES() != 0) {
                    str.append(houses.get(z).getMALES() + " Adult Male, ");
                }
                if (houses.get(z).getFEMALES() != 0) {
                    str.append(houses.get(z).getFEMALES() + " Adult Female, ");
                }
                if (houses.get(z).getCO8() != 0) {
                    str.append(houses.get(z).getCO8() + " Child over 8, ");
                }
                if (houses.get(z).getCU8() != 0) {
                    str.append(houses.get(z).getCU8() + " Child under 8, ");
                }
                str.delete(str.length() - 2, str.length());
                writer.write(str.toString() + "\n");
            }
            writer.write("\n");
            //List the ItemID's as well as the Item Names in Ascending order
            for (int i = 0; i < hampers.size(); i++) {
                writer.write("Hamper " + (i + 1) + " Items:\n");
                this.itemIDSet = hampers.get(i).getAllSet();
                this.foodMap = hampers.get(i).getFoodMap();
                List<Integer> list = new ArrayList<Integer>(itemIDSet);
                for (int j = 0; j < list.size(); j++) {
                    writer.write(foodMap.get(list.get(j)).getFOOD_ID() + "\t\t"
                            + foodMap.get(list.get(j)).getFOOD_NAME() + "\n");
                }
                writer.write("\n\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//javac -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/GUI.java
//java -cp .;lib/mysql-connector-java-8.0.23.jar edu.ucalgary.ensf409.GUI
//C:\Users\hamza\OneDrive - University of Calgary\Engineering Degree\Second Year\Winter 2022\ENSF_409\Project\MileStone_5
}
