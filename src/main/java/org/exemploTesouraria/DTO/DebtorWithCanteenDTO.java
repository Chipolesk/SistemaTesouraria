package org.exemploTesouraria.DTO;

import java.util.Date;

public class DebtorWithCanteenDTO {
    private String nameDebtor;
    private Double amount;
    private Date canteenDate;

    public DebtorWithCanteenDTO(String nameDebtor, Double amount, Date canteenDate) {
        this.nameDebtor = nameDebtor;
        this.amount = amount;
        this.canteenDate = canteenDate;
    }
}
