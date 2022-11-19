package edu.ucalgary.ensf409;
import java.lang.Exception;

public class FileNotFoundException extends Exception { 
    public FileNotFoundException(String message) 
    {
        super(message);
    }
}