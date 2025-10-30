package org.exemploTesouraria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;


@ResponseStatus(HttpStatus.NOT_FOUND)
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
    public static ResourceNotFoundException CanteenNotFound(LocalDate dateCant){
        return new ResourceNotFoundException("Cantina não encontrada do dia: " + dateCant);
    }
}
