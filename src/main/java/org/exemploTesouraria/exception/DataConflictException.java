package org.exemploTesouraria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }


    public static DataConflictException  CanteenAlreadyExist(LocalDate dateCant){
        return new DataConflictException("Cantina já existente na data: " + dateCant);
    }
    public static DataConflictException  userAlreadyExist(String username){
        return new DataConflictException("Usuário já existente com este nome: " + username);
    }
    // public static DataConflictException  MonthlyFeeAlreadyExist(Date dateCant){}
}
