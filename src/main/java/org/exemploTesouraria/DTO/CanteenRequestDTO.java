package org.exemploTesouraria.DTO;

import java.time.LocalDate;
import java.util.List;

public record CanteenRequestDTO(
     String food,
     LocalDate dateCant,
     String description,
     String annotation,
     double valueSold,
     double expenses,
    List<DebtorDTO> debtors) {}

