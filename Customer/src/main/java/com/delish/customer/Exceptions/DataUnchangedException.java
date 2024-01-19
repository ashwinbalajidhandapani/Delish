package com.delish.customer.Exceptions;

public class DataUnchangedException extends RuntimeException{
    public DataUnchangedException(String message){
        super(message);
    }
}
