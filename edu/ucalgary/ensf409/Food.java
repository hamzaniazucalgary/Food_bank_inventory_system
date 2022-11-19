package edu.ucalgary.ensf409;
/**
 * @author Thevin Mahawatte, Mohammad Mufasa, Zane Regel, Hamza Niaz
 * @version 1.5
 * @since 1.0
 * 
 * In this class, we construct different types of food and have their information in final variables. We can access their information using 
 * getters. In this class, we have variables for the number of different calories and thier percentages too. We can use the number of differet
 * calories for calculations but percentages come in handy when we want to compare mulitple food items and use the ones with the high percentage.
 * That way, we do not waste any food.
 */
public class Food{
    //Private final variables for the food item information
    private final String FOOD_NAME;
    private final Integer FOOD_ID;
    private final double FOOD_WG;
    private final double FOOD_FV;
    private final double FOOD_PROTEIN;
    private final double FOOD_OTHER;
    private final double FOOD_CALORIES;
    //Private final variables for the percent calories.
    private final double FOOD_PROTEIN_PERCENT;
    private final double FOOD_FV_PERCENT;
    private final double FOOD_OTHER_PERCENT;
    private final double FOOD_WG_PERCENT;
    //This constructor constructs a food item every time we call it.
    public Food(Integer FOOD_ID, String FOOD_NAME, double FOOD_WG, double FOOD_FV, double FOOD_PROTEIN, double FOOD_OTHER,double FOOD_CALORIES){
        this.FOOD_ID = FOOD_ID;
        this.FOOD_NAME = FOOD_NAME;
        this.FOOD_CALORIES = FOOD_CALORIES;
        this.FOOD_WG = (FOOD_WG * 0.01) * FOOD_CALORIES;
        this.FOOD_FV = (FOOD_FV * 0.01) * FOOD_CALORIES;
        this.FOOD_PROTEIN = (FOOD_PROTEIN * 0.01) * FOOD_CALORIES;
        this.FOOD_OTHER = (FOOD_OTHER * 0.01) * FOOD_CALORIES;
        

        this.FOOD_PROTEIN_PERCENT = FOOD_PROTEIN;
        this.FOOD_OTHER_PERCENT = FOOD_OTHER;
        this.FOOD_FV_PERCENT = FOOD_FV;
        this.FOOD_WG_PERCENT = FOOD_WG;

    
    }
    //Getters for the private vars
    public double getFOOD_FV_PERCENT() {
        return this.FOOD_FV_PERCENT;
    }

    public double getFOOD_OTHER_PERCENT() {
        return this.FOOD_OTHER_PERCENT;
    }

    public double getFOOD_WG_PERCENT() {
        return this.FOOD_WG_PERCENT;
    }

    public double getFOOD_PROTEIN_PERCENT(){
        return this.FOOD_PROTEIN_PERCENT;
    }

    public Integer getFOOD_ID() {
        return this.FOOD_ID;
    }

    public String getFOOD_NAME() {
        return this.FOOD_NAME;
    }

    public double getFOOD_WG() {
        return this.FOOD_WG;
    }

    public double getFOOD_FV() {
        return this.FOOD_FV;
    }

    public double getFOOD_PROTEIN() {
        return this.FOOD_PROTEIN;
    }

    public double getFOOD_OTHER() {
        return this.FOOD_OTHER;
    }

    public double getFOOD_CALORIES() {
        return this.FOOD_CALORIES;
    }
    //Auto generated code
    @Override
    public String toString() {
        return "{" +
            " FOOD_ID='" + getFOOD_ID() + "'" +
            ", FOOD_NAME='" + getFOOD_NAME() + "'" +
            ", FOOD_WG='" + getFOOD_WG() + "'" +
            ", FOOD_FV='" + getFOOD_FV() + "'" +
            ", FOOD_PROTEIN='" + getFOOD_PROTEIN() + "'" +
            ", FOOD_OTHER='" + getFOOD_OTHER() + "'" +
            ", FOOD_CALORIES='" + getFOOD_CALORIES() + "'" +
            "}";
    }
     
}
