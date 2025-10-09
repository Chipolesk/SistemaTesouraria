package org.exemploTesouraria.DTO;

import java.time.LocalDate;
import java.util.Date;

public class DebtorWithCanteenDTO {
    private String nameDebtor;
    private Double amount;
    private LocalDate canteenDate;

    public DebtorWithCanteenDTO(String nameDebtor, Double amount, LocalDate canteenDate) {
        this.nameDebtor = nameDebtor;
        this.amount = amount;
        this.canteenDate = canteenDate;
    }
}
