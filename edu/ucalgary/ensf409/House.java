package edu.ucalgary.ensf409;
import java.util.*;
/**
 * @author Thevin Mahawatte, Mohammad Mufasa, Zane Regel, Hamza Niaz
 * @version 1.2
 * @since 1.0
 * 
 * This class creates a house based on the array list of clients it recieves. It calculates the total needs for a family by having temp variables
 * which are assigned values for each of the client type. Once the loop is done, the values are assigned to the private variables where they can
 * be accessed with getters.
 */
public class House {
    private ArrayList<Client> house;
    private final int TOTAL_WG;
    private final int TOTAL_FV;
    private final int TOTAL_PROTEIN;
    private final int TOTAL_OTHER;
    private final int TOTAL_CALORIES;

    private final int MALES;
    private final int FEMALES;
    private final int CO8;
    private final int CU8;

    private Set<String> names = new HashSet<String>();
    //Calculates the total needs of a house. It also keeps track of the # of each type of client to be used when printing the orderform.
    public House(ArrayList<Client> house, int males, int females, int co8, int cu8){
        this.house = house;
        int tempWG = 0;
        int tempFV = 0;
        int tempPro = 0;
        int tempOth = 0;
        int tempCal = 0;
        for(int i = 0; i < house.size(); i++){
            tempCal += house.get(i).getCLIENT_CALORIES();
            tempWG +=  ((double)house.get(i).getCLIENT_WG() / 100) * house.get(i).getCLIENT_CALORIES();
            tempFV +=  ((double)house.get(i).getCLIENT_FV() / 100) * house.get(i).getCLIENT_CALORIES();
            tempPro += ((double)house.get(i).getCLIENT_PROTEIN() / 100) * house.get(i).getCLIENT_CALORIES();
            tempOth += ((double)house.get(i).getCLIENT_OTHER() / 100) * house.get(i).getCLIENT_CALORIES();
            this.names.add(house.get(i).getCLIENT_NAME());
        }
        this.TOTAL_CALORIES = tempCal;
        this.TOTAL_FV = tempFV;
        this.TOTAL_OTHER = tempOth;
        this.TOTAL_PROTEIN = tempPro;
        this.TOTAL_WG = tempWG;
        this.MALES = males;
        this.FEMALES = females;
        this.CO8 = co8;
        this.CU8 = cu8;
    }
    //Getters for the private variables
    public int getMALES() {
        return this.MALES;
    }

    public Set<String> getNames() {
        return this.names;
    }

    public int getFEMALES() {
        return this.FEMALES;
    }

    public int getCO8() {
        return this.CO8;
    }

    public int getCU8() {
        return this.CU8;
    }

    public ArrayList<Client> getHouse(){
        return this.house;
    }

    public void setHouse(ArrayList<Client> house){
        this.house = house;
    }

    public int getTOTAL_WG(){
        return this.TOTAL_WG;
    }

    public int getTOTAL_FV() {
        return this.TOTAL_FV;
    }

    public int getTOTAL_PROTEIN() {
        return this.TOTAL_PROTEIN;
    }

    public int getTOTAL_OTHER() {;
        return this.TOTAL_OTHER;
    }

    public int getTOTAL_CALORIES() {
        return this.TOTAL_CALORIES;
    }
    
}
