package org.exemploTesouraria.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    public static ResourceNotFoundException userNotFound(String name){
        return new ResourceNotFoundException("Usuário não encontrado: " + name);
    }

    public static ResourceNotFoundException monthlyFeeNotFound(String name, String month){
        return new ResourceNotFoundException("Mensalidade de: " + month + " - EM ABERTO - de: " + name);
    }
}
