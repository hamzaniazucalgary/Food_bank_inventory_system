package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.*;
import java.util.HashMap;
/**
 * @author Thevin Mahawatte, Mohammad Mufasa, Zane Regel, Hamza Niaz
 * @version 1.3
 * @since 1.0
 * 
 * This class takes care of the SQL. It initializes a connection to the database, where the url, user and password are provided through the constructor.
 * Once it initializes the connection, it populates two HashMaps, one for the clients and one for the food items. The client map uses the client ID as key
 * and client objects as values. Similarly, the food map uses the food item ID as key and food objects as values. This class also has a function for removing
 * food items from the database and a function for closing things.
 */

public class Inventory {
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;

    private static Connection dbConnect;
    private static ResultSet results;
    protected static HashMap<Integer,Client> clientMap;
    protected static HashMap<Integer,Food> foodMap;


    //Inventory ctr with the url, user and password
    public Inventory(String url, String user, String pw){
        this.DBURL = url;
        this.USERNAME = user;
        this.PASSWORD = pw;
    }

    //This method initialize the connection to the database. If an exception is caught, print the trace.
    public void initConnection(){
        try {
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Getters
    public String getDbrul(){return this.DBURL;}
    public String getUsername(){return this.USERNAME;}
    public String getPassword(){return this.PASSWORD;}
    //This method selects all the food items in the database and puts them in the food map.
    public HashMap<Integer,Client> selectAllClients(){
        try {
            clientMap = new HashMap<Integer,Client>();
            Statement st = dbConnect.createStatement();
            results = st.executeQuery("SELECT * FROM daily_client_needs");
            while(results.next()){
                Integer id = results.getInt("ClientID");
                String name = results.getString("Client");
                Integer wg = results.getInt("WholeGrains");
                Integer fv = results.getInt("FruitVeggies");
                Integer protein = results.getInt("Protein");
                Integer other = results.getInt("Other");
                Integer calories = results.getInt("Calories");
                clientMap.put(id, new Client(id,name,wg,fv,protein,other,calories));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientMap;
    }
    //Getters for the maps
    public HashMap<Integer,Food> getfoodMap(){
        return foodMap;
    }

    public HashMap<Integer,Client> getclientMap(){
        return clientMap;
    }
    //This method selects all the clients types in the database and also puts them in a client map.
    public static HashMap<Integer,Food> selectAllFoodItems(){
        try {
            foodMap = new HashMap<Integer,Food>();
            Statement st = dbConnect.createStatement();
            results = st.executeQuery("SELECT * FROM available_food");
            while(results.next()){
                Integer id = results.getInt("ItemID");
                String name = results.getString("Name");
                double gc = results.getInt("GrainContent");
                double fv = results.getInt("FVContent");
                double protein = results.getInt("ProContent");
                double other = results.getInt("Other");
                double cal = results.getInt("Calories");
                foodMap.put(id, new Food(id,name,gc,fv,protein,other,cal));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodMap;
    }
    //This method is used to remove food items from the database based on their item ID. We do not have the same method for the client database since we
    //will not be removing anything
    public static void deleteItem(int id){
        try {
            String query = "DELETE FROM available_food WHERE ItemID = ?";
            PreparedStatement pst = dbConnect.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //This method closes the results set and the database connection.
    public void close(){
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //We added this method just incase we want to add items back into the food item map but it was never needed. We left it here anyway.
    public void insertNewItem(int id, String name, int gc, int fv, int pro, int other, int cal){
        try {
            boolean changeID = false;
            String query = "INSERT INTO available_food (ItemID, Name, GrainContent, FVContent, ProContent, Other, Calories) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stm = dbConnect.prepareStatement(query);
            results = stm.executeQuery("SELECT * FROM available_food");
            while(results.next()){
                if(results.getInt("ItemID") == id){
                    id++;
                    changeID = true;
                }
            }
            if(changeID == true){
                System.out.println("The provided already existed in the database. The new item is assigned the ID: " + id);
            }
            stm.setInt(1, id);
            stm.setString(2, name);
            stm.setInt(3, gc);
            stm.setInt(4, fv);
            stm.setInt(5, pro);
            stm.setInt(6, other);
            stm.setInt(7, cal);
            stm.executeUpdate();
            stm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

