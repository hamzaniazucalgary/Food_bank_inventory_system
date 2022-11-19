package edu.ucalgary.ensf409;
import java.lang.Exception;

public class ItemNotFoundException extends Exception { 
    public ItemNotFoundException(String message) 
    {
        super(message);
    }
}