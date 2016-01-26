package com.company.Exceptions;

/**
 * Created by JulianStellaard on 26/01/16.
 */
public class WrongCommandoException extends Exception{
    public WrongCommandoException(String message) {
        super(message);
    }
}
