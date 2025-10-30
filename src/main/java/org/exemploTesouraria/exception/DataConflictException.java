package org.exemploTesouraria.exception;

import org.exemploTesouraria.model.MonthlyFee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }


    public static DataConflictException  canteenAlreadyExist(LocalDate dateCant){
        return new DataConflictException("Cantina já existente na data: " + dateCant);
    }
    public static DataConflictException  userAlreadyExist(String username){
        return new DataConflictException("Usuário já existente com este nome: " + username);
    }
    public static DataConflictException  monthlyFeeAlreadyExist(MonthlyFee monthlyFee){
        return new DataConflictException("O usuário: " + monthlyFee.getUsers().getName() + " já possui uma mensalidade registrada no mês: " +monthlyFee.getMonth());
    }
    public static DataConflictException monthlyFeeAlreadyPaid(MonthlyFee monthlyFee){
        return new DataConflictException("A mensalidade do mês: " + monthlyFee.getMonth() + " do membro: " + monthlyFee.getUsers().getName() + " já foi paga!");
    }
}
