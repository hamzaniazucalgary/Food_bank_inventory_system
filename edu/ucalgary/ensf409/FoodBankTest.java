package edu.ucalgary.ensf409;

import java.beans.Transient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class FoodBankTest {
    Connection dbConnect;
    HashMap<Integer, Client> clientMap = new HashMap<>();
    HashMap<Integer, Food> foodMap = new HashMap<>();
    ArrayList<Client> clientList;
    Inventory inven = new Inventory("jdbc:mysql://localhost/food_inventory", "student", "ensf");
    

    @Test
    /**
     * In this test, we test for the creation of a client object. We use expected values from the database that we already have and use them to construct
     * a client object. If the expected and the actual values match, the test should pass. If not, an error is printed to the terminal.
     */
    public void testCreateClient() {
        HashMap<Integer, Client> clientMap = new HashMap<>();
		int expectedWholeGrains = 16;
        int expectedFruitVegges = 28;
        int expectedProtein = 26;
        int expectedOther = 30;
        int expectedCalories = 2500;
		
        Inventory inven = new Inventory("jdbc:mysql://localhost/food_inventory", "student", "ensf");
        inven.initConnection();
        clientMap = inven.selectAllClients();
		
        int resultWholeGrains = clientMap.get(1).getCLIENT_WG();
        int resultFruitVeggies = clientMap.get(1).getCLIENT_FV();
        int resultProtein = clientMap.get(1).getCLIENT_PROTEIN();
        int resultOther = clientMap.get(1).getCLIENT_OTHER();
        int resultCalories = clientMap.get(1).getCLIENT_CALORIES();

        assertEquals("The expected value for wholes grains did not match the result", expectedWholeGrains,
                resultWholeGrains);
        assertEquals("The expected value for fruit and veggies did not match the result", expectedFruitVegges,
                resultFruitVeggies);
        assertEquals("The expected value for protein did not match the result", expectedProtein, resultProtein);
        assertEquals("The expected value for other content did not match the result", expectedOther, resultOther);
        assertEquals("The expected value for calories did not match the result", expectedCalories, resultCalories);
    }

    @Test
	/**
	* In this test, we test the creation of House object using the House class. We calculated the expected values, and the aactual values are taken 
	* using getters of the House class. Using assertEqual, each actual value is tested
	*/
    public void testCreateHouse() throws CloneNotSupportedException {
        int expectedWholeGrains = 1476;
        int expectedFruitVegges = 2448;
        int expectedProtein = 2286;
        int expectedOther = 1890;
        int expectedCalories = 8100;
        inven.initConnection();
        clientMap = inven.selectAllClients();

        clientList = new ArrayList<Client>();
        clientList.add(clientMap.get(1).clone());
        clientList.add(clientMap.get(2).clone());
        clientList.add(clientMap.get(3).clone());
        clientList.add(clientMap.get(4).clone());

        House house = new House(clientList, 1, 1, 1, 1);

        int resultTotalWholeGrains = house.getTOTAL_WG();
        int resultTotalFruitVeggies = house.getTOTAL_FV();
        int resultTotalProtein = house.getTOTAL_PROTEIN();
        int resultTotalOther = house.getTOTAL_OTHER();
        int resultTotalCalories = house.getTOTAL_CALORIES();

        assertEquals("The expected total value for wholes grains did not match the result", expectedWholeGrains,
                resultTotalWholeGrains);
        assertEquals("The expected total value for fruit and veggies did not match the result", expectedFruitVegges,
                resultTotalFruitVeggies);
        assertEquals("The expected total value for protein did not match the result", expectedProtein,
                resultTotalProtein);
        assertEquals("The expected total value for other content did not match the result", expectedOther,
                resultTotalOther);
        assertEquals("The expected total value for calories did not match the result", expectedCalories,
                resultTotalCalories);

    }

    @Test
	/**
	* In this test, we tested the functionality of removing an item from the database with the given food id. First the id sent to the
	* database to process the removing function, and then using the same id, database is checked if that value is still available. Then using a boolean
	* after all the precesses are done, assretTrue will be used to test the boolean
	*/
    public void testInventoryRemove() {

        Inventory inventory = new Inventory("jdbc:mysql://localhost/food_inventory", "student", "ensf");
        inventory.initConnection();

        int itemID = 15;
        Inventory.deleteItem(itemID);
        boolean result = true;
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/food_inventory", "student", "ensf");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String query = "SELECT ItemID FROM available_food WHERE ItemID = ?";
            PreparedStatement pst = dbConnect.prepareStatement(query);
            pst.setInt(1, itemID);
            result = pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue("The item is not removed from the database", result);
    }

    @Test
	/**
	* In this test, we test the contructor, getter and setter in the Food class. Using assertEqual, expected and actual values will be tested.
	*/
    public void testConstructorFoodAndGetters() {
        Integer expectedID = 1;
        String expectedName = "Tomato Sauce, jar";
        int expectedWG = 0;
        int expectedFV = 0;
        int expectedProtein = 0;
        int expectedOther = 0;
        int expectedCal = 120;
        Food testObj = new Food(1, expectedName, expectedWG, expectedFV, expectedProtein, expectedOther, expectedCal);
        Integer actualID = testObj.getFOOD_ID();
        String actualName = testObj.getFOOD_NAME();
        int actualWG = (int)testObj.getFOOD_WG();
        int actualFV = (int)testObj.getFOOD_FV();
        int actualProtein = (int)testObj.getFOOD_PROTEIN();
        int actualOther = (int)testObj.getFOOD_OTHER();
        int actualCal = (int)testObj.getFOOD_CALORIES();
        assertEquals("Constructor or getter for food ID gave incorrect value", expectedID, actualID);
        assertEquals("Constructor or getter for food name gave incorrect value", expectedName, actualName);
        assertEquals("Constructor or getter for grain content gave incorrect value", expectedWG, actualWG);
        assertEquals("Constructor or getter for fruit veggie content gave incorrect value", expectedFV, actualFV);
        assertEquals("Constructor or getter for protien content gave incorrect value", expectedProtein, actualProtein);
        assertEquals("Constructor or getter for other content gave incorrect value", expectedOther, actualOther);
        assertEquals("Constructor or getter for food calories gave incorrect value", expectedCal, actualCal);
    }

    @Test
	/**
	* In this test, we test the contructor, getters and setters of the Client class. Using assertEqual, expected and actual values will be tested.
	*/
    public void testClientConstructorandGetters() {
        inven.initConnection();
        clientMap = inven.selectAllClients();
        int clientID = 1;
        int expectedWG = 16;
        int expectedFV = 28;
        int expectedProtien = 26;
        int expectedOther = 30;
        int expectedCal = 2500;
        String expectedName = "Adult Male";
        Client testObj = new Client(clientID,expectedName,expectedWG,expectedFV,expectedProtien,expectedOther,expectedCal);
        int actualID = testObj.getCLIENT_ID();
        String actualName = testObj.getCLIENT_NAME();
        int actualWG = testObj.getCLIENT_WG();
        int actualFV = testObj.getCLIENT_FV();
        int actualPro = testObj.getCLIENT_PROTEIN();
        int actualOth = testObj.getCLIENT_OTHER();
        int actualCal = testObj.getCLIENT_CALORIES();


        assertEquals("Constructor or getter for client whole grains need gave incorrect value", expectedWG, actualWG);
        assertEquals("Constructor or getter for client fruit veggies need gave incorrect value", expectedFV, actualFV);
        assertEquals("Constructor or getter for client protein needs gave incorrect value", expectedProtien, actualPro);
        assertEquals("Constructor or getter for client other needs gave incorrect value", expectedOther, actualOth);
        assertEquals("Constructor or getter for client calories need gave incorrect value", expectedCal, actualCal);
        assertEquals("Constructor or getter for client name gave incorrect value", expectedName, actualName);
    }

    @Test
	/**
	* In this test, we check the functionality of creating hamper according to the required nutritions. We check if the actual nutrition values are greater
	* than or equal and using that boolean value, using assertTrue, we check the actual values vs the expected values
	*/
    public void testOrderCreation() throws CloneNotSupportedException, ItemNotFoundException{
        inven.initConnection();
        foodMap = inven.selectAllFoodItems();
        int expectedCal = 56700;
        int expectedFV = 17136;
        int expectedWG = 10332;
        int expectedPro = 16002;
        int expectedOther = 13230;
        clientMap = inven.selectAllClients();
        clientList = new ArrayList<Client>();
        clientList.add(clientMap.get(1).clone());
        clientList.add(clientMap.get(2).clone());
        clientList.add(clientMap.get(3).clone());
        clientList.add(clientMap.get(4).clone());
        House house = new House(clientList, 1, 1, 1, 1);
        Hamper hamper = new Hamper(house);
        int actualCal = (int)hamper.getCal();
        int actualFV = (int)hamper.getFv();
        int actualWG = (int)hamper.getWg();
        int acutalPro = (int)hamper.getPro();
        int actualOth = (int)hamper.getOther();
        boolean c = (actualCal >= expectedCal);
        boolean f = (actualFV >= expectedFV);
        boolean w = (actualWG >= expectedWG);
        boolean p = (acutalPro >= expectedPro);
        boolean o = (actualOth >= expectedOther);
        assertTrue("The calories needs of the order are not met",c);
        assertTrue("The fruit and veggies needs of the order are not met",f);
        assertTrue("The whole grain needs of the order are not met",w);
        assertTrue("The protein needs of the order are not met",p);
        assertTrue("The other content needs of the order are not met",o);

    }
    /**
     * In this test, we test to see if the program throws an exception when there is a food shortage. This test call the hamper class with too many 
     * clients. It causes a food shortage and thus throws an expception. This test should fail and print the expcetion to the terminal
     * 
     */
    @Test
    public void testTooMuchFoodRequested()throws CloneNotSupportedException, ItemNotFoundException{
        boolean exceptionThrown = false;
        inven.initConnection();
        foodMap = inven.selectAllFoodItems();
        clientMap = inven.selectAllClients();
        clientList = new ArrayList<Client>();
        for(int i = 0; i <=5; i++){
            clientList.add(clientMap.get(1).clone());
            clientList.add(clientMap.get(2).clone());
            clientList.add(clientMap.get(3).clone());
            clientList.add(clientMap.get(4).clone());
        }
        try {
            House house = new House(clientList, 5, 5, 5, 5);
            Hamper hamper = new Hamper(house);
        }catch(ItemNotFoundException e){
            exceptionThrown = true;
        }
        assertTrue("Did not throw an exception when there is a food shorage",exceptionThrown);
    }
    
}
// javac -cp .;lib/* edu/ucalgary/ensf409/FoodBankTest.java
// java -cp .;lib/* org.junit.runner.JUnitCore edu.ucalgary.ensf409.FoodBankTest