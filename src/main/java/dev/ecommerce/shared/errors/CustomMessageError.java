package dev.ecommerce.shared.errors;

public class CustomMessageError extends RuntimeException{
    public CustomMessageError(String message){
        super(message);
    }
}
