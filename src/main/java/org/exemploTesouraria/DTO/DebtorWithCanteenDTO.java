package org.exemploTesouraria.DTO;

import java.time.LocalDate;


public record DebtorWithCanteenDTO(String nameDebtor, Double amount, LocalDate canteenDate) {}
