package edu.ucalgary.ensf409;
import java.util.*;
import java.io.*;
import java.time.LocalDate;
/**
 * @author Thevin Mahawatte, Mohammad Mufasa, Zane Regel, Hamza Niaz
 * @version 1.13
 * @since 1.0
 * In this class, we calculate the least wasteful hamper for the client's request. All of the different calories have their own algorithm.
 * If the client's complete request was unsuccessful, it throws an exception. The main goal of this class to meet all the family's weekly 
 * nutrition requirements. 
 */

import java.lang.Math;
public class Hamper{
    //These private variables store the minimum values for the respective nutrients. It comes in handy when we try to get rid of food
    //items when we go too over board.
    private House house;
    private double minCal;
    private double minPro;
    private double minOth;
    private double minFV;
    private double minWG;
    //These private variables keep track of the amount of calories we have in each category.
    private double cal;
    private double pro;
    private double fv;
    private double other;
    private double wg;
    //These private variables are the family's needs for a week. We will be comparing out current values against these values.
    private double weeklyCal;
    private double weeklyFV;
    private double weeklyOth;
    private double weeklyPro;
    private double weeklyWG;
    //diff is a variable that tells use the difference between the needs and the current values we have.
    private double diff;
    //The foodMap keeps track of the food items we have in the inventory. 
    private HashMap<Integer,Food> foodMap = new HashMap<Integer,Food>();
    //The allSet keeps track of all the food item IDs that fulfil the hamper needs. Once the order is complete, we can remove those IDs
    //from the database
    private TreeSet<Integer> allSet = new TreeSet<Integer>();
    //The removeSet keeps track of all the food item IDs that we can remove from allSet without going under the weekly requirements. More
    //on this later in the documentation
    private Set<Integer> removeSet = new HashSet<Integer>();
    //The addSet has those food item IDs from removeSet that take us under the weekly requirements, so we add them back to allSet
    private Set<Integer> addSet = new HashSet<Integer>();
    //The constructor constructs a hamper based on the house it gets. It does throw a custom expception when we do not have enough food
    //in the inventory. The constructor constructs the house based on a weekly requirements instead of daily
    public Hamper(House house) throws ItemNotFoundException{
        this.house = house;
        this.foodMap = Inventory.foodMap;
        this.weeklyCal = house.getTOTAL_CALORIES() * 7;
        this.weeklyFV = house.getTOTAL_FV() * 7;
        this.weeklyOth = house.getTOTAL_OTHER() * 7;
        this.weeklyPro = house.getTOTAL_PROTEIN() * 7;
        this.weeklyWG = house.getTOTAL_WG() * 7;
        callMethods();
        print();
        
    }
    //Getters for the private vars
    public double getWeeklyCal() {
        return this.weeklyCal;
    }

    public double getWeeklyFV() {
        return this.weeklyFV;
    }

    public double getWeeklyOth() {
        return this.weeklyOth;
    }

    public double getWeeklyPro() {
        return this.weeklyPro;
    }

    public double getWeeklyWG() {
        return this.weeklyWG;
    }

    public HashMap<Integer,Food> getFoodMap() {
        return this.foodMap;
    }

    public TreeSet<Integer> getAllSet() {
        return this.allSet;
    }

    public double getMinPro() {
        return this.minPro;
    }

    public double getCal() {
        return this.cal;
    }

    public double getPro() {
        return this.pro;
    }

    public double getFv() {
        return this.fv;
    }

    public double getOther() {
        return this.other;
    }

    public double getWg() {
        return this.wg;
    }
    //This method call some other methods. Keep the code cleaner and eaiser to understand.
    public void callMethods() throws ItemNotFoundException{
        assignValues();
        proAlg();
        fvAlg();
        othAlg();
        wgAlg();
        lastCheck();
        removeItems();
        checkRequirements();
        removeItemsFromDB();
    }
    

    //In assignValues, we iterate through the foodMap to find the minimum values for each of the different nutrition categories. We assign some default
    //large values to the variables so that the variables have some values to being with. For every food item in the map, it compares the default value
    //of the variable to the map entry value. It only assigns the variable the entry value if and only if the entry default value if greater than the 
    //entry AND the entry value is not 0 AND the entry key is not contained in the set of the selected keys. It does this for the whole map and by the 
    //end of the loop, they all should have the right minimum values. This is done for all the nutrition categories. This is done based on the numerical
    //values of the different categories and not the percentages.
    public void assignValues(){
        minCal = 9999999;
        minFV = 9999999;
        minOth = 9999999;
        minPro = 9999999;
        minWG = 9999999;
        for(Map.Entry<Integer,Food> entry : foodMap.entrySet()){
            if(minCal > entry.getValue().getFOOD_CALORIES() && entry.getValue().getFOOD_CALORIES() != 0 && !allSet.contains(entry.getKey())){
                minCal = entry.getValue().getFOOD_CALORIES();
            }
            if(minFV > entry.getValue().getFOOD_FV() && entry.getValue().getFOOD_FV() != 0 && !allSet.contains(entry.getKey())){
                minFV = entry.getValue().getFOOD_FV();
            }
            if(minOth > entry.getValue().getFOOD_OTHER() && entry.getValue().getFOOD_OTHER() != 0 && !allSet.contains(entry.getKey())){
                minOth = entry.getValue().getFOOD_OTHER();
            }
            if(minPro > entry.getValue().getFOOD_PROTEIN() && entry.getValue().getFOOD_PROTEIN() != 0 && !allSet.contains(entry.getKey())){
                minPro = entry.getValue().getFOOD_PROTEIN();
            }
            if(minWG > entry.getValue().getFOOD_WG() && entry.getValue().getFOOD_WG() != 0 && !allSet.contains(entry.getKey())){
                minWG = entry.getValue().getFOOD_WG();
            }
        }
    }
    //This method calculates the best set of food items for protein only. This means that it only checks the protein content of the food item. This 
    //way, it gives the best case item set. Even though we only care about the protein content, we still add the food item's other nutriton categories
    //to their respective variables. We do this beacuse when we call the other algorithms, we would start from those numbers and not 0. We add all those 
    //items for best case scenario protein to the allSet. We do run the map loop in a for loop itself. That is because we have an int that starts at 100,
    //which indicates the percentage of the nutrition content of the food. We start from 100 and go down to 0 1 by 1 percent. That was, we add the high 
    //percent protein items first so we do not generate alot of waste when doing do. 
    public void proAlg(){
        for(int i = 100; i > 0; i--){
            for(Map.Entry<Integer,Food> entry : foodMap.entrySet()){
                if(pro < weeklyPro && entry.getValue().getFOOD_PROTEIN_PERCENT() >= i && !allSet.contains(entry.getKey())){
                    pro += entry.getValue().getFOOD_PROTEIN();
                    cal += entry.getValue().getFOOD_CALORIES();
                    other += entry.getValue().getFOOD_OTHER();
                    fv += entry.getValue().getFOOD_FV();
                    wg += entry.getValue().getFOOD_WG();
                    diff = Math.abs(pro - weeklyPro);
                    if(pro > weeklyPro && diff > minPro){
                        pro -= entry.getValue().getFOOD_PROTEIN();
                        cal -= entry.getValue().getFOOD_CALORIES();
                        other -= entry.getValue().getFOOD_OTHER();
                        fv -= entry.getValue().getFOOD_FV();
                        wg -= entry.getValue().getFOOD_WG();
                    }else{;
                        allSet.add(entry.getKey());
                    }
                }
            }
        }
    }
    //This method is pretty much the same as the protein one but here we are doing the same for fruit and veggies category. One this to note is that the 
    //fv tracker does not start from 0 and that is because some items that contain protein might also contain fv so those are also added from the protein 
    //method and thus we do not start at 0. Other than that, it pretty much the same as the protein method.
    public void fvAlg(){
        for(int i = 100; i > 0; i--){
            for(Map.Entry<Integer,Food> entry : foodMap.entrySet()){
                if(fv < weeklyFV && entry.getValue().getFOOD_FV_PERCENT() >= i && !allSet.contains(entry.getKey())){
                    pro += entry.getValue().getFOOD_PROTEIN();
                    cal += entry.getValue().getFOOD_CALORIES();
                    other += entry.getValue().getFOOD_OTHER();
                    fv += entry.getValue().getFOOD_FV();
                    wg += entry.getValue().getFOOD_WG();
                    diff = Math.abs(fv - weeklyFV);
                    if(fv > weeklyFV && diff > minFV){
                        pro -= entry.getValue().getFOOD_PROTEIN();
                        cal -= entry.getValue().getFOOD_CALORIES();
                        other -= entry.getValue().getFOOD_OTHER();
                        fv -= entry.getValue().getFOOD_FV();
                        wg -= entry.getValue().getFOOD_WG();
                    }else{
                        allSet.add(entry.getKey());
                    }
                }
            }
        }
    }
    //The same explanination as the protein method and we do not start at 0. This is explainted in the protein and fv documentation.
    public void wgAlg(){
        for(int i = 100; i > 0; i--){
            for(Map.Entry<Integer,Food> entry : foodMap.entrySet()){
                if(wg < weeklyWG && entry.getValue().getFOOD_WG_PERCENT() >= i && !allSet.contains(entry.getKey())){
                    pro += entry.getValue().getFOOD_PROTEIN();
                    cal += entry.getValue().getFOOD_CALORIES();
                    other += entry.getValue().getFOOD_OTHER();
                    fv += entry.getValue().getFOOD_FV();
                    wg += entry.getValue().getFOOD_WG();
                    diff = Math.abs(wg - weeklyWG);
                    if(wg > weeklyWG && diff > minWG){
                        pro -= entry.getValue().getFOOD_PROTEIN();
                        cal -= entry.getValue().getFOOD_CALORIES();
                        other -= entry.getValue().getFOOD_OTHER();
                        fv -= entry.getValue().getFOOD_FV();
                        wg -= entry.getValue().getFOOD_WG();
                    }else{
                        allSet.add(entry.getKey());
                    }
                }
            }
        }
    }
    //Same explanination as above.
    public void othAlg(){
        for(int i = 100; i > 0; i--){
            for(Map.Entry<Integer,Food> entry : foodMap.entrySet()){
                if(other < weeklyOth && entry.getValue().getFOOD_OTHER_PERCENT() >= i && !allSet.contains(entry.getKey())){
                    pro += entry.getValue().getFOOD_PROTEIN();
                    cal += entry.getValue().getFOOD_CALORIES();
                    other += entry.getValue().getFOOD_OTHER();
                    fv += entry.getValue().getFOOD_FV();
                    wg += entry.getValue().getFOOD_WG();
                    diff = Math.abs(other - weeklyOth);
                    if(other > weeklyOth && diff > minOth){
                        pro -= entry.getValue().getFOOD_PROTEIN();
                        cal -= entry.getValue().getFOOD_CALORIES();
                        other -= entry.getValue().getFOOD_OTHER();
                        fv -= entry.getValue().getFOOD_FV();
                        wg -= entry.getValue().getFOOD_WG();
                    }else{
                        allSet.add(entry.getKey());
                    }
                }
            }
        }
    }
    //This method does a last check for the nutrition contents we have compared to the nutrition contents we need. If any of them are lower then the 
    //requirements, it add an item(s) that do not already exist in the allSet until all the requirements are met. This function is just a safety net 
    //and will rarely be used.
    public void lastCheck(){
        for(Map.Entry<Integer,Food> entry : foodMap.entrySet()){
            if((pro < weeklyPro || fv < weeklyFV || other < weeklyOth || wg < weeklyWG) && !allSet.contains(entry.getKey())){
                pro += entry.getValue().getFOOD_PROTEIN();
                cal += entry.getValue().getFOOD_CALORIES();
                other += entry.getValue().getFOOD_OTHER();
                fv += entry.getValue().getFOOD_FV();
                wg += entry.getValue().getFOOD_WG();
                allSet.add(entry.getKey());
            }
        }
    }
    //In this function, we check if we can get rid of any of the items we have in the allSet without going underboard in any of the categories.
    //If we are above in any of the categories, remove the first item in the set, add that item ID to the removeSet
    //and check if the took us underboard in any of the categories. If yes, add the item ID to the addSet. After the loop ends, we take the set difference
    //of removeSet and addSet to see what items were actually removed. Finally we remove those removed items from the over all set and that will be 
    //out final set of items.
    public void removeItems(){
        List<Integer> list = new ArrayList<Integer>(allSet);
        for(int i = 0; i < list.size(); i++){
            if(pro > weeklyPro || fv > weeklyFV || other > weeklyOth || wg > weeklyWG){
                pro -= foodMap.get(list.get(i)).getFOOD_PROTEIN();
                cal -= foodMap.get(list.get(i)).getFOOD_CALORIES();
                fv -= foodMap.get(list.get(i)).getFOOD_FV();
                other -= foodMap.get(list.get(i)).getFOOD_OTHER();
                wg -= foodMap.get(list.get(i)).getFOOD_WG();
                removeSet.add(list.get(i));
                if(pro < weeklyPro || fv < weeklyFV || other < weeklyOth || wg < weeklyWG){
                    pro += foodMap.get(list.get(i)).getFOOD_PROTEIN();
                    cal += foodMap.get(list.get(i)).getFOOD_CALORIES();
                    fv += foodMap.get(list.get(i)).getFOOD_FV();
                    other += foodMap.get(list.get(i)).getFOOD_OTHER();
                    wg += foodMap.get(list.get(i)).getFOOD_WG();
                    addSet.add(list.get(i));
                }
            }
        }
        removeSet.removeAll(addSet);
        allSet.removeAll(removeSet);
    }
    //This method converts the set into a list, which is ran through a for loop for each item ID to be removed from the database. It also updates the
    //food map so if the client want to make a new order, they will be accessing the updated food map and database.
    public void removeItemsFromDB(){
        List<Integer> list = new ArrayList<Integer>(allSet);
        for(int i = 0; i < list.size(); i++){
            Inventory.deleteItem(list.get(i));
            Inventory.selectAllFoodItems();
        }
    }
    //This method for us to see the results of the algorithm. Nothing really happens here
    public void print(){
        List<Integer> list = new ArrayList<Integer>(allSet);
        pro = 0;
        cal = 0;
        fv = 0;
        other = 0;
        wg = 0;
        for(int i = 0; i < list.size(); i++){
            pro += foodMap.get(list.get(i)).getFOOD_PROTEIN();
            cal += foodMap.get(list.get(i)).getFOOD_CALORIES();
            fv += foodMap.get(list.get(i)).getFOOD_FV();
            other += foodMap.get(list.get(i)).getFOOD_OTHER();
            wg += foodMap.get(list.get(i)).getFOOD_WG();
        }
        System.out.println(String.format("Needed Calories %.2f \t\t Calculated Calories %.2f", weeklyCal,cal));
        System.out.println(String.format("Needed FV %.2f \t\t\t Calculated FV %.2f", weeklyFV,fv));
        System.out.println(String.format("Needed WG %.2f \t\t\t Calculated WG %.2f", weeklyWG,wg));
        System.out.println(String.format("Needed Protein %.2f \t\t\t Calculated Protein %.2f", weeklyPro,pro));
        System.out.println(String.format("Needed Other %.2f \t\t\t Calculated Other %.2f", weeklyOth,other));
        System.out.println("\n\n");

    }   
    //This method is the last method that is called. This check if the weekly needs are greater than what we calculated, that means that we do not have 
    //enough food in the inventory and thus throws an expcetion.
    public void checkRequirements() throws ItemNotFoundException{
        if(weeklyCal > cal || weeklyFV > fv || weeklyOth > other || weeklyPro > pro || weeklyWG > wg){
            throw new ItemNotFoundException("Not enough items in the inventory");
        }
    }
}

