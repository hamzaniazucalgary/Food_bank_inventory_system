package edu.ucalgary.ensf409;
import java.lang.Exception;

public class IllegalArgumentException extends Exception { 
    public IllegalArgumentException(String message) 
    {
        super(message);
    }
}