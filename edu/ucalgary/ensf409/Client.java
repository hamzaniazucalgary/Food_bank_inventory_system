package edu.ucalgary.ensf409;
import java.util.*;

public class Client implements Cloneable{
    private final String CLIENT_NAME;
    private final int CLIENT_ID;
    private final int CLIENT_WG;
    private final int CLIENT_FV;
    private final int CLIENT_PROTEIN;
    private final int CLIENT_OTHER;
    private final int CLIENT_CALORIES;

    /*Constructor:
    This takes in the nutritional requirements from the database and is then fed into this constructor.
    The Client_ID is how we know what type of client their are. So 1 is adult male, 2 is adult female etc etc. */
    public Client(int CLIENT_ID, String CLIENT_NAME, int CLIENT_WG,int CLIENT_FV, int CLIENT_PROTEIN,
                int CLIENT_OTHER, int CLIENT_CALORIES){
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_NAME = CLIENT_NAME;
        this.CLIENT_WG = CLIENT_WG;
        this.CLIENT_FV = CLIENT_FV;
        this.CLIENT_PROTEIN = CLIENT_PROTEIN;
        this.CLIENT_OTHER = CLIENT_OTHER;
        this.CLIENT_CALORIES = CLIENT_CALORIES;
    }

    //Getters
    //No setters because these should not be changed after they have been initialized
    public int getCLIENT_ID() {
        return this.CLIENT_ID;
    }

    public String getCLIENT_NAME() {
        return this.CLIENT_NAME;
    }

    public int getCLIENT_WG() {
        return this.CLIENT_WG;
    }

    public int getCLIENT_FV() {
        return this.CLIENT_FV;
    }

    public int getCLIENT_PROTEIN() {
        return this.CLIENT_PROTEIN;
    }

    public int getCLIENT_OTHER() {
        return this.CLIENT_OTHER;
    }

    public int getCLIENT_CALORIES() {
        return this.CLIENT_CALORIES;
    }

    /*This checks if whether or not the inputed Object is the same as the current instatiated object
    If they are the same then it returns true*/
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Client)){
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(CLIENT_ID, client.CLIENT_ID) 
                && Objects.equals(CLIENT_NAME, client.CLIENT_NAME)
                && Objects.equals(CLIENT_WG, client.CLIENT_WG)
                && Objects.equals(CLIENT_FV, client.CLIENT_FV)
                && Objects.equals(CLIENT_PROTEIN, client.CLIENT_PROTEIN)
                && Objects.equals(CLIENT_OTHER, client.CLIENT_OTHER)
                && Objects.equals(CLIENT_CALORIES, client.CLIENT_CALORIES);
    }

    //Method that returns the variables are a string for outputs
    @Override
    public String toString() {
        return "{" +
            " CLIENT_ID='" + CLIENT_ID + "'" +
            ", CLIENT_NAME='" + CLIENT_NAME + "'" +
            ", CLIENT_WG='" + CLIENT_WG + "'" +
            ", CLIENT_FV='" + CLIENT_FV + "'" +
            ", CLIENT_PROTEIN='" + CLIENT_PROTEIN + "'" +
            ", CLIENT_OTHER='" + CLIENT_OTHER + "'" +
            ", CLIENT_CALORIES='" + CLIENT_CALORIES + "'" +
            "}";
    }  
    
    public Client clone() throws CloneNotSupportedException{
        return (Client) super.clone();
    }
}
