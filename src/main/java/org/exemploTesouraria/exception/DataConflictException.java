package org.exemploTesouraria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }


    public static DataConflictException  CanteenAlreadyExist(Date dateCant){
        return new DataConflictException("Cantina já existente na data: " + dateCant);
    }
}
